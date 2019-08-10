podTemplate(
    containers: [containerTemplate(name: 'maven', image: 'maven-docker-centos', ttyEnabled: true, command: 'cat')], 
    volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')]
      //       hostPathVolume(hostPath: '/data/', mountPath: '${JENKINS_HOME}/'),
        //      hostPathVolume(hostPath: '/usr/bin/docker', mountPath: '/usr/bin/docker')
           //  ]
    //volumes: [hostPathVolume(hostPath: '/var/data/', mountPath: '/home/jenkins/agent/workspace')]  
){
    node(POD_LABEL) {
            container('maven') {
                stage('Clone') {
                     checkout ([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [],
          submoduleCfg: [], userRemoteConfigs: [[credentialsId:  '96ce8238-69cc-4acf-b2e9-ae6bb3818112',
          url: 'https://github.com/PeterBrave/CICDserver.git']]]) 
                }
                    //sh 'docker version'
                stage('Build'){    
                    sh 'mvn package'
                }
                stage('Build Docker'){
                    
                    /*构建镜像*/
                    sh 'docker build -t cicd_test_docker .'
                    /*推送镜像*/
                    sh 'docker tag cicd_test_docker zxpwin/cicd_test_docker_1'
                    sh 'docker login --username zxpwin --password=yNJL4CcAa42yM72 '
                    sh 'docker push zxpwin/cicd_test_docker_1'
                }
            }
        } 
}



