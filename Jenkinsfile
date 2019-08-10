podTemplate(
    containers: [containerTemplate(name: 'maven', image: 'zxpwin/citrix-docker-mvn-centos', ttyEnabled: true, command: 'cat')], 
    volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
      //       hostPathVolume(hostPath: '/data/', mountPath: '${JENKINS_HOME}/'),
        //      hostPathVolume(hostPath: '/usr/bin/docker', mountPath: '/usr/bin/docker')
           //  ]
    //volumes: [hostPathVolume(hostPath: '/var/data/', mountPath: '/home/jenkins/agent/workspace')]  
 serviceAccount: 'jenkins2'){
   
        stage('Deploy'){
            sh 'kubectl apply -f k8s.yaml'
        }
    } 
    
    }




