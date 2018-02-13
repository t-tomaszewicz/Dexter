pipeline {
  agent any
  tools {
        maven 'apache-maven-3.0.1' 
        gradle 'gradle-4.5.1'
  }
  stages {
    stage('Echo') {
      steps {
        echo 'Hello world'
        echo 'second message'
      }
    }
    stage('Example') {
    steps {
        sh 'gradle build'
    }
    }
  }
}
