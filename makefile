# Copyright (c) 2010 Martin Ueding <dev@martin-ueding.de>

.PHONY: buddler.jar
buddler.jar:
	javac Digger.java
	jar cfm buddler.jar manifest.txt *.class *.java *.properties *.png

