#!groovy

node {
   // Description: initialize product
   // Params: projectName
   // Triggers: call from RH Connect

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

   stage('Create application') {
     // oc new-app will create build, dc and service for app verification

     // FIXME: conditionally build
     def created
     openshift.withCluster() {
       openshift.withProject("${projectNamespace}") {
         created = openshift.newApp("${sourceUrl}#${sourceBranch}")
       }
     }
     echo "new-app created ${created.count()} objects named: ${created.names()}"
     created.describe()
     def dc = created.narrow('dc')
     def bc = created.narrow('bc')
     def buildResult = bc.logs('-f')
     echo "The logs operation require ${result.actions.size()} oc interactions"
     def logsString = buildResult.actions[0].out
     def buildErr = buildResult.actions[0].err
   }

   stage('Verify build') {
     // check ${buildErr} to conditionally fail or use...
     //openshiftVerifyBuild(buildConfig: "${containerName}")
     echo ''
   }

   stage('Verify application') {
     // check ${dc} object
     echo ''
   }

}
