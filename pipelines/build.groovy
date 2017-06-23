#!groovy

//@Library('Utils')
//import com.redhat.*

node {
   // Description: build image
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
       /*

       TODO: The current verison of this may not be sufficient for a build service.
       newBuild = newBuildOpenShift() {
           url = sourceUrl
           branch = sourceBranch
           contextDir = sourceContextDir
           deleteBuild = false
           randomName = false
       }
       */

   }

   stage('Start container image build') {
       // verify build exists?
       echo "OpenShift build"
       /*
       TODO: variable for imageTag
       containerZoneScan {
           credentialsId = "ContainerZone"
           openShiftUri = "insecure://api.rhc4tp.openshift.com"
           imageName = containerName
           imageTag = "1.0"
       }
       */
   }

   stage('Verify build completed') {
       // poll for build to complete
       echo ''
       // pushed build will auto-trigger healthScan
   }

   stage('Poll for healthScan results') {
       echo ''
   }

   stage('Notify') {
       def subj = "Red Hat Connect build ${currentBuild.number}: ${currentBuild.result}"
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
