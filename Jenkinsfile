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
                createVersion()

                mvn "clean install -DskipTests -Parq-wildfly-swarm -Drevision=${versionName}"
                archiveArtifacts '**/target/*.*ar'
            }
        }

        stage('Tests') {
            parallel {
                stage('Unit Test') {
                    steps {
                        mvn "test -Drevision=${versionName}"
                    }
                }
                stage('Integration Test') {
                    when { expression { return isNightly() } }
                    steps {
                        mvn "verify -DskipUnitTests -Parq-wildfly-swarm -Drevision=${versionName}"
                    }
                }
            }
        }

        stage('Statical Code Analysis') {
            steps {
                analyzeWithSonarQubeAndWaitForQualityGoal()
            }
        }

        stage('Deploy') {
            when { expression { return currentBuild.currentResult == 'SUCCESS' } }

            steps {
                script {

                    // Comment in and out some things so this deploys branch 11-x for this demo.
                    // In real world projects its good practice to deploy only develop and master branches
                    if (env.BRANCH_NAME == "master") {
                        //deployToKubernetes(versionName, 'kubeconfig-prod', getServiceIp('kubeconfig-prod'))
                    } else { //if (env.BRANCH_NAME == 'develop') {
                        deployToKubernetes(versionName, 'kubeconfig-staging', getServiceIp('kubeconfig-staging'))
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

String createVersion() {
    // E.g. "201708140933"
    String versionName = "${new Date().format('yyyyMMddHHmm')}"

    if (env.BRANCH_NAME != "master") {
        versionName += '-SNAPSHOT'
    }
    echo "Building version ${versionName} on branch ${env.BRANCH_NAME}"
    currentBuild.description = versionName
    env.versionName = versionName
}

void deployToKubernetes(String versionName, String credentialsId, String hostname) {

    String dockerRegistry = 'us.gcr.io/ces-demo-instances'
    String imageName = "${dockerRegistry}/kitchensink:${versionName}"

    docker.withRegistry("https://${dockerRegistry}", 'docker-us.gcr.io/ces-demo-instances') {
        docker.build(imageName, '.').push()
    }

    withCredentials([file(credentialsId: credentialsId, variable: 'kubeconfig')]) {

        withEnv(["IMAGE_NAME=${imageName}"]) {

            kubernetesDeploy(
                    credentialsType: 'KubeConfig',
                    kubeConfig: [path: kubeconfig],
                    configs: 'k8s/deployment.yaml',
                    enableConfigSubstitution: true
            )
        }
    }

    timeout(time: 3, unit: 'MINUTES') {
        waitUntil {
            sleep(time: 10, unit: 'SECONDS')
            isVersionDeployed(versionName, "http://${hostname}/rest/version")
        }
    }
}

boolean isVersionDeployed(String expectedVersion, String versionEndpoint) {
    def deployedVersion = sh(returnStdout: true, script: "curl -s ${versionEndpoint}").trim()
    echo "Deployed version returned by ${versionEndpoint}: ${deployedVersion}. Waiting for ${expectedVersion}."
    return expectedVersion == deployedVersion
}

void analyzeWithSonarQubeAndWaitForQualityGoal() {
    withSonarQubeEnv('sonarcloud.io') {
        mvn "${SONAR_MAVEN_GOAL} -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_AUTH_TOKEN} " +
                // Here, we could define e.g. sonar.organization, needed for sonarcloud.io
                "${SONAR_EXTRA_PROPS} " +
                // Addionally needed when using the branch plugin (e.g. on sonarcloud.io)
                "-Dsonar.branch.name=${BRANCH_NAME} -Dsonar.branch.target=master"
    }
    timeout(time: 10, unit: 'MINUTES') { // Normally, this takes only some ms. sonarcloud.io might take minutes, though :-(
        def qg = waitForQualityGate()
        if (qg.status != 'OK') {
            echo "Pipeline unstable due to quality gate failure: ${qg.status}"
            currentBuild.result = 'UNSTABLE'
        }
    }
}

String getServiceIp(String kubeconfigCredential) {

    withCredentials([file(credentialsId: kubeconfigCredential, variable: 'kubeconfig')]) {

        String serviceName = 'kitchensink' // See k8s/service.yaml

        // Using kubectl is so much easier than plain REST via curl (parsing info from kubeconfig is cumbersome!)
        return sh(returnStdout: true, script:
                "docker run -v ${kubeconfig}:/root/.kube/config lachlanevenson/k8s-kubectl:v1.9.5" +
                        " get svc ${serviceName}" +
                        ' |  awk \'{print $4}\'  | sed -n 2p'
                ).trim()
    }
}