pipeline {
    agent any

    stages {
        stage('单元测试') {
            steps {
            sh "mvn clean surefire-report:report"
            }
}
        
    }
}
