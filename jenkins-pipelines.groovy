def PROJECT_NAME = "ojt-minhyeok-resource"
def label = "${PROJECT_NAME}"
podTemplate(
        label: label,
        containers: [
                containerTemplate(name: 'jnlp', image: 'jenkins/inbound-agent:latest', args: '${computer.jnlpmac} ${computer.name}'),
                containerTemplate(name: 'maven', image: 'maven:3.8.3-openjdk-17', command: "cat", ttyEnabled: true),
                containerTemplate(name: "docker", image: "docker", command: "cat", ttyEnabled: true),
                containerTemplate(name: "kubectl", image: "lachlanevenson/k8s-kubectl", command: "cat", ttyEnabled: true)
        ],
        volumes: [
                hostPathVolume(hostPath: "/var/run/docker.sock", mountPath: "/var/run/docker.sock"),
                hostPathVolume(hostPath: "/home/iadmin/.docker/config.json", mountPath: "/root/.docker/config.json"),
                persistentVolumeClaim(mountPath: '/root/.m2/repository', claimName: 'maven-repo-storage', readOnly: false)
        ]
)
        {
            node(label) {

                stage('Git Pull') {
                    checkout scm
                }

                VERSION = readMavenPom().getVersion()

                stage('Maven Build') {
                    MAVEN_BUILD_OPT = "-Pdev clean verify package"
                    MAVEN_BUILD_DCHECK_OPT = "-Ddependency-check.skip=true"
                    MAVEN_BUILD_SKIP_JUNIT_TEST = "-Dmaven.test.skip=false -Dspring.profiles.active=local"
                    echo "Start BUILD ${PROJECT_NAME}:${VERSION} Build_Opt [${MAVEN_BUILD_OPT} ${MAVEN_BUILD_DCHECK_OPT} ${MAVEN_BUILD_SKIP_JUNIT_TEST}]"

                    container('maven') {
                        sh "mvn ${MAVEN_BUILD_OPT} ${MAVEN_BUILD_DCHECK_OPT} ${MAVEN_BUILD_SKIP_JUNIT_TEST}"
                    }
                }

                stage('Docker Build & Push') {
                    container("docker") {
                        dockerApp = docker.build("secaas/${PROJECT_NAME}", "--no-cache -f Dockerfile .")
                        docker.withRegistry("${CONTAINER_REGISTRY_URL}", 'harbor') {
                            dockerApp.push("${VERSION}")
                            dockerApp.push("latest")
                        }
                    }
                }

                stage('Kubernetes Deploy') {

                    container("kubectl") {
                        CURRENT_VERSION = sh(script: "kubectl describe deployment ${PROJECT_NAME} -n ${KUBE_NAMESPACE} | grep Image: | awk -F ':' '{ print \$3 }'",
                                returnStdout: true).trim()

                        echo "Current Running Deployment Version : ${CURRENT_VERSION}"

                        if (VERSION == CURRENT_VERSION) {
                            // 빌드 버전과 현재 버전이 같으면 Re Deploy
                            echo "The currently running deployment version and build version are the same."

                            sh "kubectl rollout restart deploy ${PROJECT_NAME} -n ${KUBE_NAMESPACE}"
                        } else {
                            YAML_FILE = "jenkins-deploy.yml"

                            sh "sed -i 's/BUILD_NUMBER/${VERSION}/g' ${YAML_FILE}"
                            sh "sed -i 's/IMAGE_HOST/${KUBE_IMAGE_HOST}/g' ${YAML_FILE}"
                            sh "sed -i 's/KUBE_NAMESPACE/${KUBE_NAMESPACE}/g' ${YAML_FILE}"
                            sh "kubectl apply -f ${YAML_FILE}"
                        }
                    }
                }
            }
        }
