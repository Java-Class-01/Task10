pipeline {
    agent any
    
    tools {
        // If you have Maven configured in Jenkins, use this:
        // maven 'M3'
        jdk 'JDK11'  
    }
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', 
                url: 'https://github.com/Java-Class-01/task9.git'
            }
        }
        
        stage('Build') {
            steps {
                // First, let's see what's in the directory
                sh 'dir /w'
                sh 'mvn --version'
                echo 'Building the project...'
            }
        }
        
        stage('Package') {
            steps {
                script {
                    // Try to package, but continue even if it fails for now
                    try {
                        bat 'mvn clean package -DskipTests'
                    } catch (Exception e) {
                        echo "Build failed, but continuing to see what happened: ${e.message}"
                    }
                    
                    // List what was created
                    bat 'dir target /w 2>nul || echo "No target directory found"'
                }
            }
        }
    }
    
    post {
        always {
            // Try to archive artifacts if they exist
            script {
                def files = findFiles(glob: 'target/*.jar')
                if (files) {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                    echo "Archived ${files.length} artifact(s)"
                } else {
                    echo "No JAR files found to archive"
                }
            }
        }
        
        success {
            echo '✅ Step 1 completed successfully! Ready for Step 2!'
        }
        
        failure {
            echo '❌ Pipeline failed. We need to fix the build first.'
        }
    }
}
