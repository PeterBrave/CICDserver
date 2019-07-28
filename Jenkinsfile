pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn package';
            }
        }
    
        stage('单元测试') {
            steps {
            sh 'mvn clean test'
            }
}
        
    }
}
