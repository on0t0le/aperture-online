pipeline {
    agent none
    stages {
        stage ('Test echo') {
        when { triggeredBy 'SCMTrigger' }
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
//comments