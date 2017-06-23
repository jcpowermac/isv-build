// example params file

// user-defined params
sourceUrl = ""
sourceContextDir = null
sourceSecret = null
sourceBranch = "master" // default
notifyBuild = [email: [""], slack: [baseUrl: "", channel: "#CHAN", botUser: null, teamDomain: null, token: null, tokenCredentialId: null], webhook: null]
notifyPublish = [email: null, slack: "", webhook: ""]
autoPublish = true
// build triggers?

// build service params
projectName = "" // assigned by Connect automation
containerName = ""
