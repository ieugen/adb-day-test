Http Static File server
=======================

A simple HTTP file server for static files written in Java. Based on HTTP file server example from Netty project.
http://static.netty.io/3.5/xref/org/jboss/netty/example/http/file/package-summary.html

**Features**

* High performance, multi-threading - based on Netty network library (http://netty.io)
* Large file support
* Configurable via YAMML configuration file
* supports directory listing via Mustache templates (http://mustache.github.com/)
* supports HTTP Keep Alive

Requirements
============

Tested on Linux, should be multi-platform.

* Git - to get the sources from https://github.com/ieugen/adb-day-test
* Java JDK >= 1.6 to build the sources
* Apache Maven >= 3.0.3 - as a build tool

How to build
============

~~~
    git clone git@github.com:ieugen/adb-day-test.git
    cd adb-day-test
    mvn clean install
~~~

After it builds successfully unpack the archive and run:

~~~
    cd target/
    tar zxf file-server-0.1-application.tar.gz
    cd file-server-0.1
    ./http-fileserver.sh
~~~

You should get output similar to this:

~~~
    $ ./http-fileserver.sh
    [main] INFO ro.ieugen.fileserver.Main - Reading configuration from http-server-conf.yml
    [main] INFO ro.ieugen.fileserver.http.HttpStaticFileServer - Starting HTTP server on port 9112 with root src/
~~~


Configuration
=============

You can configure the server via a configuration file written in YAMML (http://www.yaml.org/spec/1.2/spec.html).

Options are:

* **port** - the port on which to serve files, default 9111
* **root** - a path for the root directory from which we serve files, default is current working directory
* **workerThreadCount** - the number of Netty worker threads to server files, default is 4
* **template** - Mustache template for rendering the directory listing, default is 'index-template.html'

You can change the configuration file name the server reads via **http.server.config** system property
(-Dhttp.server.config="<new-config-file>").
Sample configuration options in the file may look like:

~~~
port: 9112
root: root/
~~~
