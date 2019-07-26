node('slave'){
        stage('Pull from git'){
            /*拉取代码*/
            checkout ([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [],
            submoduleCfg: [], userRemoteConfigs: [[credentialsId:  '2d68d4f8-a24c-44d4-a381-3894235b8b54', 
            url: 'https://github.com/zxpgo/lmb.git']]])
        }
        
        stage('Build') {
            sh 'mvn package';
        }
        
        stage('Build Docker'){

            echo 'build docker'
            /*构建镜像*/
            sh 'docker build -t zxp_test_docker_1 .'
            /*推送镜像*/
            sh 'docker tag zxp_test_docker_1 zxpwin/zxp_test_docker_1'
            sh 'docker login -u zxpwin -p yNJL4CcAa42yM72'
            sh 'docker push zxpwin/zxp_test_docker_1'
        }
}

node('zxp_node2'){
        stage('Deploy'){
            echo 'Deploy'
            sh 'docker pull zxpwin/jenskin-slave-zxp'
            sh 'docker run -d -p 8080:8080 -i -t  zxpwin/jenskin-slave-zxp  /bin/bash'
            sh 'systemctl tomcat start'
        }
}
