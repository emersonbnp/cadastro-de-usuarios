pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        sh "./mvnw package"
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