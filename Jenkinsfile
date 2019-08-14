/*Setting variables*/
def environment_docker_name = 'environment-image'            // The name of slave image
def tag_environment_docker_name = "zxpwin/environment-image" //The tag of slave image, zxpwin stands for the name of docker hub
def deploy_docker_name = "cicd-test-docker"                  // The name of deployment image
def tag_deploy_docker_name = "zxpwin/cicd-test-docker"     // The tag of  deployment image
def deploy_project_name = "cicd-service"

/*Start the slave, build and deploy the project*/
podTemplate(
    containers: [containerTemplate(name: 'maven', image: "maven", ttyEnabled: true, command: 'cat')], 
    //volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
	volumes: [hostPathVolume(hostPath: '/root/data/', mountPath: '/root/data/')],
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
		sh "cp -r /home/jenkins/agent/workspace/ /root/data/"
            }
	    stage('Unit test') {         
        	sh 'mvn test'
		//sh 'mvn war:war'
	    }
	}
    }
}
podTemplate(
    containers: [containerTemplate(name: 'sonarscanner', image: "aexea/sonarscanner", ttyEnabled: true, command: 'cat')], 
    //volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
	volumes: [hostPathVolume(hostPath: '/root/data/', mountPath: '/opt/code')],
	namespace: 'kube-jenkins'
){
    node(POD_LABEL) {
        container('sonarscanner') {
	    stage('Scan') {
        	echo "starting codeAnalyze with SonarQube......"
		environment {
             		Sonar_ACCESS_KEY_ID     = credentials('sonar-secret-key-id')
       		}
		sh "apt-get update -y"
		sh "apt-get install -y maven"
        	withSonarQubeEnv('sonarqube-server') {
            	//注意这里withSonarQubeEnv()中的参数要与之前SonarQube servers中Name的配置相同
            		sh ' mvn sonar:sonar -Dsonar.projectKey=test3 -Dsonar.host.url=http://52.34.18.46:9000 -Dsonar.login= $Sonar_ACCESS_KEY_ID '            
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
