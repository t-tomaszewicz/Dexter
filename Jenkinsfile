pipeline {
  agent any
  stages {
    stage('Build') {
      parallel {
        stage('Build1') {
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
        stage('Step1') {
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
    stage('Finalize') {
      steps {
        echo 'Finalize'
      }
    }
  }
}