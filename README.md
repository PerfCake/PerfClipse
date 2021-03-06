Perfclipse
==========

Eclipse plugin which brings support for [PerfCake](https://www.perfcake.org/).

Steps to build plugin update site
---------------------------------
* Build p2 repository (needed for plugin build) - under $PERFCLIPSE_DIR/org.perfclipse.repo directory run:  
```sh
$ mvn (clean) p2:site
```
* (A)  Build unsigned PerfClipse plugin - under the $PERFCLIPSE_DIR directory run:  
```sh
$ mvn (clean) package
```
* (B) Build signed PerfClipse plugin using jarsigner - under the $PERFCLIPSE_DIR directory run:  
```sh
$ mvn (clean) package -Djarsigner.skip=false -Djarsigner.keystore=<paht-to-keystore> -Djarsigner.storepass=<keystore-password>  -Djarsigner.alias=<certifacete-alias> -Djarsigner.keypass=<key-password>
```
* Update site can be found under $PERFCLIPSE_DIR/org.perfclipse.update/target/repository directory.

Versioning
----------

This repository does not use any special workflow and everything is commited directly to master for now.

Bug reporting
-------------

Please report bugs directly to the issues in this repository.
