pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/mdjawad0/foodinc-Javabackend-test-jenkins.git'

                // Run Maven Wrapper Commands
                sh "./mvnw clean compile"

                echo 'Building the Project with maven compile'
            }
        }

        stage('Test') {
            steps {

                // Run Maven Wrapper Commands
                sh "./mvnw clean test"

                echo 'Testing the Project with maven test'
            }
        }

        stage('Package') {
            steps {

                // Run Maven Wrapper Commands
                sh "./mvnw clean package"

                echo 'Packaging the Project with maven package'
            }
        }
    }
}
