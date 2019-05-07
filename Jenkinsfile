pipeline {
    agent{
        node {
            label: 'slave1'
        }
        stages{
            stage('Test'){
                echo 'Hello from slave!'
            }
        }
    }
}