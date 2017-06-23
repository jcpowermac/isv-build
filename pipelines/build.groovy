#!groovy

node {
   // Description: build image and notify
   // Params: projectName
   // Triggers: RH Connect call, healthScan job

   def workspace = pwd()
   def paramsFile = "${workspace}@script/vars/params/${projectName}.groovy"

   try {
     load "${paramsFile}"
   } catch ( e ) {
     println "Could not initialize params file"
     println e.toString()
     currentBuild.result = 'FAILURE'
     return
   }

   stage('Create Build (if necessary)') {
       echo "conditionally creating build"
       //openshiftVerifyBuild(buildConfig: "${containerName}")
   }

   stage('Start container image build') {
       echo "OpenShift build"
   }

   stage('Verify build completed') {
       echo ''
       // pushed build will auto-trigger healthScan
   }

   stage('Poll for healthScan results') {
       echo ''
   }

   stage('Notify') {
       // move to vars method
       if (notifyBuild.email) {
           echo "Emailing ${notifyBuild.email}"
       }
       if (notifyBuild.slack) {
           echo "Posting slack msg to ${notifyBuild.slack}"
           //slackSend channel: "${notifyBuild.slack.channel}", message: "Debug ISV build service"
       }
   }
}
