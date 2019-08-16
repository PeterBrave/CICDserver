/*Setting variables*/
def environment_docker_name = 'environment-image'            // The name of slave image
def tag_environment_docker_name = "zxpwin/environment-image" //The tag of slave image, zxpwin stands for the name of docker hub
def deploy_docker_name = "cicd-test-docker"                  // The name of deployment image
def tag_deploy_docker_name = "zxpwin/cicd-test-docker"     // The tag of  deployment image
def deploy_project_name = "cicd-service"

/*Setup the environment of the slave*/
podTemplate(
    containers: [containerTemplate(name: 'environment', image: 'docker', ttyEnabled: true, command: 'cat')], 
    volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
	// serviceAccount: 'jenkins2',
	namespace: 'kube-jenkins',
	nodeSelector: "ip-172-26-14-103.ap-northeast-2.compute.internal"
){
    node(POD_LABEL) {
    	container('environment') {
        stage("Environment setup"){
	    /*Dockerfile*/
        sh 'echo "FROM centos \n RUN yum update -y && yum install -y java && yum install -y maven && curl -LO https://storage.googleapis.com/kubernetes-release/release/v1.14.0/bin/linux/amd64/kubectl && chmod +x kubectl && mv kubectl /usr/local/bin/kubectl && yum install wget -y && wget https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo -O /etc/yum.repos.d/docker-ce.repo && yum -y install docker-ce-18.06.1.ce-3.el7 && yum install -y unzip && mkdir /home/sonarqube/ && wget https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-4.0.0.1744-linux.zip && unzip -o sonar-scanner-cli-4.0.0.1744-linux.zip -d /home/sonarqube/" > Dockerfile'

            //sh 'echo "FROM centos \n RUN yum update -y && yum install -y wget && yum install -y unzip && yum install -y maven && cd / && cd  && mkdir /home/sonarqube/ && wget https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-4.0.0.1744-linux.zip && unzip -o sonar-scanner-cli-4.0.0.1744-linux.zip -d /home/sonarqube/" > Dockerfile'


	    /*Build docker*/
	    sh "docker build -t ${environment_docker_name} ."
	    /*Tag image*/
            sh "docker tag ${environment_docker_name} ${tag_environment_docker_name}"
	    /*Login the docker hub and push image to the hub*/
            withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                sh "docker login -u ${dockerHubUser} -p ${dockerHubPassword}"
                sh "docker push ${tag_environment_docker_name}"
            }
            sh "rm -rf Dockerfile"
        }
    	}
	}
}

podTemplate(
    containers: [containerTemplate(name: 'maven', image: "${tag_environment_docker_name}", ttyEnabled: true, command: 'cat')], 
    //volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
	volumes: [hostPathVolume(hostPath: '/root/data/', mountPath: '/root/data/')],
	namespace: 'kube-jenkins',
	nodeSelector: "ip-172-26-14-103.ap-northeast-2.compute.internal"
){
    node(POD_LABEL) {
        container('maven') {
	   /*Clone code form github*/
            stage('Clone') {
                checkout ([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [],
                           submoduleCfg: [], userRemoteConfigs: [[credentialsId:  '96ce8238-69cc-4acf-b2e9-ae6bb3818112',
                                                                  url: 'https://github.com/PeterBrave/CICDserver.git']]]) 
            }
            /*Build project*/
            stage('Build'){    
               sh "mvn package"
            }
	  stage('Unit test') { 
        	sh 'mvn test'
		sh "cp -r /home/jenkins/agent/workspace/ /root/data/"
	   }
	}
    }
}

podTemplate(
    containers: [containerTemplate(name: 'sonarscanner', image: "${tag_environment_docker_name}", ttyEnabled: true, command: 'cat')], 
    //volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
	volumes: [hostPathVolume(hostPath: '/root/data/', mountPath: '/root/data'),
		 hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
	namespace: 'kube-jenkins',
	nodeSelector: "ip-172-26-14-103.ap-northeast-2.compute.internal"
){
    node(POD_LABEL) {
		container("sonarscanner"){

		stage('Scan') {
        	echo "starting codeAnalyze with SonarQube......"
			sh "cp -r /root/data/workspace/cicdtest/*  /home/jenkins/agent/workspace/cicdtest/"
			sh "cp -r /root/data/workspace/cicdtest@tmp/*  /home/jenkins/agent/workspace/cicdtest@tmp/"
		environment {
             		Sonar_ACCESS_KEY_ID     = credentials('sonar-secret-key-id')
       		}
        	withSonarQubeEnv('sonarqube-server') {
            	//注意这里withSonarQubeEnv()中的参数要与之前SonarQube servers中Name的配置相同
            		sh ' mvn sonar:sonar -Dsonar.projectKey=cicd_test_2 -Dsonar.host.url=http://52.34.18.46:9000 -Dsonar.login= $Sonar_ACCESS_KEY_ID ' 
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
            
		}
        }
    }
}

podTemplate(
    containers: [containerTemplate(name: 'deploy', image: "${tag_environment_docker_name}", ttyEnabled: true, command: 'cat')], 
    //volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
    volumes: [hostPathVolume(hostPath: '/root/data/', mountPath: '/root/data'),
         hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
    namespace: 'kube-jenkins',
    nodeSelector: "ip-172-26-14-103.ap-northeast-2.compute.internal"
){
    node(POD_LABEL) {
        container("deploy"){

        stage('Build Docker'){
  		/*Dockerfile*/

		sh "cp -r /root/data/workspace/cicdtest/*  /home/jenkins/agent/workspace/cicdtest/"
		sh ' echo "FROM centos \n RUN yum update -y && yum install -y java && yum install -y wget &&  yum install -y tomcat && cd / && cd usr/share/tomcat/conf/ &&  rm -rf server.xml && wget https://raw.githubusercontent.com/PeterBrave/CICDserver/master/server.xml  && systemctl enable tomcat \n COPY /target/*.war /usr/share/tomcat/webapps/ \n ENTRYPOINT /usr/sbin/init" > Dockerfile'		/*Build docker*/
        	sh "docker build -t ${deploy_docker_name} ."
  		/*Tag image*/
		sh "docker tag ${deploy_docker_name} ${tag_deploy_docker_name}"
		/*Login the docker hub and push image to the hub*/
               	withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                   sh "docker login -u ${dockerHubUser} -p ${dockerHubPassword}"
		   sh "docker push ${tag_deploy_docker_name}"
               	}
               	sh "rm -rf Dockerfile"
             
            }
            
            stage('Deploy'){
		/*Delete the originally deployed project*/
            	sh "kubectl delete service/${deploy_project_name} -n kube-jenkins"
            	sh "kubectl delete deployment.apps/${deploy_project_name} -n kube-jenkins"
		/*Redeployed project*/
		sh "kubectl create deployment ${deploy_project_name} --image=${tag_deploy_docker_name}"
		sh "kubectl expose deployment ${deploy_project_name} --port=8082 --type=NodePort"
            }
       }
	}
}
