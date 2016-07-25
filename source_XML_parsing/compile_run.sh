#!/bin/bash
javac -classpath ".:./mysql-connector-java-5.0.8-bin.jar" SAXParserFilm.java
javac -classpath ".:./mysql-connector-java-5.0.8-bin.jar" SAXParserCast.java
java -classpath .:./mysql-connector-java-5.0.8-bin.jar SAXParserFilm
java -classpath .:./mysql-connector-java-5.0.8-bin.jar SAXParserCast

