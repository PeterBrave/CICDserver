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
      echo "starting unitTest......"
      //注入jacoco插件配置,clean test执行单元测试代码. All tests should pass.
      sh "mvn org.jacoco:jacoco-maven-plugin:prepare-agent -f pom.xml clean test -Dautoconfig.skip=true -Dmaven.test.skip=false -Dmaven.test.failure.ignore=true"
      junit '**/target/surefire-reports/*.xml'
      //配置单元测试覆盖率要求，未达到要求pipeline将会fail,code coverage.LineCoverage>20%.
      jacoco changeBuildStatus: true, maximumLineCoverage: 20
    }
}
        
    }
}
