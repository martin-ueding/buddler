.PHONY: buddler.jar
buddler.jar:
	javac Digger.java
	jar cfm buddler.jar manifest.txt *.class *.java *.properties *.png

