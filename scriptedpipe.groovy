#!/usr/bin/env groovy
node('slave1'){

    stage('Checkot'){
        checkout scm
    }

    stage('Build Docker'){
        def containerList = sh(script: 'docker ps', returnStdout: true)
        echo "You have this containers"
        echo "${containerList}"
    }
}