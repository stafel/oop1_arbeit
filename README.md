# OOP1 Projektarbeit

## Referenzverwaltung

## Goal

Make referencing sources simple no matter the technology used.

## Maven config

To build anything navigate into the maven directory.

Start application directly with command:
```
mvn clean javafx:run
```

You can also package it as a fat JAR with all dependencies with the command:
```
mvn clean compile package
```

## Git structure

diagram: contains gaphor uml and other image documentation
dokumentation: latex file that generated the "Stauffer Rafael OOP1 Dokumentation.pdf" file
maven: the main maven project
poc_bibtex: minimal proof of concept to generate BibTex reference documents out of java
szenariobeschrieb: latex file that generated the "Stauffer Rafael OOP1 Szenarien Beschrieb.pdf"