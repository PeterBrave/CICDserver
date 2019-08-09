podTemplate {
    node(POD_LABEL) {
       stage('Clone'){
          /*拉取代码*/
          checkout ([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [],
          submoduleCfg: [], userRemoteConfigs: [[credentialsId:  '96ce8238-69cc-4acf-b2e9-ae6bb3818112',
          url: 'https://github.com/PeterBrave/CICDserver.git']]])
          }

      stage('Build') {
        sh 'mvn package'
        //sh 'mvn war:war'
      }
    }
}




