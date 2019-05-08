#!/usr/bin/env groovy
node('slave1'){

    stage('Checkot'){
        checkout scm
    }

    // stage('List Docker'){
    //     def containerList = sh(script: 'docker ps', returnStdout: true)
    //     echo "You have this containers"
    //     echo "${containerList}"
    // }

    stage('Pull website from git'){
       checkout(changelog: false, poll: false, scm: [$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'apertureweb']], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/on0t0le/aperturesciense-website.git']]])
    }

    stage('List workspace'){
        def wsList = sh(script:'ls -la',returnStdout:true)
        echo "Workspace contains: ${wsList}"
    }

    stage('Docker build'){
        sh "docker build -t myregistry.com:5000/admin/webapp:${BUILD_NUMBER} apertureweb/."
    }

    stage('Deploy to registry'){
        def registryServer = 'myregistry.com:5000'
        withCredentials([usernamePassword(credentialsId: 'docker_cred', passwordVariable: 'password', usernameVariable: 'username')]) {
            sh "docker login -u${username} -p${password} ${registryServer}"
        }
        sh "docker push myregistry.com:5000/admin/webapp:${BUILD_NUMBER}"
    }

    stage('Cleanup'){
        sh 'docker ps -q -f status=exited | xargs --no-run-if-empty docker rm'
        sh 'docker images -q -f dangling=true | xargs --no-run-if-empty docker rmi'
        sh 'docker image ls -q | xargs --no-run-if-empty docker image rm -f'
        sh 'docker volume ls -qf dangling=true | xargs -r docker volume rm'
        deleteDir()
    }
}

timeout(time: 1, unit: 'MINUTES'){
    node('master'){
        try {

            stage('Pull web-container to prod'){
                def registryServer = 'myregistry.com:5000'
                withCredentials([usernamePassword(credentialsId: 'docker_cred', passwordVariable: 'password', usernameVariable: 'username')]) {
                    sh "docker login -u${username} -p${password} ${registryServer}"
                }
                sh 'docker ps -q | xargs --no-run-if-empty docker rm -f'
                sh "docker pull myregistry.com:5000/admin/webapp:${BUILD_NUMBER}"
            }

            stage('Run web-app'){
                sh "docker r -p 8080:80 myregistry.com:5000/admin/webapp:${BUILD_NUMBER}"
            }

            stage('Test webapp'){
                sh (script:'curl http://localhost:8080')
            }

            stage('Cleanup'){
                deleteDir()
            }
        } catch (err) {
            echo 'Something failed, I should sound the klaxons!'
            throw err
        }
    }
}