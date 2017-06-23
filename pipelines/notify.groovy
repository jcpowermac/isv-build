#!groovy

node {
   // Description: notify
   // Params: projectName
   // Triggers: any job that needs to notify

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

   stage('Notify') {
       if (notifyBuild.email) {
           echo "Emailing ${notifyBuild.email}"
           def subj = "foo"
           def body = "bar"
           mail bcc: '', body: "${body}", cc: '', from: "redhat-connect@redhat.com", replyTo: "do-not-reply@redhat.com", subject: "${subj}", to: "${notifyBuild.email}"
       }
       if (notifyBuild.slack) {
           echo "Posting slack msg to ${notifyBuild.slack}"
           //slackSend channel: "${notifyBuild.slack.channel}", message: "Debug ISV build service"
       }
   }
}
