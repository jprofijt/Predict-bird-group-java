# Bird Classification using bone length's

Java application that when given a bird or bird fossil's 
bone lenghts, can classify and label the ecological group that bird belongs to.

Can classify 5 different groups:
* SW    :   Swimming Birds
* W     :   Wading birds
* R     :   Raptors
* P     :   Scansorial Birds
* SO    :   Singing Birds

Classifies based on:
* Length and Diameter of Humerus
* Length and Diameter of Ulna
* Length and Diameter of Femur
* Length and Diameter of Tibiotarsus
* Length and Diameter of Taesometatasus

## Usage
java -jar birdclassifier.jar -f <Birds_to_classify.arff>

### Options
* -h: returns help for the application
* -f [path/to/file]: use -f to point to the birds to classify file
* -c: use if the output should be formatted as csv
* -o [path/to/file]: use -o to specify a output file, defaults to BirdclassificationResults.arff or .csv

### to classify file
should be an arff file
with all required collums

Example:
```
@relation CleanDataAll

@attribute huml numeric
@attribute humw numeric
@attribute ulnal numeric
@attribute ulnaw numeric
@attribute feml numeric
@attribute femw numeric
@attribute tibl numeric
@attribute tibw numeric
@attribute tarl numeric
@attribute tarw numeric
@attribute type {SW,W,T,R,P,SO}


@data
15.24,1.4,18.37,1.13,13.75,1.05,25.57,0.91,19.1,0.86,?
41.65,3.62,46.01,3.03,38.37,2.85,48.11,2.55,35.49,2.22,?
46.38,3.41,39.8,3.48,54.67,4.08,85.88,4.04,63.75,3.09,?
```

Classification can be changed if the RandomForest.model file is changed out with a diffrent one, replaced by the user

This application will only work with RandomForest models. and if you choose to change model file the corresponding unknown file as the have the same structure as the new model.

## Getting Started

Go to [releases](https://github.com/jprofijt/Predict-bird-group-java/releases) tab and download latest release

* extract contents of BirdClassifier.zip
* run application in extracted folder, because necessary files are located here.


## Getting Started(development)
clone the project to your local machine for local development.
```
git clone https://github.com/jprofijt/Predict-bird-group-java.git
```
### Prerequisites

to build a working copy from source you'll need to install the gradle build tool:

https://gradle.org/install

On Arch based distributions:

```
sudo pacman -S gradle
```

for other distributions install manually following the gradle website or the listed package managers.

for gradle(jdk7+) but also for this project you'll need the Java JDK 10.2.0

https://www.oracle.com/technetwork/java/javase/downloads/index.html

```
java -version

openjdk version "10.0.2" 2018-07-17
OpenJDK Runtime Environment (build 10.0.2+13)
OpenJDK 64-Bit Server VM (build 10.0.2+13, mixed mode)

```

### Installing

navigate to project location and run:

```
gradle build
```
a distribution will be created in build/distributions/


## Authors

* **Jouke Profijt** - *Research into subject & application development* - [jprofijt](https://github.com/jprofijt)

## License

This project is licensed under the GPLv3 License - see the [LICENSE](LICENSE) file for details

## Acknowledgments

* Some parts of the code was used from [Michiel Noback](https://github.com/MichielNoback) as a base for the project.
* Model file and additional research from [Predict-bird-group](https://github.com/jprofijt/Predict-bird-group)
* Application made as school project
