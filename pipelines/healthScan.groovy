#!groovy

node {
   // Description: periodically run healthScan
   // Params: projectName
   // Triggers: daily cron

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

   def notify = false

   stage('Get latest healthScan') {
       echo ''
       // if rebuild, call rebuild job and set notify
         // rebuild
         // notify = true
         // build job: 'build', parameters: [string(name:'projectName', value: "${projetName}")]
   }

   stage('Notify') {
       if (notify) {
           if (notifyBuild.email) {
               echo "Emailing ${notifyBuild.email}"
           }
           if (notifyBuild.slack) {
               echo "Posting slack msg to ${notifyBuild.slack}"
               //slackSend channel: "${notifyBuild.slack.channel}", message: "Debug ISV build service"
           }
       }
   }
}
