pipeline {
    agent any

    tools {
        maven 'M3'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn -B package'
            }
        }
    }
}



