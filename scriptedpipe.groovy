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

    stage('Cleanup'){
        sh 'docker ps -q -f status=exited | xargs --no-run-if-empty docker rm'
        sh 'docker images -q -f dangling=true | xargs --no-run-if-empty docker rmi'
        sh 'docker image ls -q | xargs --no-run-if-empty docker image rm -f'
        sh 'docker volume ls -qf dangling=true | xargs -r docker volume rm'
        deleteDir()
    }
}