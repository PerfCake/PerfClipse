Perfclipse
==========

Eclipse plugin which brings support for [PerfCake](https://www.perfcake.org/).

Steps to build plugin update site
---------------------------------
1.) Build p2 repository (dependencies for plugin build) - under $PERFCLIPSE_DIR/org.perfclipse.repo directory run:
´´´sh
$ mvn (clean) p2:site
´´´
2.) Build PerfClipse plugin - under the $PERFCLIPSE_DIR directory run:
´´´sh
$ mvn (clean) package
´´´
3.) Update site can be found under $PERFCLIPSE_DIR/org.perfclipse.update/target/repository directory.
