@Library('ci-pipelines') _

javaCIPipeline([
    gitCredentialsId           : "jenkins-scm",
    gitPublishBranch           : "develop",
    mavenSettingsConfig        : "starter-settings.xml",
    javaVersion                : 21,
    mavenReleaseDeploymentRepo : 'bcepublic::https://bcepublic.cloud.bcsv.in.bund.de/nexus/repository/releases/',
    mavenSnapshotDeploymentRepo: 'bcepublic-snapshots::https://bcepublic.cloud.bcsv.in.bund.de/nexus/repository/snapshots/',
    publish                    : true,
    postStatusMail             : "committer",
    teamscaleCredentialsId     : "starter-apps-teamscale",
    teamscaleProject           : "java-starter-spring-boot",
    enableTeamscaleBuildbreaker: false, // TODO: re-enable once test coverage threshold is reached
    nexusIqAppId               : "java-starter-spring-boot",
])