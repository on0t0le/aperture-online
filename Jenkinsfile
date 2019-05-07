pipeline {
    agent {
        node {
            label 'slave1'
        }
    }
    stages {
        stage ('git'){
            steps {
                git branch: 'master', url: 'https://github.com/on0t0le/aperture-online.git'
            }
        }

        stage ('List files') {
            steps {
                script {
                    def files = sh(script:'ls -la', returnStdout:true)
                    echo "Here is files:"
                    echo "${files}"
                }
            }
        }

        stage ('Build container') {
            steps {
                script {
                    sh 'docker build -t test .'
                }
            }
        }
    }
}