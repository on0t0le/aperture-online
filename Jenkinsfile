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
    post {
        always {
            echo 'One way or another, I have finished'
            sh 'docker ps -q -f status=exited | xargs --no-run-if-empty docker rm'
            sh 'docker images -q -f dangling=true | xargs --no-run-if-empty docker rmi'
            sh 'docker volume ls -qf dangling=true | xargs -r docker volume rm'
            deleteDir()
        }
    }
}