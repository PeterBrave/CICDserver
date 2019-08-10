

node{
        stage('Deploy'){
            sh 'kubectl create deployment cicd --image=zxpwin/cicd_test_docker_1'
            sh 'kubectl expose deployment cicd --port=8080 --type=NodePort --nodePort=8080'
       }



