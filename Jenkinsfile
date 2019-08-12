//test4
podTemplate(
    containers: [containerTemplate(name: 'maven', image: 'zxpwin/kubectl-centos', ttyEnabled: true, command: 'cat')], 
    volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
      //       hostPathVolume(hostPath: '/data/', mountPath: '${JENKINS_HOME}/'),
        //      hostPathVolume(hostPath: '/usr/bin/docker', mountPath: '/usr/bin/docker')
           //  ]
    //volumes: [hostPathVolume(hostPath: '/var/data/', mountPath: '/home/jenkins/agent/workspace')]  
// serviceAccount: 'jenkins2',
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
                /*????????????*/
                sh 'docker build -t cicd_test_docker .'
                /*????????????*/
               sh 'docker tag cicd_test_docker zxpwin/cicd-test-docker'
               withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                   sh "docker login -u ${dockerHubUser} -p ${dockerHubPassword}"
                   sh "docker push zxpwin/cicd-test-docker"
               }
                //sh 'docker tag cicd_test_docker zxpwin/cicd_test_docker_1'
                //sh 'docker login --username zxpwin --password=yNJL4CcAa42yM72 '
                //sh 'docker push zxpwin/cicd_test_docker_1'
            }
            
            stage('Deploy'){
                sh 'kubectl create deployment cicd --image=zxpwin/cicd-test-docker'
                sh  'kubectl expose deployment cicd --port=8082 --type=NodePort'
  
            }
       }
   } 
}
