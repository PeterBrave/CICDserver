node('zxp_node3'){
//node('zxp_slave'){

    /*stage('Environment'){
		sh 'echo "FROM jenkins/ssh-slave \n RUN apt-get update && apt-get install -y sudo && apt-get install -y maven " > Dockerfile'
		sh 'docker build -t environment .'
		sh 'rm -rf Dockerfile'
		sh 'docker run --privileged=true -itd  environment:latest /bin/bash'
	}*/
        
     stage('Pull from git'){
        /*拉取代码*/
        checkout ([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [],
		submoduleCfg: [], userRemoteConfigs: [[credentialsId:  '96ce8238-69cc-4acf-b2e9-ae6bb3818112', 
        url: 'https://github.com/PeterBrave/CICDserver.git']]])
     }
        
    stage('Build') {         
        sh 'mvn package'
	//sh 'mvn war:war'
        //sh 'mvn -version'
        sh 'java -version'         
        //sh 'cp target/*.war /home/jenkins/'
    }
    /*
    stage('Scan') {
        echo "starting codeAnalyze with SonarQube......"
        withSonarQubeEnv('sonarqube-server') {
            //注意这里withSonarQubeEnv()中的参数要与之前SonarQube servers中Name的配置相同
            sh "mvn sonar:sonar -Dsonar.projectKey=test -Dsonar.host.url=http://52.34.18.46:9000 -Dsonar.login=3f17ab190f4989204cd76e0d8b0211bd8c85659c"            
        }
        script {
            timeout(1) {
                //这里设置超时时间1分钟，不会出现一直卡在检查状态
                //利用sonar webhook功能通知pipeline代码检测结果，未通过质量阈，pipeline将会fail
                def qg = waitForQualityGate('sonarqube-server')
                //注意：这里waitForQualityGate()中的参数也要与之前SonarQube servers中Name的配置相同
                if (qg.status != 'OK') {
                    error "未通过Sonarqube的代码质量阈检查，请及时修改！failure: ${qg.status}"
                }
            }
        }
            
    }*/
}

node('zxp_node3'){ 
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
        //sh 'mkdir /usr/share/tomcat'
        sh 'docker pull zxpwin/zxp_test_docker_1'
        sh 'docker run --privileged=true -itd -p 8082:8082 zxpwin/zxp_test_docker_1:latest /usr/sbin/init bash'
        sh '/bin/bash'
	sh 'java -jar /usr/share/tomcat/webapps/*.jar'
	//sh 'mysqladmin -uroot password 'newpassword' '
        ///usr/sbin/init -v /var/run/docker.sock:/var/run/docker.sock   -v /usr/share/tomcat:/usr/share/tomcat
        /*sh 'systemctl enable tomcat'
        sh 'systemctl start tomcat'
        sh 'systemctl status tomcat'*/
        }
}
