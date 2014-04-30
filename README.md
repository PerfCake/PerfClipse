Perfclipse
==========

Eclipse plugin which brings support for [PerfCake](https://www.perfcake.org/).

Steps to build plugin update site
---------------------------------
1. Build p2 repository (needed for plugin build) - under $PERFCLIPSE_DIR/org.perfclipse.repo directory run:
```sh
$ mvn (clean) p2:site
```
2. Build PerfClipse
⋅⋅*  Build unsigned PerfClipse plugin - under the $PERFCLIPSE_DIR directory run:⋅⋅
```sh
$ mvn (clean) package
```
⋅⋅* Build signed PerfClipse plugin using jarsigner - under the $PERFCLIPSE_DIR directory run:⋅⋅
```sh
$ mvn (clean) package -Djarsigner.skip=false -Djarsigner.keystore=<paht-to-keystore> -Djarsigner.storepass=<keystore-password>  -Djarsigner.alias=<certifacete-alias> -Djarsigner.keypass=<key-password>
```
* Update site can be found under $PERFCLIPSE_DIR/org.perfclipse.update/target/repository directory.
