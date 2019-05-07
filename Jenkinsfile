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
                    def files = sh(script:'ls -l', returnStdout:true)
                    echo "Here is files:"
                    echo "${files}"
                }
            }
        }
    }
}
//some comments