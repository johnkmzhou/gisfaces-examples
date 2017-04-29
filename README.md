# gisfaces-examples

Web application running on WildFly 10.1.0.Final.

## Installation
Install Java JDK 8. If you're running on Windows be sure to set your system variable JAVA_HOME to the location of your JDK.

    java -version

The command above should print out a version that is at least 1.8.0.

Install Maven if you plan to use the CLI. Otherwise, you can use Eclipse's Maven plugin.

    maven -version

For the CLI the command above should print out a version that is at least 3.1.0.

Install WildFly.

Download GISFaces from https://www.gisfaces.com/ and copy the gisfaces jar to the project's /src/main/webapp/WEB-INF/lib folder.

If you're using Eclipse Java EE:

1. Go to Preferences → Server → Runtime Environments, click Add..., and select JBoss AS, WildFly, & EAP Server Tools to install JBoss Tools.
2. After that you can define a new server via the same process. Select WildFly 10.x Runtime, set the Home Directory to the directory of your WildFly server, and make sure you set the Runtime JRE to your installed JDK.
3. Now you can import the Maven project and deploy it to the WildFly server in Eclipse.

If you want more details or other ways on getting started you can find them here: 
https://github.com/wildfly/quickstart/blob/10.x/README.md
https://github.com/wildfly/quickstart/blob/10.x/guide/GettingStarted.asciidoc
