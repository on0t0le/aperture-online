pipeline {
    stages{
        stage ('Test echo'){
            agent{
                node {
                    label: 'slave1'
                }
            }
            steps{
                echo 'Hello from test!'
            }
        }
    }
}