#!/usr/bin/env groovy

@Library('jenkins-shared-library') _

pipeline {
	agent any
    options { disableConcurrentBuilds() }
	environment {

	}
	stages {
		stage('Build') {
			steps {
				echo 'Building project...'
				checkout([
						$class: 'GitSCM',
						branches: scm.branches,
						doGenerateSubmoduleConfigurations: scm.doGenerateSubmoduleConfigurations,
						extensions: scm.extensions + [[$class: 'RelativeTargetDirectory', relativeTargetDir: checkoutDir], [$class: 'CloneOption', noTags: false, shallow: false, depth: 0, reference: '']],
						userRemoteConfigs: scm.userRemoteConfigs
				])
				sh './gradlew clean build -x test'
			}
		}

		stage('Skip?') {
			steps {
				abortIfGitTagExists env.VERSION
			}
		}


		stage('Tag') {
			when { branch 'master' }
			steps {
				pushGitTag env.VERSION
			}
		}
	}
	post {
		always {
			dockerCompose 'down --volumes --remove-orphans'
			dockerCompose 'rm --force'
			slackBuildStatus '#team-need4speed-auto', env.SLACK_USER
		}
	}
}
