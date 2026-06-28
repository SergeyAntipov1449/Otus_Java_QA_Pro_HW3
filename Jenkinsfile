pipeline {
    agent { label 'docker' }

    environment {
        REGISTRY = 'localhost:5000'
        IMAGE = "${REGISTRY}/${env.JOB_NAME}:latest"
        CONTAINER = "${env.JOB_NAME}-${env.BUILD_NUMBER}"
    }

    parameters {
        string(
            name: 'BASE_URL',
            defaultValue: 'https://petstore.swagger.io',
            description: 'Базовый URL для тестов'
        )
    }

    stages {
        stage('Checkout') {
            steps { checkout scm }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t ${IMAGE} ."
            }
        }

        stage('Push to Registry') {
            steps {
                sh "docker push ${IMAGE}"
            }
        }

        stage('Run Tests') {
            steps {
                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                    sh "docker run --name ${CONTAINER} ${IMAGE} --base_url ${params.BASE_URL}"
                }
                sh """
                    mkdir -p ./allure-results
                    docker cp ${CONTAINER}:/app/target/allure-results/. ./allure-results/ || echo "WARNING: No allure results found"
                    docker rm ${CONTAINER}
                """
            }
        }

        stage('Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'allure-results']]
                ])
            }
        }
    }

    post {
        always {
            sh "docker rm -f ${CONTAINER} || true"
            sh "docker rmi -f ${IMAGE} || true"
        }
    }
}