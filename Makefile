all:
	rm -f *.class client
	javac -d . -classpath . ClientPrograms.java
	
	file="./client"
	echo "#!/bin/sh\njava ClientPrograms \$$1 \$$2" >./client
	chmod +x client
