#!groovy

node {
   // Description: initialize product
   // Params: projectName
   // Triggers: call from RH Connect

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
   stage('Call external webhook') {
       echo 'https://httpbin.org/get'
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
   stage('Poll for image scan results') {
       echo 'Scanning status...'
   }
   stage('Publish?') {
       echo 'Do you want to publish?' // need this to be an external webhook
   }
   stage('Notify') {
       if (notifyBuild.email) {
           echo "Emailing ${notifyBuild.email}"
       }
       if (notifyBuild.slack) {
           echo "Posting slack msg to ${notifyBuild.slack}"
           //slackSend channel: "${notifyBuild.slack.channel}", message: "Debug ISV build service"
       }
   }
}
