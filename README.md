<div align="center">
  <img src="https://i.imgur.com/ZM4KTZG.png"><br><br>
</div>

-----------------

2D Side Scroller MMO Engine written in Java.

## Screenshots

<img src="https://i.imgur.com/dhXHUxO.jpg">

<img src="https://i.imgur.com/sKuNw54.png">

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

libGDX      - https://libgdx.badlogicgames.com/

JDK (v8.1)  - https://www.oracle.com/technetwork/java/javase/downloads/index.html

Kryonet     - https://github.com/EsotericSoftware/kryonet/releases

H2 Embedded - http://www.h2database.com/html/main.html

### Installing

OUTDATED:
Download and install JDK

Download and install Eclipse

Download and extract Slick2d

Download and extract Kryonet

Download and extract Derby

Download project from github

Open project in eclipse by clicking:
  * File
  * Open Projecs from File System
  * Find Project and click Select Folder
  
Create a folder in the project directory called lib

Add jinput.jar, lwjgl_util.jar, lwgjl.jar, ibxm.jar, and slick.jar from slick2d to the lib folder

Add derby.jar, derbyclient.jar, derbynet.jar, derbyrun.jar, derbytools.jar to the the lib folder

Add kryonet-2.21-all.jar to the lib folder

*If errors arise, rick click the project in the package explore and click properties. Go to Java build path and then click on the libraries tab. If there is a jar file missing, click add JAR and navigate to the lib folder and ctrl-click all of them, then click add.

*If you are using itellij, add them to the same folder then highlight them all, right click and then select "Add library"

## Deployment

Compile jars and distrubute

## Built With

* [libGDX](https://libgdx.badlogicgames.com/) - The graphics framework used

* [H2](http://www.h2database.com/html/main.html) - SQL databse used

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Gregory Plachno** - *Creator* - [Jumbofile](https://github.com/Jumbofile)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

