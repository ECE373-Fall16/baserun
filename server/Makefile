CC=g++ -std=c++11
CLASSPATH=./lib_java/commons-codec-1.3.jar:./lib_java/xmlrpc-2.0.1.jar:.
DEBUG=-g
CFLAGS=$(DEBUG) -c
SERVER_SOURCE=server.cpp
SERVER_INCLUDE=-I. -I./include -I./include
SERVER_LIB=./lib/libxmlrpc_server_abyss++.a ./lib/libxmlrpc_server++.a ./lib/libxmlrpc_abyss++.a ./lib/libxmlrpc_server_abyss.a ./lib/libxmlrpc_server.a ./lib/libxmlrpc_abyss.a -lssl -lcrypto ./lib/libxmlrpc++.a ./lib/libxmlrpc_util++.a ./lib/libxmlrpc.a ./lib/libxmlrpc_xmlparse.a ./lib/libxmlrpc_xmltok.a ./lib/libxmlrpc_util.a -lpthread     

CLIENT_SOURCE=client.cpp
CLIENT_INCLUDE=-I. -I./include -I./include
CLIENT_LIB=./lib/libxmlrpc_client++.a ./lib/libxmlrpc_client.a ./lib/libxmlrpc++.a ./lib/libxmlrpc_util++.a ./lib/libxmlrpc.a ./lib/libxmlrpc_xmlparse.a ./lib/libxmlrpc_xmltok.a ./lib/libxmlrpc_util.a -lpthread -L/usr/lib/x86_64-linux-gnu -lcurl ./lib/libxmlrpc_packetsocket.a   


all:	ServerTest.class
	$(CC) $(CFLAGS) $(SERVER_INCLUDE) server.cpp sessionList-class.cpp session-class.cpp team-class.cpp linkedList.cpp
	$(CC) -o xmlrpc-server server.o sessionList-class.o session-class.o team-class.o linkedList.o $(SERVER_LIB)
	
server:
	./xmlrpc-server

client:
	@java -classpath $(CLASSPATH) ServerTest

ServerTest.class:
	javac -classpath $(CLASSPATH) ServerTest.java

clean:
	rm -rf *.o xmlrpc-server xmlrpc-client
	rm ServerTest.class

