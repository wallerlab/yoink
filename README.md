# Yoink  

*Yoink is a JAVA program for adaptive QM/MM partitioning. It enables one to identify QM and MM regions in a given system with a predefined fixed QM core. It is Licensed under [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0).*

[![Build Status](https://travis-ci.org/wallerlab/yoink.svg?branch=master)](https://travis-ci.org/wallerlab/yoink)

[![Coverage Status](https://coveralls.io/repos/github/wallerlab/yoink/badge.svg?branch=master)](https://coveralls.io/github/wallerlab/yoink?branch=master)


#Quickstart

###1. Cloning
To obtain a copy of the source code, please enter the following [git](https://git-scm.com/) command:

 `git clone https://github.com/wallerlab/yoink.git`

` cd yoink`

###2. Building
In order to build the yoink program please use the following [gradle ](https://gradle.org/) command:

`./gradlew clean build`

####Note: Yoink should be rebuilt using the above gradle command anytime changes are made to the source code.

After the building phase is completed, the executable jar file can be found in:

`build/libs/Yoink-{version}.jar`

In order to read java documentation use the following command:

`./gradlew alljavadoc`

then open file `build/docs/javadoc/index.html` with web browser

###3. Input Preparation

* The Yoink program scans all *.xml files in the folder inputs (default) to find any job requests that will be processed by Yoink.
* There are some example xml input files in `./src/main/resources`
* The required xml file is in the format of [CML](http://www.xml-cml.org/). In the beginning, we need to predefine the fixed QM core and the rest as MM, see example below.	
* For every molecular system, we can define specific parameters in the section of parameterList in xml file. 
* #### Note: The `application.properties` file contains default parameters.

###4. Run

`java -jar  Yoink-{version}.jar`

After it starts, it will keep looking for *.xml files in the input folder and execute corresponding jobs unless the user terminates it.

###5. Result
* The results will be written as *.xml files in the output folder. The ID of molecule represents the region to which it belongs.
* The QM region includes molecules labelled as: QM_CORE_FIXED,QM_CORE_ADAPTIVE,QM_ADAPTIVE
* The MM region includes molecules labelled as: MM_NOBUFFER, BUFFER
* The smoothing factors are shown as a PropertyList in the output xml files.


# Examples
1. after gradle build is finished, go to folder "examples"

 `cd examples `

2.  copy the executable jar file "build/libs/Yoink-{version}.jar" to current folder

 ` cp  ../build/libs/Yoink-{version}.jar ./ `

3. make a new folder called "inputs"

 ` mkdir inputs `

4. copy example input xml files in "src/main/resources" to new built folder "./inputs"

 ` copy ../src/main/resources/* ./inputs`

5. make a new folder called "outputs"

 ` mkdir outputs`

6. run examples

 ` java -jar Yoink-{version}.jar`


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


# Structure
Subprojects in total:

| Subproject              | Description        | 
| -------------           |------------|
|  yoink-core-api         |contains all the interfaces used in Yoink | 
|  yoink-core-math        |common mathematic operations and functions Yoink needs |
| yoink-core-molecular    |basic description and operation about a molecular system | 
|  yoink-core-cube        |is used to do density analysis for a molecular system| 
|  yoink-core-density     |density and related properties(DORI/SEDD) calculation |
|  yoink-core-regionizer  |calculate and define all regions| 
|  yoink-core-adaptive    |calculate smoothing factors for the buffer region|
| yoink-core-bootstrap    |set up and execute adaptive QM/MM partitioning |


# Sequence Diagram

![yoink.png](https://github.com/wallerlab/yoink/blob/master/yoink-sequence.png)


### -	Regionizer zoom-in


![regionizer.png](https://github.com/wallerlab/yoink/blob/master/regionizer.png)
