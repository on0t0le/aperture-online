pipeline {
    stages{
        agent{
            node {
                label: 'slave1'
            }
            stage('Test'){
                echo 'Hello from slave!'
            }
        }
    }
}