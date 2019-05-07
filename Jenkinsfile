pipeline {
    agent none
    stages {
        stage ('git'){
            agent {
                node {
                    label 'master'
                }
            }
            steps {
                git branch: 'master', url: 'https://github.com/on0t0le/aperture-online.git'
            }
        }

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