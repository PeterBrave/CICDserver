node('build_docker_node'){
    stage('Clone'){
        checkout ([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [],
        submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/${githubName}/${repo}.git']]])
    }

    stage('Build') {
        sh 'mvn package'
    }

    stage('Unit test') {
        sh 'mvn package'
    }

    stage('Scan') {
        echo "starting codeAnalyze with SonarQube......"
        environment {
            Sonar_ACCESS_KEY_ID = credentials('sonar-secret-key-id')
        }
        withSonarQubeEnv('sonarqube-server') {
            sh ' mvn sonar:sonar -Dsonar.projectKey=test3 -Dsonar.host.url=http://52.34.18.46:9000 -Dsonar.login= $Sonar_ACCESS_KEY_ID '
        }
        script {
            timeout(1) {
                //Use the sonar webhook function to notify the pipeline code detection result. If the quality threshold is not passed, the pipeline will fail.
                def qg = waitForQualityGate('sonarqube-server')
                if (qg.status != 'OK') {
                    error "Did not pass Sonarqube's code quality threshold check, please modify it in time! Failure"
                }
            }
        }
    }
}

node('build_docker_node'){
    stage('Build Docker'){
        echo 'build docker'
        environment {
            Dockerhub_ACCESS_KEY_ID = credentials('dockerhub-secret-id')
        }
        sh 'rm -rf Dockerfile'
        sh 'echo "FROM centos  \n RUN yum update -y && yum install -y wget && yum install -y java && mkdir /usr/share/tomcat  \n COPY /target/*.jar /usr/share/tomcat/ \n ENTRYPOINT java -jar /usr/share/tomcat/cicd-0.0.1-Beta.jar" > Dockerfile'
        sh 'docker build -t cicd_test_docker .'
        sh 'docker tag cicd_test_docker zxpwin/cicd_test_docker'
        withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
            sh "docker login -u $dockerHubUser -p $dockerHubPassword"
            sh "docker push "zxpwin/cicd_test_docker"
        }
    }
}

node('deploy_node'){
    stage('Deploy'){
        echo 'Deploy'
        sh 'docker pull zxpwin/cicd_test_docker'
        sh 'docker run --privileged=true -itd -p 8080:8080 zxpwin/cicd_test_docker:latest /usr/sbin/init'
    }
}