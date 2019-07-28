pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn package';
            }
        }
        stage('静态代码检查') {
            steps {
                echo "starting codeAnalyze with SonarQube......"
                withSonarQubeEnv('sonarqube-server') {
                    //注意这里withSonarQubeEnv()中的参数要与之前SonarQube servers中Name的配置相同
                 
                        sh "mvn sonar:sonar -Dsonar.projectKey=test -Dsonar.host.url=http://52.34.18.46:9000 -Dsonar.login=3f17ab190f4989204cd76e0d8b0211bd8c85659c"
                    
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
