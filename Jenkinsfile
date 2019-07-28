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
                sh 'mvn test'
            }
        }
        
    }
}
