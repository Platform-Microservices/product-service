pipeline {
    agent any

    environment {
        SERVICE = 'product'
        NAME = "esthercaroline/${env.SERVICE}"
    }

    stages {
        stage('Dependencies') {
            steps {
                build job: 'product', wait: true
            }
        }

        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }

        stage('Build & Push Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credential', usernameVariable: 'USERNAME', passwordVariable: 'TOKEN')]) {
                    sh "docker login -u $USERNAME -p $TOKEN"
                    sh "docker buildx create --use --platform=linux/arm64,linux/amd64 --node multi-platform-builder-${env.SERVICE} --name multi-platform-builder-${env.SERVICE}"
                    sh "docker buildx build --platform=linux/arm64,linux/amd64 --push --tag ${env.NAME}:latest --tag ${env.NAME}:${env.BUILD_ID} -f Dockerfile ."
                    sh "docker buildx rm --force multi-platform-builder-${env.SERVICE}"
                }
            }
        }

        stage('Deploy') {
            steps {
                withCredentials([
                    usernamePassword(credentialsId: 'aws-credentials', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY'),
                    file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG_FILE')
                ]) {
                    sh '''
                        export AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID
                        export AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY
                        export KUBECONFIG=$KUBECONFIG_FILE

                        # Atualiza o token para o EKS
                        aws eks get-token --region us-east-2 --cluster-name eks-store > /dev/null

                        # Aplica os manifests
                        kubectl apply -f k8s/k8s.yaml
                    '''
                }
            }
        }
    }
}
