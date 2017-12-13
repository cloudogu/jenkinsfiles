@Library('github.com/triologygmbh/jenkinsfile@f38945a') _

pipeline {
    agent { label 'docker' } // Require a build executor with docker

    options {
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    stages {
        stage('Build') {
            steps {
                createPipelineTriggers()
                mvn 'clean install -DskipTests'
                archiveArtifacts '**/target/*.*ar'
            }
        }

        stage('Tests') {
            parallel {
                stage('Unit Test') {
                    steps {
                        mvn 'test'
                    }
                }
                stage('Integration Test') {
                    when { expression { return isNightly() } }
                    steps {
                        mvn 'verify -DskipUnitTests -Parq-wildfly-swarm '
                    }
                }
            }
        }
    }

    post {
        always {
            // Archive Unit and integration test results, if any
            junit allowEmptyResults: true,
                    testResults: '**/target/surefire-reports/TEST-*.xml, **/target/failsafe-reports/*.xml'
            mailIfStatusChanged env.EMAIL_RECIPIENTS
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