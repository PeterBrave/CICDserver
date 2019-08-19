<?xml version='1.1' encoding='UTF-8'?>
<flow-definition plugin="workflow-job@2.33">
    <actions>
        <org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobAction plugin="pipeline-model-definition@1.3.9"/>
        <org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobPropertyTrackerAction plugin="pipeline-model-definition@1.3.9">
            <jobProperties/>
            <triggers/>
            <parameters/>
            <options/>
        </org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobPropertyTrackerAction>
    </actions>
    <description></description>
    <keepDependencies>false</keepDependencies>
    <properties>
        <com.coravy.hudson.plugins.github.GithubProjectProperty plugin="github@1.29.4">
            <projectUrl>https://github.com/${githubName}/${repo}.git/</projectUrl>
            <displayName></displayName>
        </com.coravy.hudson.plugins.github.GithubProjectProperty>
    </properties>
    <definition class="org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition" plugin="workflow-cps@2.71">
        <scm class="hudson.plugins.git.GitSCM" plugin="git@3.10.1">
            <configVersion>2</configVersion>
            <userRemoteConfigs>
                <hudson.plugins.git.UserRemoteConfig>
                    <url>https://github.com/${githubName}/${repo}.git</url>
                </hudson.plugins.git.UserRemoteConfig>
            </userRemoteConfigs>
            <branches>
                <hudson.plugins.git.BranchSpec>
                    <name>*/master</name>
                </hudson.plugins.git.BranchSpec>
            </branches>
            <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>
            <submoduleCfg class="list"/>
            <extensions/>
        </scm>
        <scriptPath>Jenkinsfile</scriptPath>
        <lightweight>true</lightweight>
    </definition>
    <triggers/>
    <disabled>false</disabled>
</flow-definition>