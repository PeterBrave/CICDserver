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
	namespace: 'kube-jenkins'
){
    node(POD_LABEL) {
    	container('environment') {
        stage("Environment setup"){
	    /*Dockerfile*/
            sh 'echo "FROM centos \n RUN yum update -y && curl -LO https://storage.googleapis.com/kubernetes-release/release/v1.14.0/bin/linux/amd64/kubectl && chmod +x kubectl && mv kubectl /usr/local/bin/kubectl && yum install maven -y && yum install wget -y && wget https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo -O /etc/yum.repos.d/docker-ce.repo && yum -y install docker-ce-18.06.1.ce-3.el7" > Dockerfile'
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
/*Start the slave, build and deploy the project*/
podTemplate(
    containers: [containerTemplate(name: 'maven', image: "${tag_environment_docker_name}", ttyEnabled: true, command: 'cat')], 
    volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
	namespace: 'kube-jenkins'
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
            stage('Build Docker'){
  		/*Dockerfile*/
		//sh ' echo "FROM tomcat \n COPY /target/*.war /usr/local/tomcat/webapps/ " > Dockerfile'
  		sh ' echo "FROM centos \n RUN yum update -y && yum install -y java && yum install -y wget && mkdir /usr/share/tomcat && cd /usr/share/tomcat && wget http://apache.mirrors.ionfish.org/tomcat/tomcat-8/v8.5.43/bin/apache-tomcat-8.5.43.tar.gz && tar -zxf apache-tomcat-8.5.43.tar.gz && /usr/share/tomcat/apache-tomcat-8.5.43/bin/catalina.sh start \n COPY /target/*.jar /usr/share/tomcat/apache-tomcat-8.5.43/webapps \nENTRYPOINT ["java", "-jar", "/usr/share/tomcat/apache-tomcat-8.5.43/webapps/cicd-0.0.1-Beta.jar"] " > Dockerfile'
		/*Build docker*/
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
            	//sh "kubectl delete service/${deploy_project_name} -n kube-jenkins"
            	//sh "kubectl delete deployment.apps/${deploy_project_name} -n kube-jenkins"
		/*Redeployed project*/
		sh "kubectl create deployment ${deploy_project_name} --image=${tag_deploy_docker_name}"
		sh "kubectl expose deployment ${deploy_project_name} --port=8082 --type=NodePort"
            }
       }
   } 
}
