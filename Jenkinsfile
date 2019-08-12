def environment_docker_name = "environment-image"
def tag_environment_docker_name = "zxpwin/environment-image"

podTemplate(
    containers: [containerTemplate(name: 'environment', image: 'zxpwin/kubectl-centos', ttyEnabled: true, command: 'cat')], 
    volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
	// serviceAccount: 'jenkins2',
	namespace: 'kube-jenkins'
){
    node(POD_LABEL) {
    	container('environment') {
        stage("Environment setup"){
            sh ' echo "FROM centos \n RUN yum update -y && curl -LO https://storage.googleapis.com/kubernetes-release/release/v1.14.0/bin/linux/amd64/kubectl && chmod +x kubectl && mv kubectl /usr/local/bin/kubectl && yum install maven -y && yum install wget -y && wget https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo -O /etc/yum.repos.d/docker-ce.repo && yum -y install docker-ce-18.06.1.ce-3.el7" > Dockerfile'

            sh 'docker build -t ${environment_docker_name} -f Dockerfile'
            sh 'docker tag ${environment_docker_name} ${tag_environment_docker_name}'
            withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                sh "docker login -u ${dockerHubUser} -p ${dockerHubPassword}"
                sh "docker push ${tag_environment_docker_name}"
            }
            sh 'rm -rf Dockerfile'
        }
    	}
	}
}

podTemplate(
    containers: [containerTemplate(name: 'maven', image: 'zxpwin/environment-image', ttyEnabled: true, command: 'cat')], 
    volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
	namespace: 'kube-jenkins'
){
    node(POD_LABEL) {
        container('maven') {
            stage('Clone') {
                checkout ([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [],
                           submoduleCfg: [], userRemoteConfigs: [[credentialsId:  '96ce8238-69cc-4acf-b2e9-ae6bb3818112',
                                                                  url: 'https://github.com/PeterBrave/CICDserver.git']]]) 
            }
                  
            stage('Build'){    
               sh 'mvn package'
            }
            stage('Build Docker'){
  
  				sh ' echo "FROM centos \n RUN yum update -y && yum install -y java && yum install -y wget && mkdir /usr/share/tomcat && cd /usr/share/tomcat && wget http://apache.mirrors.ionfish.org/tomcat/tomcat-8/v8.5.43/bin/apache-tomcat-8.5.43.tar.gz && tar -zxf apache-tomcat-8.5.43.tar.gz && /usr/share/tomcat/apache-tomcat-8.5.43/bin/catalina.sh start \n COPY /target/*.war /usr/share/tomcat/apache-tomcat-8.5.43/webapps " > Dockerfile'

                sh 'docker build -t cicd_test_docker .'

               	sh 'docker tag cicd_test_docker zxpwin/cicd-test-docker'
               	withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                   sh "docker login -u ${dockerHubUser} -p ${dockerHubPassword}"
                   sh "docker push zxpwin/cicd-test-docker"
               	}
               	sh 'rm -rf Dockerfile'
             
            }
            
            stage('Deploy'){
            	sh 'kubectl delete service/cicd -n kube-jenkins'
            	sh 'kubectl delete deployment.apps/cicd -n kube-jenkins'
                sh 'kubectl create deployment cicd --image=zxpwin/cicd-test-docker'
                sh 'kubectl expose deployment cicd --port=8082 --type=NodePort'
            }
       }
   } 
}
