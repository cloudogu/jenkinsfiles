#!groovy

@Library('github.com/triologygmbh/jenkinsfile@ad12c8a9') _

catchError {

    node('docker') { // Require a build executor with docker (label)

        properties([
                pipelineTriggers(createPipelineTriggers()),
                disableConcurrentBuilds(),
                buildDiscarder(logRotator(numToKeepStr: '10'))
        ])

        stage('Checkout') {
            checkout scm
        }

        stage('Build') {
            mvn 'clean install -DskipTests'
            archiveArtifacts '**/target/*.*ar'
        }

        parallel(
                unitTest: {
                    stage('Unit Test') {
                        catchError {
                            mvn 'test'
                        }
                        junit allowEmptyResults: true, testResults: '**/target/surefire-reports/TEST-*.xml'
                    }
                },
                integrationTest: {
                    stage('Integration Test') {
                        if (isTimeTriggeredBuild()) {
                            catchError {
                                mvn 'verify -DskipUnitTests -Parq-wildfly-swarm '
                            }
                            junit allowEmptyResults: true, testResults: '**/target/failsafe-reports/*.xml'
                        }
                    }
                }
        )


        stage('Statical Code Analysis') {
            withSonarQubeEnv('sonarcloud.io') {
                mvn "$SONAR_MAVEN_GOAL -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=$SONAR_AUTH_TOKEN " +
                        // Additionally needed for sonarcloud.io (EXTRA_PROPS would be the place to configure sonar.organization)
                        "$SONAR_EXTRA_PROPS " +
                        // Addionally needed when using the branch plugin (e.g. on sonarcloud.io)
                        "-Dsonar.branch.name=$BRANCH_NAME -Dsonar.branch.target=master"
            }
        }
    }

    stage('Quality Gate') {
        if (currentBuild.currentResult == 'SUCCESS') {
            timeout(time: 2, unit: 'MINUTES') {
                def qg = waitForQualityGate()
                if (qg.status != 'OK') {
                    unstable("Pipeline unstable due to quality gate failure: ${qg.status}")
                }
            }
        }
    }
}

node {
    mailIfStatusChanged env.EMAIL_RECIPIENTS
}


def createPipelineTriggers() {
    if (env.BRANCH_NAME == 'master') {
        // Run a nightly only for master
        return [cron('H H(0-3) * * 1-5')]
    }
    return []
}