CLASSPATH=./lib/commons-codec-1.3.jar:./lib/xmlrpc-2.0.1.jar:.

all: Base.class GameList.class TestServer.class TestSuite.class BaseRunClient.class Game.class Network.class User.class

Base.class:
	javac Base.java
GameList.class:
	javac GameList.java
TestServer.class:
	javac -classpath $(CLASSPATH) TestServer.java
TestSuite.class:
	javac TestSuite.java
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
