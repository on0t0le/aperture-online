pipeline {
    agent none
    stages {
        stage('Build image'){
            agent {
                dockerfile {
                    label 'slave1'
                    additionalBuildArgs "-t test"
                }
            }
            steps{
                sh 'nginx -v'
            }
        }
    }
}