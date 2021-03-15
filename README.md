
testing/jenkinsfile2 

========================
This project contains examples for the [Jenkins pipeline plugin](https://jenkins.io/solutions/pipeline/), comparing both declarative and scripted syntax.

The examples were developed while working on an article series called *Coding Continuous Delivery* published in [Java aktuell](http://www.ijug.eu/java-aktuell/das-magazin.html). Both English translation and German original can be found on the [Cloudogu Blog](https://cloudogu.com/en/blog).

<table  border="0">
  <tr>
    <td colspan="2">01/2018 (covering examples on branches 1 to 5)</td>
  </tr>
  <tr>
    <td rowspan="2"><img src="https://cloudogu.com/images/blog/2018/04/jenkins_grundlagen.png" width=50% /></td>
    <td><a href="https://cloudogu.com/en/blog/continuous_delivery_1_basics"> 🇬🇧 Jenkins pipeline plugin basics</a></td>
  </tr>
  <tr>
   <td><a href="https://cloudogu.com/de/blog/continuous_delivery_1_grundlagen"> 🇩🇪 Grundlagen des Jenkins-Pipeline-Plug-ins</a></td>
  </tr>
 
   <tr>
    <td colspan="2">02/2018 (covering examples on branches 6 and 7)</td>
  </tr>
  <tr>
    <td rowspan="2"><img src="https://cloudogu.com/images/blog/2018/04/jenkins_optimization.png" width=50% /></td>
    <td><a href="https://cloudogu.com/en/blog/continuous_delivery_2"> 🇬🇧 Performance optimization for the Jenkins Pipeline</a></td>
  </tr>
  <tr>
   <td><a href="https://cloudogu.com/de/blog/continuous_delivery_2_de"> 🇩🇪 Performance Optimierung für die Jenkins Pipeline</a></td>
  </tr>
  
   <tr>
    <td colspan="2">03/2018 (covering examples on branches 8 and 9)</td>
  </tr>
  <tr>
    <td rowspan="2"><img src="https://cloudogu.com/images/blog/2018/05/jenkins_werkzeuge.png" width=50% /></td>
    <td><a href="https://cloudogu.com/en/blog/continuous_delivery_part_3"> 🇬🇧 Helpful Tools for the Jenkins Pipeline</a></td>
  </tr>
  <tr>
   <td><a href="https://cloudogu.com/de/blog/continuous_delivery_teil_3"> 🇩🇪 Hilfreiche Werkzeuge für die Jenkins Pipeline</a></td>
  </tr>

   <tr>
    <td colspan="2">04/2018 (covering examples on branches 10 and 11)</td>
  </tr>
  <tr>
    <td rowspan="2"><img src="https://cloudogu.com/images/blog/2018/Jenkins_KubernetesSonarqube.png" width=50% /></td>
    <td><a href="https://cloudogu.com/en/blog/continuous_delivery_4_en"> 🇬🇧 Statical Code Analysis with SonarQube and deployment to Kubernetes et al. with Jenkins Pipelines</a></td>
  </tr>
  <tr>
   <td><a href="https://cloudogu.com/de/blog/continuous_delivery_4_de"> 🇩🇪 Statische Code Analyse mit SonarQube und Deployment auf Kubernetes et al. mit Jenkins Pipelines</a></td>
  </tr>
  
</table>

The project being built by the pipeline examples is [wildfly/quickstart/kitchensink](https://github.com/wildfly/quickstart/tree/cfd2e05d16e4ae788bc12486f5b30d668b921973/kitchensink), a typical JEE web app basing on CDI, JSF, JPA, EJB, JAX-RS and integration tests with arquillian.
It was extended slightly to allow for running integration tests using WildFly Swarm and (in branch `11-x`) to provide its version name via REST. 

The pipeline examples are built on top of each other, each in declarative and scripted syntax, respectively. Each example is put on a separate [branches](https://github.com/cloudogu/jenkinsfiles/branches) for convenient access.

Please see [our Jenkins Instance](https://oss.cloudogu.com/jenkins/blue/organizations/jenkins/cloudogu-github%2Fjenkinsfiles/branches) for build results.

The following aspects are covered by the examples:

1. A simple pipeline ([declarative](https://github.com/cloudogu/jenkinsfiles/blob/1-declarative/Jenkinsfile) | [scripted](https://github.com/cloudogu/jenkinsfiles/blob/1-scripted/Jenkinsfile)) 
2. Improving maintainability by introducing custom steps ([declarative](https://github.com/cloudogu/jenkinsfiles/blob/2-declarative/Jenkinsfile) | [scripted](https://github.com/cloudogu/jenkinsfiles/blob/2-scripted/Jenkinsfile))
3. Division into smaller stages ([declarative](https://github.com/cloudogu/jenkinsfiles/blob/3-declarative/Jenkinsfile) | [scripted](https://github.com/cloudogu/jenkinsfiles/blob/3-scripted/Jenkinsfile))
4. End of pipeline
   a Handling failures ([declarative](https://github.com/cloudogu/jenkinsfiles/blob/4a-declarative/Jenkinsfile) | [scripted](https://github.com/cloudogu/jenkinsfiles/blob/4a-scripted/Jenkinsfile))  
   b Simplified Mailing ([declarative](https://github.com/cloudogu/jenkinsfiles/blob/4b-declarative/Jenkinsfile) | [scripted](https://github.com/cloudogu/jenkinsfiles/blob/4b-scripted/Jenkinsfile))
5. Archive and Properties/Options ([declarative](https://github.com/cloudogu/jenkinsfiles/blob/5-declarative/Jenkinsfile) | [scripted](https://github.com/cloudogu/jenkinsfiles/blob/5-scripted/Jenkinsfile))
6. Parallel ([declarative](https://github.com/cloudogu/jenkinsfiles/blob/6-declarative/Jenkinsfile) | [scripted](https://github.com/cloudogu/jenkinsfiles/blob/6-scripted/Jenkinsfile))
7. Time Triggered Builds (e.g. nightly) ([declarative](https://github.com/cloudogu/jenkinsfiles/blob/7-declarative/Jenkinsfile) | [scripted](https://github.com/cloudogu/jenkinsfiles/blob/7-scripted/Jenkinsfile))
8. Shared libraries ([declarative](https://github.com/cloudogu/jenkinsfiles/blob/8-declarative/Jenkinsfile) | [scripted](https://github.com/cloudogu/jenkinsfiles/blob/8-scripted/Jenkinsfile) | [shared library](https://github.com/cloudogu/jenkinsfiles/tree/8-shared-library))
9. Docker  
   a Run whole pipeline inside a Docker container ([declarative](https://github.com/cloudogu/jenkinsfiles/blob/9a-declarative/Jenkinsfile) | [scripted](https://github.com/cloudogu/jenkinsfiles/blob/9a-scripted/Jenkinsfile))    
   b Using Docker inside a custom step (implemented in shared library) ([declarative](https://github.com/cloudogu/jenkinsfiles/blob/9b-declarative/Jenkinsfile) | [scripted](https://github.com/cloudogu/jenkinsfiles/blob/9b-scripted/Jenkinsfile) | [shared library](https://github.com/cloudogu/jenkinsfiles/tree/9b-shared-library))
10. Statical code analysis with SonarQube  
   a check quality gate outside of node (as shown within the docs)  ([declarative](https://github.com/cloudogu/jenkinsfiles/blob/10a-declarative/Jenkinsfile) | [scripted](https://github.com/cloudogu/jenkinsfiles/blob/10a-scripted/Jenkinsfile))  
   b analysis and quality gate within one stage (pragmatic, easier to maintain) ([declarative](https://github.com/cloudogu/jenkinsfiles/blob/10b-declarative/Jenkinsfile) | [scripted](https://github.com/cloudogu/jenkinsfiles/blob/10b-scripted/Jenkinsfile))
11. Plain Deployment to Kubernetes ([declarative](https://github.com/cloudogu/jenkinsfiles/blob/11-declarative/Jenkinsfile) | [scripted](https://github.com/cloudogu/jenkinsfiles/blob/11-scripted/Jenkinsfile))
12. Deployment to Kubernetes using Helm (declarative (TODO) | [scripted](https://github.com/cloudogu/jenkinsfiles/blob/12-scripted/Jenkinsfile))


# Jenkins Setup

All examples (obviously) need the [Jenkins Pipeline plugin](https://plugins.jenkins.io/workflow-aggregator) installed.
It has been part of the Jenkins default plugins for quite some time.

## 8. Shared libraries

Starting with branch 8, the [Pipeline: GitHub Groovy Libraries plugin](https://plugins.jenkins.io/pipeline-github-lib) is needed so the libraries are loaded out of the box.
It is also one of the Jenkins default plugins.

## 9. Docker

Branch 9 and up require the [Docker Pipeline plugin](https://plugins.jenkins.io/docker-workflow) (installed by default) and only run on workers that have a working docker client (`docker` binary on the `PATH`). 
In order to distinguish this workers from others, they provide a `docker` label.

## 10. SonarQube

From branch 10 the [SonarQube Scanner plugin](https://plugins.jenkins.io/sonar) and a SonarQube instance `sonarcloud.io` set up in Jenkins.
In addition, in SonarQube, a webhook to `https://JENKINS/sonarqube-webhook` must be configured.

Before Jenkins is able to analyse the branches a first analysis has to be done (e.g. from your developer machine) analysis,
e.g. like so

```bash
mvn clean install -Pjenkins -DskipITs
mvn sonar:sonar -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=<SECURITY TOKEN> -Dsonar.organization=<YOUR-ORG-KEY>
```
If you want the quality gate to fail (for showcases), one option is to analyse one of the 10a branches but remove the 
class `Untested` before.

## 11. Kubernetes

In order to deploy to Kubernetes the examples in the `11-x`-branches require the [Kubernetes Continuous Deploy plugin](https://plugins.jenkins.io/kubernetes-cd).
The following must be executed (from one of the `11-x`-branches) before the build can succeed.

```bash
kubectl apply -f k8s/namespace.yaml
kubectl apply --namespace jenkins-ns -f k8s/service.yaml,k8s/serviceaccount.yaml
k8s/create-kubeconfig jenkins-sa --namespace=jenkins-ns > jenkins.kubeconfig
```
Then, add the `jenkins.kubeconfig` as Jenkins file credential, called `kubeconfig-oss-deployer`.  
Finally, add a Username and Password credential called `hub.docker.com-cesmarvin` 
(e.g. on [GCR](https://cloud.google.com/container-registry/docs/advanced-authentication#using_a_json_key_file), the user is `_json_key` and the password is the a JSON in single quotes with line breaks removed - `cat account.json`).
and change the image name to match your registry.

# Jenkins Build status

| Branch        | Declarative | Scripted | Library/SQ |
| ------------- |:-----------:| --------:| ----------:|
| 1. Simple pipeline                      | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/1-declarative)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/1-declarative/) | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/1-scripted)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/1-scripted/) |  | 
| 2. Custom steps                         | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/2-declarative)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/2-declarative/) | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/2-scripted)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/2-scripted/) |  |
| 3. Smaller Stages                       | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/3-declarative)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/3-declarative/) | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/3-scripted)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/3-scripted/) |  |
| 4a Handling failures                    | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/4a-declarative)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/4a-declarative/) | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/4a-scripted)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/4a-scripted/) |  |
| 4b Simplified Mailing                   | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/4b-declarative)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/4b-declarative/) | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/4b-scripted)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/4b-scripted/) |  |
| 5. Archive and Properties/Options       | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/5-declarative)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/5-declarative/) | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/5-scripted)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/5-scripted/) |  |
| 6. Parallel                             | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/6-declarative)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/6-declarative/) | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/6-scripted)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/6-scripted/) |  |
| 7. Time Triggered Builds                | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/7-declarative)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/7-declarative/) | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/7-scripted)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/7-scripted/) |  |
| 8. Shared libraries                     | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/8-declarative)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/8-declarative/) | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/8-scripted)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/8-scripted/) | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/8-shared-library)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/8-shared-library/) |
| 9a Docker (whole pipeline in container) | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/9a-declarative)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/9a-declarative/) | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/9a-scripted)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/9a-scripted/) |  |
| 9b Docker (inside custom step)          | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/9b-declarative)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/9b-declarative/) | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/9b-scripted)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/9b-scripted/) | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/9b-shared-library)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/9b-shared-library) |
| 10a SonarQube (as shown in docs)        | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/10a-declarative)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/10a-declarative/) <br/> See SQ! | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/10a-scripted)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/10a-scripted/) <br/> See SQ!| [![SonarQube Badge](https://sonarcloud.io/api/project_badges/measure?project=com.cloudogu.jenkinsfiles%3Awildfly-kitchensink&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.cloudogu.jenkinsfiles%3Awildfly-kitchensink) | |
| 10b SonarQube (pragmatic)               | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/10b-declarative)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/10b-declarative/) <br/> See SQ!| [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/10b-scripted)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/10b-scripted/) <br/> See SQ!| [![SonarQube Badge](https://sonarcloud.io/api/project_badges/measure?project=com.cloudogu.jenkinsfiles%3Awildfly-kitchensink&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.cloudogu.jenkinsfiles%3Awildfly-kitchensink) | |
| 11. Kubernetes (plain)                  | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/11-declarative)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/11-declarative/) | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/11-scripted)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/11-scripted/) |  |
| 12. Kubernetes (Helm)                   | - | [![Build Status](https://oss.cloudogu.com/jenkins/buildStatus/icon?job=cloudogu-github/jenkinsfiles/12-scripted)](https://oss.cloudogu.com/jenkins/job/cloudogu-github/job/jenkinsfiles/job/12-scripted/) |  |

# Further resources

## `Jenkinsfile`s

* [triologygmbh/test-data-loader](https://github.com/triologygmbh/test-data-loader) - Deploys to maven central
* [cloudogu/continuous-delivery-docs-example](https://github.com/cloudogu/continuous-delivery-docs-example) - Docs as code to ODT & PDF
* [cloudogu/continuous-delivery-slides-example](https://github.com/cloudogu/continuous-delivery-slides-example) - Deploy presentations to Kubernetes and Maven Sites (Nexus) 
 
## Shared libraries

A collection of shared libraries to be used as reference.

* [cloudogu/ces-build-lib](https://github.com/cloudogu/ces-build-lib) - Contains features for Maven, Docker, SonarQube and others
* [mozilla/fxtest-jenkins-pipeline](https://github.com/mozilla/fxtest-jenkins-pipeline) - used by Firefox Test Engineering
* [docker/jenkins-pipeline-scripts](https://github.com/docker/jenkins-pipeline-scripts) -  helper functions and classes used by Jenkins instances managed by Docker, Inc.
* [fabric8io/fabric8-pipeline-library](https://github.com/fabric8io/fabric8-pipeline-library) -  reusable Jenkins Pipeline steps and functions to be used with the fabric8 platform.
* [Mirantis/pipeline-library](https://github.com/Mirantis/pipeline-library) - Contains Salt commands, Git actions, Artifactory management, Docker image building, AWS and OpenStack provisioning, etc.
* [Shared Library Demo](https://github.com/jenkinsci/workflow-aggregator-plugin/tree/master/demo) - Demonstrates a continuous delivery pipeline of Java web application including library
 
## Others

* [jenkinsci/JenkinsPipelineUnit](https://github.com/jenkinsci/JenkinsPipelineUnit) - Unit test for Jenkins pipelines
