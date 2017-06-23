#!groovy

node {
   // Description: build image and notify
   // Params: projectName
   // Triggers: RH Connect call, healthScan job

   try {
     evaluate(new File("vars/params/${projectName}.groovy"))
   } catch ( e ) {
     println "Could not initialize"
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

   stage("Wait for Remote System") {
       // webhook-step plugin
       hook = registerWebhook()
       echo "Waiting for POST to ${hook.getURL()}"
       // callback POST failing with 500
       /*
       data = waitForWebhook hook
       echo "Webhook called with data: ${data}"
       */
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
