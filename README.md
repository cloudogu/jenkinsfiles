triologygmbh/jenkinsfile 
========================
This project contains examples for the [Jenkins pipeline plugin](https://jenkins.io/solutions/pipeline/), comparing both declarative and scritped syntax.

The project being built by the pipeline examples is [wildfly/quickstart/kitchensink](https://github.com/wildfly/quickstart/tree/cfd2e05d16e4ae788bc12486f5b30d668b921973/kitchensink), a typical JEE web app basing on CDI, JSF, JPA, EJB, JAX-RS and integration tests with arquillian.
It was extended slightly to allow for running integration tests using WildFly Swarm. 

The pipeline examples are built on top of each other, each in declarative and scripted syntax, respectively. Each example is put on a separate [branches](https://github.com/triologygmbh/jenkinsfile/branches) for convenient access.

Please see [our Jenkins Instance](https://opensource.triology.de/jenkins/blue/organizations/jenkins/triologygmbh-github%2Fjenkinsfile/branches) for build results.

The following aspects are covered by the examples:

1. A simple pipeline ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/1-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/1-scripted/Jenkinsfile)) 
2. Improving maintainability by introducing custom steps ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/2-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/2-scripted/Jenkinsfile))
3. Division into smaller stages ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/3-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/3-scripted/Jenkinsfile))
4. a End of pipeline run and handling failures ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/4a-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/4a-scripted/Jenkinsfile))  
   b Simplified Mailing ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/4b-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/4b-scripted/Jenkinsfile))
5. Archive and Properties/Options ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/5-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/5-scripted/Jenkinsfile))
6. Parallel ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/6-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/6-scripted/Jenkinsfile))
7. Time Triggered Builds (e.g. nightly) ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/7-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/7-scripted/Jenkinsfile))
8. Shared libraries ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/8-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/8-scripted/Jenkinsfile) | [shared library](https://github.com/triologygmbh/jenkinsfile/tree/8-shared-library))
9. Docker  
   a Run whole pipeline inside a Docker container ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/9a-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/9a-scripted/Jenkinsfile))  
   b Using Docker inside a custom step (implemented in shared library) ([declarative](https://github.com/triologygmbh/jenkinsfile/blob/9b-declarative/Jenkinsfile) | [scripted](https://github.com/triologygmbh/jenkinsfile/blob/9b-scripted/Jenkinsfile) | [shared library](https://github.com/triologygmbh/jenkinsfile/tree/9b-shared-library))