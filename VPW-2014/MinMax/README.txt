# How to run
1. Delete STDIN.txt, everything gets provided through System.in!!
2. Compile ```javac Main.java```
2. Run ```java -Xrs -Xmx262144k Main.java```

# Input
input is provided through System.in (Create a new scanner and you get the lines fed in there)

## Example STDIN
4
4 programmeren
13 implementatie
3 winnen
1 team

## Example (Reading through System.in (OFFICIAL))
Scanner scanner = new Scanner(System.in)
var amountOfLines = scanner.nextInt();
for (var i = 0; i < amountOfLines; i++) {
    int position = scanner.nextInt();
    String woord = scanner.next();
}

## Example (Reading through STDIN.txt (UNOFFICIAL))
Scanner scanner = new Scanner(new File("./STDIN.txt"));
var amountOfLines = scanner.nextInt();
for (var i = 0; i < amountOfLines; i++) {
    int position = scanner.nextInt();
    String woord = scanner.next();
}

## Veel gebruikte libraries
import java.lang.I
nteger;
import java.lang.Object;
import java.lang.Override;
import java.lang.System;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;