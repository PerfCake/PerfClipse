Perfclipse
==========

Eclipse plugin which brings support for [PerfCake](https://www.perfcake.org/).

Steps to build plugin update site
---------------------------------
* Build p2 repository (needed for plugin build) - under $PERFCLIPSE_DIR/org.perfclipse.repo directory run:
```sh
$ mvn (clean) p2:site
```
* Build PerfClipse plugin - under the $PERFCLIPSE_DIR directory run:
```sh
$ mvn (clean) package
```
* Update site can be found under $PERFCLIPSE_DIR/org.perfclipse.update/target/repository directory.
