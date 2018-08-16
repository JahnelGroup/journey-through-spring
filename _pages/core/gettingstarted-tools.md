---
title:  "Tooling Up"
permalink: /core/gettingstarted-tools
---

It all begins with the right tools. Take the time to set yourself up for success from the start and it will make all the difference down the road. 

## Linux v.s. Mac v.s. Windows
Professional software developers need to align themselves with the best tools available to get their job done. If you're a .NET developer or work a lot with Microsoft products then Windows is an obvious choice. Everyone else should be on some kind of Linux distrubution or a Mac. The best software tools are just typically harder to access and work with on Windows. If you're following this journey I recommend you use Linux or Mac. I personally use [Fedora](https://getfedora.org/){:target="_blank"} but there are plenty other great options available, [Ubuntu](https://www.ubuntu.com/){:target="_blank"} is a great alternative.

* **Read:** [**Lifewire:** How To Choose The Best Linux Distro For Your Needs](https://www.lifewire.com/choose-best-linux-distro-for-needs-2201172){:target="_blank"}
* **Read:** [**Digital Ocean:** Linux Basics](https://www.digitalocean.com/community/tags/linux-basics?type=tutorials){:target="_blank"}
* **Read:** [**Digital Ocean:** An Introduction to Linux Basics](https://www.digitalocean.com/community/tutorials/an-introduction-to-linux-basics){:target="_blank"}

## Installing Tools and Knowing their Versions
As a software developer it’s important to know your tools. You should know exactly what tools are being used, where they are installed and what versions they are. It’s important that we install these tools in a uniform and systematic way. 

### Install SDKMAN!
SDKMAN is a great tool for installing the software needed for developing our Spring applications. It allows you to install multiple versions (called candidates) of software at the same time and allows you to easily switch between them. SDKMAN will persist data in your home directory ~/.sdkman. 

* **Install:** [SDKMAN](https://sdkman.io/install){:target="_blank"}
* **Read:** [SDKMAN usage](https://sdkman.io/usage){:target="_blank"}

### Install Java and Kotlin

We can build Spring with Java and Kotlin so install both using SDKMAN.

```bash
$ sdk install java
$ sdk install kotlin
```

### Install Maven and Gradle
Spring developers depend on a number of different tools to deliver software. As a professional developer one of the first you'll need is a build and dependency management tool. The two biggest names in the community now are [Maven](https://maven.apache.org/){:target="_blank"} and [Gradle](https://gradle.org/){:target="_blank"}. Maven has been around longer with more example code and is really stable. Gradle is relatively new, adopted strongly by the Android community and is showing a lot more power than Maven as of late. Both will get the job done but the community is favoring Gradle. 

* **Read:** [**zeroturnaround:** Java Build Tools: How Dependency Management Works](https://zeroturnaround.com/rebellabs/java-build-tools-how-dependency-management-works-with-maven-gradle-and-ant-ivy/){:target="_blank"}

Install Maven and Gradle with SDKMAN.

```bash
$ sdk install maven
$ sdk install gradle
```

Follow these exercises and ignore the part about installing Maven or Gradle. 

* **Exercise:** [**spring.io:** Building Java Projects with Maven](https://spring.io/guides/gs/maven/){:target="_blank"}
* **Exercise:** [**spring-guides:** Building Java Projects with Gradle](https://github.com/spring-guides/gs-gradle){:target="_blank"}

### Install Spring Boot Command Line Interface (Spring CLI)
The Spring Boot CLI is a tool that can help you rapidly prototype applications right from the terminal. It can be used to run Spring-Boot groovy scripts and init your application with the Spring Starter projects. 

Install Spring Boot CLI with SDKMAN.

```bash
$ sdk install springboot
```

Follow this quick example to start a groovy web-app instantly from the command-line.

* **Exercise:** [**spring.io:** Running Applications with the CLI](https://docs.spring.io/spring-boot/docs/current/reference/html/cli-using-the-cli.html#cli-run){:target="_blank"}

### Install IntelliJ
[**jetbrains:** IntelliJ](https://www.jetbrains.com/idea/){:target="_blank"} has become the standard IDE used by most Java developers, [Eclipse](https://www.eclipse.org/){:target="_blank"} being the other.

* **Install:** [**jetbrains:** Install and set up IntelliJ IDEA](https://www.jetbrains.com/help/idea/install-and-set-up-intellij-idea.html){:target="_blank"}