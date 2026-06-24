pipeline {
    agent { label 'docker' }

    environment {
        REGISTRY = 'localhost:5000'
    }

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
                sh "docker build -t ${REGISTRY}/api-tests:${BUILD_NUMBER} ."
            }
        }

        stage('Push to Registry') {
            steps {
                sh "docker push ${REGISTRY}/api-tests:${BUILD_NUMBER}"
            }
        }

        stage('Run Tests') {
            steps {
                sh "docker run --rm ${REGISTRY}/api-tests:${BUILD_NUMBER} --base_url ${params.BASE_URL}"
            }
        }

        stage('Cleanup') {
            steps {
                sh "docker rmi ${REGISTRY}/api-tests:${BUILD_NUMBER} || true"
            }
        }
    }
}