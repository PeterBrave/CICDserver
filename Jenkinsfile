node('build_node'){
    stage('Clone'){
    /*????*/
    checkout ([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [],
    submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/PeterBrave/CICDserver.git']]])
    }

    stage('Build') {
    sh 'mvn package'
    //sh 'mvn war:war'
    }

    stage('Unit test') {
    sh 'mvn package'
    //sh 'mvn war:war'
    }

    stage('Scan') {
    echo "starting codeAnalyze with SonarQube......"
    environment {
    Sonar_ACCESS_KEY_ID     = credentials('sonar-secret-key-id')
    }
    withSonarQubeEnv('sonarqube-server') {
    //????withSonarQubeEnv()????????SonarQube servers?Name?????
    sh ' mvn sonar:sonar -Dsonar.projectKey=test3 -Dsonar.host.url=http://52.34.18.46:9000 -Dsonar.login= $Sonar_ACCESS_KEY_ID '
    }
    script {
        timeout(1) {
        //????????1???????????????
        //??sonar webhook????pipeline??????????????pipeline??fail
        def qg = waitForQualityGate('sonarqube-server')
        //?????waitForQualityGate()?????????SonarQube servers?Name?????
        if (qg.status != 'OK') {
            error "???Sonarqube???????????????failure"
            }
        }
    }

    }
}

    node('build_node'){
        stage('Build Docker'){
        echo 'build docker'
        environment {
        Dockerhub_ACCESS_KEY_ID     = credentials('dockerhub-secret-id')
        }
        /*????*/
        sh 'docker build -t cicd_test_docker .'
        /*????*/
        sh 'docker tag cicd_test_docker zxpwin/cicd_test_docker'
        sh 'docker login --username zxpwin --password=yNJL4CcAa42yM72 '
        sh 'docker push zxpwin/cicd_test_docker'
        /*docker.withRegistry('https://registry-1.docker.io/v2/', 'dockerhub-secret-id') {
            app = docker.build("cicd_test_docker", ".")
            app.push()
            }*/
        }
    }

    node('deploy_node'){
    stage('Deploy'){
    echo 'Deploy'
    sh 'docker pull zxpwin/cicd_test_docker'
    sh 'docker run --privileged=true -itd -p 8080:8080 zxpwin/cicd_test_docker:latest /usr/sbin/init'
    }
}
