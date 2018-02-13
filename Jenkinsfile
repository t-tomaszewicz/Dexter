pipeline {
  agent any
  stages {
    stage('Echo') {
      steps {
        echo 'Hello world'
        echo 'second message'
      }
    }
    stage('Build') {
      steps {
        sh 'gradle build'
      }
    }
  }
  tools {
    maven 'apache-maven-3.0.1'
    gradle 'gradle-4.5.1'
  }
}