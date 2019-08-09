podTemplate(
    containers: [containerTemplate(name: 'maven', image: 'maven:3.3.9-jdk-8-alpine', ttyEnabled: true, command: 'cat')], 
    volumes: [hostPathVolume(hostPath: '/data', mountPath: '/var/jenkins/')]
){
    node(POD_LABEL) {
        stage('Get a Maven project') {
            container('maven') {
                stage('Build a Maven project') {
                     checkout ([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [],
          submoduleCfg: [], userRemoteConfigs: [[credentialsId:  '96ce8238-69cc-4acf-b2e9-ae6bb3818112',
          url: 'https://github.com/PeterBrave/CICDserver.git']]])
                    sh 'mvn package'
                }
            }
        }
    }
}



