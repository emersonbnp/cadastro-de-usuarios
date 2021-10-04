pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        sh "./mvnw install"
        sh "docker build -t ${env.version} ."   
        print("image successfully built")
      }
    }
    stage('Deploy') {
      steps {
        echo 'Deploying....'
      }
    }
  }
}