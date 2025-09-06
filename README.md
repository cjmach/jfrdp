#  jfrdp

A RDP display component for Java Swing. Main features are:
- Provides a `RdpDisplay` component, derived from Java Swing `JPanel` class, that allows to connect to and interact with a RDP server.
- Provides Java bindings to [FreeRDP](https://github.com/FreeRDP/FreeRDP) implemented using [JNA](https://github.com/java-native-access/jna).
- Supports mouse and keyboard input.
- Supports bidirectional clipboard.
- Supports drive mapping.

A Java client application is also available to test the component.

# Requirements

- Java Runtime Environment 8+
- [FreeRDP](https://github.com/FreeRDP/FreeRDP) v3.11+

# Building

To build this project you need:

- Java Development Kit 8
- Apache Maven 3.6.x

Assuming all the tools can be found on the PATH, simply go to the project 
directory and run the following command:

```console
$ mvn -B package
```

# Releasing

Go to the project directory and run the following commands:

```console
$ mvn -B release:prepare
$ mvn -B release:perform -Darguments='-Dmaven.deploy.skip=true' 
```

It will automatically assume the defaults for each required parameter, namely,
`releaseVersion` and `developmentVersion`. If it's necessary to control the values 
of each version, the `release:prepare` command can be run as follows:

```console
$ mvn -B release:prepare -DreleaseVersion={a release version} -DdevelopmentVersion={next version}-SNAPSHOT
```