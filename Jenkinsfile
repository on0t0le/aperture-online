pipeline {
    agent none
    stages {
        stage ('Test echo') {
            agent {
                node {
                    label 'slave1'
                }
            }
            steps {
                script {
                    def containers = sh(script:'docker ps', returnStdout:true)
                    echo "Here is containers you have:"
                    echo "${containers}"
                }
            }
        }
    }
}