#!groovy

//@Library('')
//import com.redhat.*

node {
   // Description: send publish call to RH connect API and notify
   // Params: projectName
   // Triggers: healthScan job with auto-publish

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

   stage('Publish') {
       // FIXME: need logic here for notify vs wait for hook_received or manual action
       if (autoPublish) {

           echo 'publishing image'

           /*
           TODO: Would need to figure out credentials
           containerZonePublish {
               credentialsId = "ContainerZone"
               openShiftUri = "insecure://api.rhc4tp.openshift.com"
               imageName = containerName
               imageTag = containerTag
           }
           */

       }
       // else we need to figure out hooks
   }
   stage('Notify') {
       def subj = "Red Hat Connect publish ${currentBuild.number}: ${currentBuild.result}"
       def body = '''
          Job: ${currentBuild.number}
          Result: ${currentBuild.result}
          Previous build result: ${currentBuild.previousBuild}
          Reason:
          Lorem ipsum dolor sit amet,
          consectetur adipiscing elit,
          sed do eiusmod tempor incididunt
          ut labore et dolore magna aliqua.
          '''
       build job: 'notify',
       parameters: [string(name:"projectName", value: "${projectName}"),
       string(name:"subj", value: "${subj}"),
       string(name:"body", value: "${body}")],
       propagate: false
   }
}
