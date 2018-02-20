pipeline {
  agent any
  stages {
    stage('Echo') {
      parallel {
        stage('Echo') {
          steps {
            echo 'Hello world'
          }
        }
        stage('Echo2') {
          steps {
            sh 'echo "Message2"'
          }
        }
      }
    }
    stage('Build') {
      steps {
        dir(path: 'project') {
          sh 'gradle build -x test'
        }
        
      }
    }
    stage('Finalize') {
      steps {
        sh 'echo "Finalized"'
      }
    }
  }
  tools {
    gradle 'gradle-4.5.1'
  }
}