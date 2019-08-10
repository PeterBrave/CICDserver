podTemplate(
    containers: [containerTemplate(name: 'maven', image: 'maven:3.3.9-jdk-8-alpine', ttyEnabled: true, privileged: true, command: 'cat')], 
    volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock'),
             hostPathVolume(hostPath: '/data/', mountPath: '/home/jenkins/agent/*'),
              hostPathVolume(hostPath: '/usr/bin/docker', mountPath: '/bin/docker')
             ]
    //volumes: [hostPathVolume(hostPath: '/var/data/', mountPath: '/home/jenkins')]  
){
    node(POD_LABEL) {
        stage('Build') {
            container('maven') {
                stage('Clone') {
                     checkout ([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [],
          submoduleCfg: [], userRemoteConfigs: [[credentialsId:  '96ce8238-69cc-4acf-b2e9-ae6bb3818112',
          url: 'https://github.com/PeterBrave/CICDserver.git']]]) 
                    sh 'docker version'
                    //sh 'mvn package'
                    
                }
            }
        }
    }
}



