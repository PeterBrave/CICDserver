pipeline {
    agent any

    stages {
        stage('单元测试') {
            steps {
            sh "mvn clean install"   //Maven构建操作
            sh "mvn test"
            junit allowEmptyResults: true, keepLongStdio: true, testResults: 'target/surefire-reports/*.xml'
            }
}
        
    }
}
