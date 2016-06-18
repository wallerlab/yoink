# Yoink  
[![Build Status](https://travis-ci.org/wallerlab/yoink.svg?branch=master)](https://travis-ci.org/wallerlab/yoink)
[![Coverage Status](https://coveralls.io/repos/github/wallerlab/yoink/badge.svg?branch=master)](https://coveralls.io/github/wallerlab/yoink?branch=master)
[![Apache icense](http://img.shields.io/badge/license-APACHE2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

*Yoink is a program for performing (adaptive) QM/MM partitioning.*



*Website: [`http://yoink.wallerlab.org`](http://yoink.wallerlab.org)*



#Quickstart

To obtain a copy of the source code, please enter the following [git](https://git-scm.com/) command:

`git clone https://github.com/wallerlab/yoink.git`

`cd yoink`

In order to run yoink, please use the following [gradle ](https://gradle.org/) command:

`./gradlew yoink`



# Features
### -	Adaptive QM/MM partitioning methods
Its main feature is to do density based adaptive QM/MM partitioning. Also distance and number based partitioning methods are available.

| Partitioning    | Citation           | 
| -------------   |-------------|
| Density-based      | Waller, Mark P., et al. "A Density‐Based Adaptive Quantum Mechanical/Molecular Mechanical Method." ChemPhysChem 15.15 (2014): 3218-3225.| 
| Distance-based      | Kerdcharoen, et al. "A QM/MM simulation method applied to the solution of Li+ in liquid ammonia." Chemical physics 211.1 (1996): 313-323.|
| Number-based      | Takenaka, Norio, et al. "The number-adaptive multiscale QM/MM molecular dynamics simulation: Application to liquid water." Chemical Physics Letters 524 (2012): 56-61. | 


 
### -	Smoothing methods
 Methods to alleviate the discontinuity problem:

| Adaptive | Citation |
| ------------- |-------------|
| Buffered Force | Bernstein, Noam, et al. "QM/MM simulation of liquid water with an adaptive quantum region." Physical Chemistry Chemical Physics 14.2 (2012): 646-656. |
| Difference-Based Adaptive Soltuion | Bulo, Rosa E., et al. "Toward a practical method for adaptive QM/MM simulations." Journal of Chemical Theory and Computation 5.9 (2009): 2212-2221.|


### -	Clustering methods
| Clustering | Citation |
| ------------- |-------------|
| Louvain | Vincent D Blondel, Jean-Loup Guillaume, Renaud Lambiotte, Etienne Lefebvre, Journal of Statistical Mechanics: Theory and Experiment 2008 (10), P10008 (12pp) doi: 10.1088/1742-5468/2008/10/P10008 |


### -	Documentation
| Page | description |
| ------------- |-------------|
|[Code Structure](https://github.com/wallerlab/yoink/wiki/structure) | An overview of the modules and sequence diagrams |
|[Options](https://github.com/wallerlab/yoink/wiki/options) | An explanation of each of the options available in yoink |
|[deploy](https://github.com/wallerlab/yoink/wiki/deploy) | An explanation on how to deploy yoink |
|[Publish](https://github.com/wallerlab/yoink/wiki/publish) | An explanation on how to publish artifacts from yoink |



