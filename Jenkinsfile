pipeline {
    agent any

    stages {
        stage('单元测试') {
            steps {
            junit 'target/surefire-reports/*.xml'
            }
}
        
    }
}
