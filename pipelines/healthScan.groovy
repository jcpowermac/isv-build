#!groovy

//@Library('')
//import com.redhat.*

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
       /*
       def jobParameters = new JenkinsUtils().createJobParameters([name: "foo"])
       containerZoneHealthCheck {
           credentialsId = "ContainerZone"
           rebuildJobName = jobName
           rebuildJobParameters = jobParameters
       }
       */

       // if rebuild, call rebuild job and set notify
       // rebuild
       // notify = true
       build job: 'build',
               parameters: [string(name:"projectName", value: "${projectName}")], propagate: false
   }

}
