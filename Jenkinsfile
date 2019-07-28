pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn package';
            }
        }
    
        stage('Junit') {
            steps {
                junit allowEmptyResults: true, keepLongStdio: true, testResults: 'target/surefire-reports/*.xml'  //Junit插件收集单元测试结果
            }
        }
        
    }
}
