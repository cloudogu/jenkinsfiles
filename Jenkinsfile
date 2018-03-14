@Library('github.com/triologygmbh/jenkinsfile@f38945a') _

pipeline {
    agent none // Each stage uses its own agent

    options {
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    stages {
        stage('Build') {
            agent { label 'docker' } // Require a build executor with docker

            steps {
                createPipelineTriggers()
                mvn 'clean install -DskipTests'
                archiveArtifacts '**/target/*.*ar'
            }
        }

        stage('Tests') {
            parallel {
                stage('Unit Test') {
                    agent { label 'docker' } // Require a build executor with docker

                    steps {
                        mvn 'test'
                    }
                    post {
                        always {
                            junit allowEmptyResults: true, testResults: '**/target/surefire-reports/TEST-*.xml'
                        }
                    }
                }
                stage('Integration Test') {
                    agent { label 'docker' } // Require a build executor with docker

                    when { expression { return isNightly() } }
                    steps {
                        mvn 'verify -DskipUnitTests -Parq-wildfly-swarm '
                    }
                    post {
                        always {
                            junit allowEmptyResults: true, testResults: '**/target/failsafe-reports/*.xml'
                        }
                    }
                }
            }
        }

        stage('Statical Code Analysis') {
            agent { label 'docker' } // Require a build executor with docker

            steps {
                withSonarQubeEnv('sonarcloud.io') {
                    mvn "$SONAR_MAVEN_GOAL -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=$SONAR_AUTH_TOKEN " +
                            // Here, we could define e.g. sonar.organization, needed for sonarcloud.io
                            "$SONAR_EXTRA_PROPS " +
                            // Additionally needed when using the branch plugin (e.g. on sonarcloud.io)
                            "-Dsonar.branch.name=$BRANCH_NAME -Dsonar.branch.target=master"
                }
            }
        }

        stage('Quality Gate') {
            steps {
                waitForQualityGateAndSetResult()
            }
        }
    }

    post {
        always {
            node('') {
                mailIfStatusChanged env.EMAIL_RECIPIENTS
            }
        }
    }
}

void createPipelineTriggers() {
    script {
        def triggers = []
        if (env.BRANCH_NAME == 'master') {
            // Run a nightly only for master
            triggers = [cron('H H(0-3) * * 1-5')]
        }
        properties([
                pipelineTriggers(triggers)
        ])
    }
}

void waitForQualityGateAndSetResult() {
    timeout(time: 2, unit: 'MINUTES') {
        def qg = waitForQualityGate()
        if (qg.status != 'OK') {
            echo "Pipeline unstable due to quality gate failure: ${qg.status}"
            currentBuild.result = 'UNSTABLE'
        }
    }
}
