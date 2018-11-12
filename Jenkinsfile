pipeline {
  agent {
    docker {
      image 'gradle:4.8-jdk8'
    }

  }
  stages {
    stage('Test') {
      steps {
        sh 'gradle test'
      }
    }
  }
}