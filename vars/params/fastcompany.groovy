// Fast Company params

// user-defined params
sourceUrl = "https://github.com/pchriste/happywebserver"
sourceContextDir = null
sourceSecret = null
sourceBranch = "master"
notifyBuild = [email: ["aweiteka@example.com"], slack: [baseUrl: "redhat.slack.com", channel: "#sys-eng-notices", botUser: null, teamDomain: null, token: null, tokenCredentialId: null], webhook: null]
notifyPublish = [email: null, slack: "foo", webhook: "fastcompany/post/notify"]
autoPublish = true

// build service params
projectName = "fastcompany"
containerName = "happyserver"
