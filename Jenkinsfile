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
      parallel {
        stage('Build') {
          steps {
            sh 'cd project'            
            sh 'gradle build'
          }
        }
        stage('Compile') {
          steps {
            echo 'echo "Build"'

          }
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
