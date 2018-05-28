triologygmbh/jenkinsfile 
========================
This project contains examples for the [Jenkins pipeline plugin](https://jenkins.io/solutions/pipeline/), comparing both declarative and scripted syntax.

The examples were developed while working on an article series called *Coding Continuous Delivery* published in [Java aktuell](http://www.ijug.eu/java-aktuell/das-magazin.html). Both English translation and German original can be found on the [Cloudogu Blog](https://cloudogu.com/en/blog).

* 01/2018 (covering examples on branches 1 to 5)
  * 🇬🇧 [Jenkins pipeline plugin basics](https://cloudogu.com/en/blog/continuous_delivery_1_basics)
  * 🇩🇪 [Grundlagen des Jenkins-Pipeline-Plug-ins](https://cloudogu.com/downloads/publications/2018-01-Java-Aktuell--Coding-Continous-Delivery-Grundlagen-des-Jenkins-Pipeline-Plug-ins.pdf)
* 02/2018 (covering examples on branches 6 and 7)
  * 🇬🇧 [Performance optimization for the Jenkins Pipeline](https://cloudogu.com/en/blog/continuous_delivery_2)
  * 🇩🇪 [Performance Optimierung für die Jenkins Pipeline](https://cloudogu.com/downloads/publications/2018-02-Java-Aktuell--Coding_Continuous_Delivery%E2%80%93Performance-Optimierung_f%C3%BCr_die_Jenkins-Pipeline.pdf)
* 03/2018 (covering examples on branches 8 and 9)
  * 🇬🇧 [Helpful Tools for the Jenkins Pipeline](https://cloudogu.com/en/blog/continuous_delivery_part_3)
  * 🇩🇪 [Hilfreiche Werkzeuge für die Jenkins Pipeline](https://cloudogu.com/downloads/publications/2018-03-Java-aktuell--Coding_Continuous_Delivery-Hilfreiche%20Werkzeuge%20f%C3%BCr%20die%20Jenkins-Pipeline.pdf)
* 04/2018 (covering examples on branches 10 and 11)
  * 🇬🇧 Statical Code Analysis with SonarQube and deployment to Kubernetes et al. with Jenkins Pipelines
  * 🇩🇪 Statische Code Analyse mit SonarQube und Deployment auf Kubernetes et al. mit Jenkins Pipelines
      

The project being built by the pipeline examples is [wildfly/quickstart/kitchensink](https://github.com/wildfly/quickstart/tree/cfd2e05d16e4ae788bc12486f5b30d668b921973/kitchensink), a typical JEE web app basing on CDI, JSF, JPA, EJB, JAX-RS and integration tests with arquillian.
It was extended slightly to allow for running integration tests using WildFly Swarm and (in branch `11-x`) to provide its version name via REST. 

The pipeline examples are built on top of each other, each in declarative and scripted syntax, respectively. Each example is put on a separate [branches](https://github.com/triologygmbh/jenkinsfile/branches) for convenient access.

Please see [our Jenkins Instance](https://opensource.triology.de/jenkins/blue/organizations/jenkins/triologygmbh-github%2Fjenkinsfile/branches) for build results.

The following aspects are covered by the examples:

1. A simple pipeline ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/1-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/1-scripted/Jenkinsfile)) 
2. Improving maintainability by introducing custom steps ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/2-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/2-scripted/Jenkinsfile))
3. Division into smaller stages ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/3-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/3-scripted/Jenkinsfile))
4. End of pipeline
   a Handling failures ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/4a-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/4a-scripted/Jenkinsfile))  
   b Simplified Mailing ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/4b-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/4b-scripted/Jenkinsfile))
5. Archive and Properties/Options ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/5-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/5-scripted/Jenkinsfile))
6. Parallel ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/6-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/6-scripted/Jenkinsfile))
7. Time Triggered Builds (e.g. nightly) ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/7-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/7-scripted/Jenkinsfile))
8. Shared libraries ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/8-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/8-scripted/Jenkinsfile) | [shared library](https://github.com/triologygmbh/jenkinsfile/tree/8-shared-library))
9. Docker  
   a Run whole pipeline inside a Docker container ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/9a-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/9a-scripted/Jenkinsfile))    
   b Using Docker inside a custom step (implemented in shared library) ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/9b-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/9b-scripted/Jenkinsfile) | [shared library](https://github.com/triologygmbh/jenkinsfile/tree/9b-shared-library))
10. Statical code analysis with SonarQube  
   a check quality gate outside of node (as shown within the docs)  ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/10a-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/10a-scripted/Jenkinsfile))  
   b analysis and quality gate within one stage (pragmatic, easier to maintain) ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/10b-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/10b-scripted/Jenkinsfile))
11. Deployment to Kubernetes ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/11-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/11-scripted/Jenkinsfile))


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

## 11. Kubernetes

