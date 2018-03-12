#!groovy

@Library('github.com/triologygmbh/jenkinsfile@f38945a') _

// Query outside of node, in order to get pending script approvals
//boolean isTimeTriggered = isTimeTriggeredBuild()

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
                        mvn 'test'
                    }
                },
                integrationTest: {
                    stage('Integration Test') {
                        if (isNightly()) {
                            mvn 'verify -DskipUnitTests -Parq-wildfly-swarm '
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
}

node {

    stage('Quality Gate') {
        if (currentBuild.currentResult == 'SUCCESS') {
            timeout(time: 2, unit: 'MINUTES') {
                def qg = waitForQualityGate()
                if (qg.status != 'OK') {
                    echo "Pipeline unstable due to quality gate failure: ${qg.status}"
                    currentBuild.result ='UNSTABLE'
                }
            }
        }
    }

    // Archive Unit and integration test results, if any
    junit allowEmptyResults: true,
            testResults: '**/target/surefire-reports/TEST-*.xml, **/target/failsafe-reports/*.xml'

    mailIfStatusChanged env.EMAIL_RECIPIENTS
}


def createPipelineTriggers() {
    if (env.BRANCH_NAME == 'master') {
        // Run a nightly only for master
        return [cron('H H(0-3) * * 1-5')]
    }
    return []
}

/**
 * Note that this requires the following script approvals by your jenkins administrator
 * (via https://JENKINS-URL/scriptApproval/):
 * <br/>
 * {@code method hudson.model.Run getCauses}
 * {@code method org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper getRawBuild}.
 * <br/><br/>
 * Note that that the pending script approvals only appear if this method is called <b>outside a {@code node}</b>
 * within the pipeline!
 *
 * @return {@code true} if the build was time triggered, otherwise {@code false}
 */
boolean isTimeTriggeredBuild() {
    for (Object currentBuildCause : currentBuild.rawBuild.getCauses()) {
        return currentBuildCause.class.getName().contains('TimerTriggerCause')
    }
    return false
}
