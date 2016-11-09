CLASSPATH=../client/lib/commons-codec-1.3.jar:../client/lib/xmlrpc-2.0.1.jar:.
CC=g++
DEBUG=-g
CFLAGS=$(DEBUG) -c
SERVER_INCLUDE=-I. -I./include -I./include
SERVER_LIB=./lib/libxmlrpc_server_abyss++.a ./lib/libxmlrpc_server++.a ./lib/libxmlrpc_abyss++.a ./lib/libxmlrpc_server_abyss.a ./lib/libxmlrpc_server.a ./lib/libxmlrpc_abyss.a -lssl -lcrypto ./lib/libxmlrpc++.a ./lib/libxmlrpc_util++.a ./lib/libxmlrpc.a ./lib/libxmlrpc_xmlparse.a ./lib/libxmlrpc_xmltok.a ./lib/libxmlrpc_util.a -lpthread

all: ServerTest.class server.cpp sessionList-class.cpp team-class.cpp

client:
	@java -classpath $(CLASSPATH) ServerTest

server:
	./xmlrpc-server

ServerTest.class:
	javac -classpath $(CLASSPATH) ServerTest.java

server.cpp:
	$(CC) $(CFLAGS) $(SERVER_INCLUDE) server.cpp -o server.o
	$(CC) -o xmlrpc-server server.o $(SERVER_LIB)
sessionList-class.cpp:
	$(CC) $(CFLAGS) $(SERVER_INCLUDE) sessionList-class.cpp -o sessionList-class.o
	$(CC) -o sessionList-class sessionList-class.o $(SERVER_LIB)
team-class.cpp:
	$(CC) $(CFLAGS) $(SERVER_INCLUDE) team-class.cpp -o team-class.o
	$(CC) -o team-class team-class.o

clean:
	rm *.class
