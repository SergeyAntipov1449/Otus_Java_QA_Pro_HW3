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
                sh "docker build -t ${REGISTRY}/api-tests:latest ."
            }
        }

        stage('Push to Registry') {
            steps {
                sh "docker push ${REGISTRY}/api-tests:latest"
            }
        }

        stage('Run Tests') {
            steps {
                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                    sh "docker run --name api-tests-${BUILD_NUMBER} ${REGISTRY}/api-tests:latest --base_url ${params.BASE_URL}"
                }
                sh """
                    docker cp api-tests-${BUILD_NUMBER}:/app/target/allure-results ./allure-results
                    docker rm api-tests-${BUILD_NUMBER}
                """
            }
        }

        stage('Allure Report') {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'allure-results']]
                    ])
                }
            }
        }
    }

    post {
        always {
            sh "docker rm -f api-tests-${BUILD_NUMBER} || true"
            sh "docker rmi -f ${REGISTRY}/api-tests:latest || true"
        }
    }
}