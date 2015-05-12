[![Build Status](https://travis-ci.org/devhub-tud/devhub.svg?branch=master)](https://travis-ci.org/devhub-tud/devhub)
[![Stories in Ready](https://badge.waffle.io/devhub-tud/devhub.png?label=ready&title=Ready)](https://waffle.io/devhub-tud/devhub)
DevHub
======

DevHub is a software system designed to give students a simple practical introduction into modern software development. It provides an environment in which they can work on practical assignments without setting up their own (private) code repository or a Continuous Integration server. The environment is also designed to give students a working simple workflow largely based on GitHub's pull requests system. 

Architecture
------------

DevHub is comprised of several components which integrate nicely. The `devhub-server` project is a web application which can be run from the command line, and provides easy overviews of projects and their activity. The `devhub-client` project is an application which is able to manage one or multiple Docker containers on which builds and other activities can take place in a secure and shielded environment.

Contributing
------------
To run this project, one must first fetch the dependencies.
The client dependencies are managed through [Bower](http://bower.io/).
To fetch the dependencies navigate to the project folder and run `mvn clean package` to do a `bower install` automatically.

```sh
cd ~/git/devhub
mvn clean package -DskipTests
```

If you don't have Bower installed you can do so by running the following shell command.

```sh
npm install -g bower
```

This requires `npm` (the [Node Package Manager](http://nodejs.org/))  to be installed on your system.
For Mac OS X this can be done using [Homebrew](http://brew.sh/). Brew is similar to `apt-get` for Ubuntu.
For Windows systems, an installer for Node can be found on the website.

```sh
brew update && brew doctor
brew install node
```
