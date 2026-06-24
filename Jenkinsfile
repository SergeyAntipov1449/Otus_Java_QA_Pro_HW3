pipeline {
    agent { label 'docker' }
    parameters {
        string(
            name: 'BASE_URL',
            defaultValue: 'https://petstore.swagger.io',
            description: 'Базовый URL для API-тестов'
        )
    }
    stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Docker Build') {
            steps {
                sh "docker build -t api-tests:${BUILD_NUMBER} ."
            }
        }
        stage('Run Tests') {
            steps {
                sh "docker run --rm api-tests:${BUILD_NUMBER} --base_url ${params.BASE_URL}"
            }
        }
        stage('Cleanup') {
            steps {
                sh "docker rmi api-tests:${BUILD_NUMBER} || true"
            }
        }
    }
}