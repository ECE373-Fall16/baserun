CLASSPATH=./lib/commons-codec-1.3.jar:./lib/xmlrpc-2.0.1.jar:.

all: Base.class Network.java User.class TestServer.class Game.class TestSuite.class

server:
	@java -classpath $(CLASSPATH) TestServer

client:
	@java -classpath $(CLASSPATH) TestSuite
	
Base.class:
	javac  Base.java
GameList.class:
	javac GameList.java
TestServer.class:
	javac -classpath $(CLASSPATH) TestServer.java
TestSuite.class:
	javac -classpath $(CLASSPATH) TestSuite.java
BaseRunClient.class:
	javac BaseRunClient.java
Game.class:
	javac Game.java
Network.class:
	javac -classpath $(CLASSPATH) Network.java
User.class:
	javac User.java
clean: 
	rm *.class
