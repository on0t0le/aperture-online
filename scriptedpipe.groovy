#!/usr/bin/env groovy
node('slave1'){

    stage('Checkot'){
        checkout scm
    }

    stage('List Docker'){
        def containerList = sh(script: 'docker ps', returnStdout: true)
        echo "You have this containers"
        echo "${containerList}"
    }

    stage('List workspace'){
        def wsList = sh(script:'ls -la',returnStdout:true)
        echo "Workspace contains: ${wsList}"
    }

    stage('Docker build'){
        sh 'docker build -t myregistry.com/admin/webapp .'
    }

    stage('Deploy to registry'){
        withDockerRegistry(credentialsId: '61581225-5c57-46e5-9e9b-ddd07ac0806b', url: 'myregistry.com:5000') {
            sh 'docker push myregistry.com/admin/webapp'
        }
    }

    stage('Cleanup'){
        sh 'docker ps -q -f status=exited | xargs --no-run-if-empty docker rm'
        sh 'docker images -q -f dangling=true | xargs --no-run-if-empty docker rmi'
        sh 'docker image ls -q | xargs --no-run-if-empty docker image rm -f'
        sh 'docker volume ls -qf dangling=true | xargs -r docker volume rm'
        deleteDir()
    }
}