pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        git(url: 'github.com/KarolAntczak/Dexter', branch: 'master', changelog: true)
      }
    }
  }
}