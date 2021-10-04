pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        sh "./gradlew build"
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