#!groovy

node {
   // Description: periodically run healthScan, conditionally call build
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

   stage('Get latest healthScan') {
       echo ''
       // if rebuild, call rebuild job
         // rebuild
         // notify = true
         build job: 'build',
         parameters: [string(name:"projectName", value: "${projectName}")], propagate: false
   }

}
