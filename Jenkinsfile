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

        stage('Statical Code Analysis') {
            steps {
                analyzeWithSonarQubeAndWaitForQualityGoal()
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

void analyzeWithSonarQubeAndWaitForQualityGoal() {
    withSonarQubeEnv('sonarcloud.io') {
        mvn "$SONAR_MAVEN_GOAL -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=$SONAR_AUTH_TOKEN " +
                // Here, we could define e.g. sonar.organization, needed for sonarcloud.io
                "$SONAR_EXTRA_PROPS " +
                // Addionally needed when using the branch plugin (e.g. on sonarcloud.io)
                "-Dsonar.branch.name=$BRANCH_NAME -Dsonar.branch.target=master"
    }
    timeout(time: 2, unit: 'MINUTES') {
        def qg = waitForQualityGate()
        if (qg.status != 'OK') {
            echo "Pipeline unstable due to quality gate failure: ${qg.status}"
            currentBuild.result = 'UNSTABLE'
        }
    }
}