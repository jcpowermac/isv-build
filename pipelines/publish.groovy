#!groovy

node {
   // Description: send publish call to RH connect API and notify
   // Params: projectName
   // Triggers: healthScan job with auto-publish

   try {
     evaluate(new File("vars/params/${projectName}.groovy"))
   } catch ( e ) {
     println "Could not initialize"
     currentBuild.result = 'FAILURE'
     return
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
