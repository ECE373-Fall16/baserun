CLASSPATH=./lib/commons-codec-1.3.jar:./lib/xmlrpc-2.0.1.jar:.

all: GameTimer.class Base.class User.class GameNetwork.class GameList.class  Game.class TestSuite.class 

server:
	@java -classpath $(CLASSPATH) TestServer

client:
	@java -classpath $(CLASSPATH) TestSuite
	
GameTimer.class:
	javac GameTimer.java
Base.class:
	javac  Base.java
GameList.class:
	javac GameList.java
TestSuite.class:
	javac -classpath $(CLASSPATH) TestSuite.java
Game.class:
	javac Game.java
GameNetwork.class:
	javac -classpath $(CLASSPATH) GameNetwork.java
User.class:
	javac User.java
clean: 
	rm *.class
