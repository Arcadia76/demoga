Publishing the Java artifact to GitHub Packages

Overview
- This repository is configured to build and publish the Maven artifact produced by the app in java-app to GitHub Packages using GitHub Actions.
- On every push, the CI workflow builds and tests the project. If successful, it deploys the artifact to the GitHub Packages Maven registry for this repository.

How publishing works
1. The workflow file .github/workflows/build-java-app.yml sets up JDK 21 and Maven with caching.
2. It grants the job the required permissions (packages: write) and uses the built-in GITHUB_TOKEN to authenticate.
3. It runs mvn deploy with an altDeploymentRepository pointing at the GitHub Packages URL for the current repo, so no change to your local settings.xml is required for CI.

Key details
- Deploy command used by CI:
  ./mvnw -B -ntp -DskipTests \
    -DaltDeploymentRepository=github::default::https://maven.pkg.github.com/${OWNER}/${REPO} \
    deploy
- Authentication is provided by actions/setup-java, which injects a server named github using GITHUB_ACTOR and GITHUB_TOKEN.
- The POM coordinates are defined in java-app/pom.xml:
  groupId: org.example.app
  artifactId: demo
  version: 0.0.1-SNAPSHOT

Consuming the package from another project
Add the GitHub Packages repository and credentials to your consumer project’s settings.xml (recommended) or use environment variables. Example settings.xml server entry:

  <servers>
    <server>
      <id>github</id>
      <username>${env.GITHUB_ACTOR}</username>
      <password>${env.GITHUB_TOKEN}</password>
    </server>
  </servers>

And add the repository and dependency to your consumer project’s POM:

  <repositories>
    <repository>
      <id>github</id>
      <url>https://maven.pkg.github.com/OWNER/REPO</url>
    </repository>
  </repositories>

  <dependency>
    <groupId>org.example.app</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
  </dependency>

Replace OWNER/REPO with this repository’s owner and name.

Manual local deploy (optional)
If you want to deploy from your local machine to GitHub Packages, run:

  export GITHUB_ACTOR=your-username
  export GITHUB_TOKEN=your-classic-or-fine-grained-token
  cd java-app
  ./mvnw -DaltDeploymentRepository=github::default::https://maven.pkg.github.com/OWNER/REPO deploy

Notes
- For private repos, the consumer must authenticate to read the package.
- For public repos, reading generally does not require authentication, but Maven often requires a server stanza; using env vars is the simplest approach.