In order to deploy to Kubernetes the examples in the `11-x`-branches require the [Kubernetes Continuous Deploy plugin](https://plugins.jenkins.io/kubernetes-cd).
The following must be executed (from one of the `11-x`-branches) before the build can succeed.

```bash
kubectl apply -f k8s/namespace.yaml
kubectl apply --namespace jenkins-ns -f k8s/service.yaml,k8s/serviceaccount.yaml
k8s/create-kubeconfig jenkins-sa --namespace=jenkins-ns > jenkins.kubeconfig
```
Then, add the `jenkins.kubeconfig` as Jenkins file credential, called `kubeconfig-staging`.  
Finally, add a Username and Password credential called `docker-us.gcr.io/ces-demo-instances` (e.g. on [GCR](https://cloud.google.com/container-registry/docs/advanced-authentication#using_a_json_key_file), the user is `json_key` and the password is the a JSON in single quotes with line breaks removed!).

# Jenkins Build status

| Branch        | Declarative | Scripted | Library/SQ |
| ------------- |:-----------:| --------:| ----------:|
| 1. Simple pipeline                      | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/1-declarative)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/1-declarative/) | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/1-scripted)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/1-scripted/) |  | 
| 2. Custom steps                         | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/2-declarative)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/2-declarative/) | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/2-scripted)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/2-scripted/) |  |
| 3. Smaller Stages                       | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/3-declarative)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/3-declarative/) | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/3-scripted)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/3-scripted/) |  |
| 4a Handling failures                    | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/4a-declarative)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/4a-declarative/) | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/4a-scripted)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/4a-scripted/) |  |
| 4b Simplified Mailing                   | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/4b-declarative)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/4b-declarative/) | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/4b-scripted)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/4b-scripted/) |  |
| 5. Archive and Properties/Options       | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/5-declarative)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/5-declarative/) | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/5-scripted)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/5-scripted/) |  |
| 6. Parallel                             | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/6-declarative)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/6-declarative/) | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/6-scripted)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/6-scripted/) |  |
| 7. Time Triggered Builds                | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/7-declarative)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/7-declarative/) | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/7-scripted)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/7-scripted/) |  |
| 8. Shared libraries                     | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/8-declarative)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/8-declarative/) | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/8-scripted)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/8-scripted/) | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/8-shared-library)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/8-shared-library/) |
| 9a Docker (whole pipeline in container) | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/9a-declarative)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/9a-declarative/) | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/9a-scripted)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/9a-scripted/) |  |
| 9b Docker (inside custom step)          | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/9b-declarative)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/9b-declarative/) | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/9b-scripted)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/9b-scripted/) | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/9b-shared-library)](https://opensource.triology.de/jenkins/job/triologygmbh-github/jenkinsfile/9b-shared-library) |
| 10a SonarQube (as shown in docs)        | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/10a-declarative)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/10a-declarative/) <br/> See SQ! | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/10a-scripted)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/10a-scripted/) <br/> See SQ!| [![SonarQube Badge](https://sonarcloud.io/api/project_badges/measure?project=de.triology.jenkinsfile%3Awildfly-kitchensink&metric=alert_status)](https://sonarcloud.io/dashboard?id=de.triology.jenkinsfile%3Awildfly-kitchensink) | |
| 10b SonarQube (pragmatic)               | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/10b-declarative)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/10b-declarative/) <br/> See SQ!| [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/10b-scripted)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/10b-scripted/) <br/> See SQ!| [![SonarQube Badge](https://sonarcloud.io/api/project_badges/measure?project=de.triology.jenkinsfile%3Awildfly-kitchensink&metric=alert_status)](https://sonarcloud.io/dashboard?id=de.triology.jenkinsfile%3Awildfly-kitchensink) | |
| 11. Kubernetes                          | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/11-declarative)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/11-declarative/) | [![Build Status](https://opensource.triology.de/jenkins/buildStatus/icon?job=triologygmbh-github/jenkinsfile/11-scripted)](https://opensource.triology.de/jenkins/job/triologygmbh-github/job/jenkinsfile/job/11-scripted/) |  |

# Further resources

## `Jenkinsfile`s

* [triologygmbh/test-data-loader](https://github.com/triologygmbh/test-data-loader) - Deploys to maven central
* [cloudogu/continuous-delivery-docs-example](https://github.com/cloudogu/continuous-delivery-docs-example) - Docs as code to ODT & PDF
* [cloudogu/continuous-delivery-slides-example](https://github.com/cloudogu/continuous-delivery-slides-example) - Deploy presentations to Kubernetes and Maven Sites (Nexus) 
 
## Shared libraries

* [cloudogu/ces-build-lib](https://github.com/cloudogu/ces-build-lib)
* [mozilla/fxtest-jenkins-pipeline](https://github.com/mozilla/fxtest-jenkins-pipeline)
* [docker/jenkins-pipeline-scripts](https://github.com/docker/jenkins-pipeline-scripts)
* [fabric8io/fabric8-pipeline-library](https://github.com/fabric8io/fabric8-pipeline-library)
* [Shared Library Demo](https://github.com/jenkinsci/workflow-aggregator-plugin/tree/master/demo)
 
 
## Others

* [jenkinsci/JenkinsPipelineUnit](https://github.com/jenkinsci/JenkinsPipelineUnit) - Unit test for Jenkins pipelines
