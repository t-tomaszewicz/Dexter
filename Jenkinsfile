pipeline {
  agent any
  stages {
    stage('Initialize') {
      steps {
        echo 'Initialize'
      }
    }
    stage('Build') {
      parallel {
        stage('Build') {
          steps {
            sh 'echo Build'
          }
        }
        stage('Performance') {
          steps {
            sh 'echo Performance'
          }
        }
      }
    }
    stage('Stage2') {
      steps {
        sleep 120
      }
    }
  }
}