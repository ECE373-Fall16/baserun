CLASSPATH=../client/lib/commons-codec-1.3.jar:../client/lib/xmlrpc-2.0.1.jar:.

all: ServerTest.class

client:
	@java -classpath $(CLASSPATH) ServerTest

ServerTest.class:
	javac -classpath $(CLASSPATH) ServerTest.java

clean:
	rm *.class
