pipeline {
  agent any
  stages {
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
      parallel {
        stage('Stage2') {
          steps {
            sleep 120
            sh 'echo date'
          }
        }
        stage('step2') {
          steps {
            sh 'echo step2'
          }
        }
      }
    }
  }
}