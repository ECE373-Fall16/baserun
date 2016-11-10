#include <cassert>
#include <stdexcept>
#include <iostream>
#include <unistd.h>
#include <string>

#include "xmlrpc-c/base.hpp"
#include "xmlrpc-c/registry.hpp"
#include "xmlrpc-c/server_abyss.hpp"

#ifndef sessionList_class_h
#define sessionList_class_h
#include "sessionList-class.h"
#endif

#ifndef session_class_h
#define session_class_h
#include "session-class.h"
#endif

#ifndef session_structs_h
#define sessions_structs_h
#include "session-structs.h"
#endif

#ifndef team_class_h
#define team_class_h
#include "team-class.h"
#endif

using namespace std;

#define SLEEP(seconds) sleep(seconds);

sessionList slist;

class genID : public xmlrpc_c::method {
public:
    genID(){
        this->_signature = "A:i";
        this->_help = "This method initializes the session and returns it GID";
    }
    void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const  retvalP) {
        
        long int const pid(paramList.getInt(0));

        paramList.verifyEnd(1);

	long int gid = slist.addSession((long int)pid);

        vector<xmlrpc_c::value> arrayData;
        arrayData.push_back(xmlrpc_c::value_int(gid));
        xmlrpc_c::value_array array1(arrayData);
        *retvalP = array1;
	}
};

class createGame : public xmlrpc_c::method {
public:
    createGame(){
        this->_signature = "A:iidididd";
        this->_help = "This method initializes the session and returns it GID";
    }
    void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const  retvalP) {
        
        long int const gid(paramList.getInt(0));
	int const maxGameSize(paramList.getInt(1));
	double const radius(paramList.getDouble(2));
	int const numBases(paramList.getInt(3));
	double const startX(paramList.getDouble(4));
	double const startY(paramList.getDouble(5));

        paramList.verifyEnd(6);

	session *game = slist.getSession(gid);

	game->setMaxPlayerSize(maxGameSize);
	game->setRadius(radius);
	game->setNumBases(numBases);
	game->setStart(startX,startY);

	game->generateLocationArray();	

	int success =1;
	
        vector<xmlrpc_c::value> arrayData;
        arrayData.push_back(xmlrpc_c::value_int(success));
        xmlrpc_c::value_array array1(arrayData);
        *retvalP = array1;
	}
};

class join : public xmlrpc_c::method {
public:
    join(){
        this->_signature = "A:ii";
        this->_help = "This method adds a player";
    }
    void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const  retvalP) {
        
        long int const gid(paramList.getInt(0));
	long int const pid(paramList.getInt(1));

        paramList.verifyEnd(2);

	session *game = slist.getSession(gid);
	game->init_Player(pid);
	int tmNum = game->getPTeamNum(pid);
	
	team* tm = game->getTeam(tmNum);
	team* otherTm = game->getTeam((tmNum+1)%2);

        vector<xmlrpc_c::value> arrayData;
        arrayData.push_back(xmlrpc_c::value_int(30));//game->getNumPlayers()));

	player_t* pArr = tm->getPlayerArr();
	player_t* pArr2 = otherTm->getPlayerArr();
	int numP = tm->getNumPlayers();	
	int numP2 = otherTm->getNumPlayers();

	

	for(int i=0; i<numP; i++){
        	arrayData.push_back(xmlrpc_c::value_int(pArr[i].id));
        	arrayData.push_back(xmlrpc_c::value_int(tm->getTeamNum()));
	}
	for(int i=0; i<numP2; i++){
        	arrayData.push_back(xmlrpc_c::value_int(pArr2[i].id));
        	arrayData.push_back(xmlrpc_c::value_int(otherTm->getTeamNum()));
	}

	int baseSize = game->getNumBases();

        arrayData.push_back(xmlrpc_c::value_int(baseSize));
	base_t* bases = game->getBases();

	for(int i=0; i<baseSize; i++){
        	arrayData.push_back(xmlrpc_c::value_double(bases[i].x));
        	arrayData.push_back(xmlrpc_c::value_double(bases[i].y));
        	arrayData.push_back(xmlrpc_c::value_double(game->getBaseRadius()));

	}

        arrayData.push_back(xmlrpc_c::value_int(game->getMaxPlayerSize()));
        arrayData.push_back(xmlrpc_c::value_double(game->getStartX()));
        arrayData.push_back(xmlrpc_c::value_double(game->getStartY()));
        arrayData.push_back(xmlrpc_c::value_double(game->getRadius()));
	       
 
	xmlrpc_c::value_array array1(arrayData);
        *retvalP = array1;
	}
};

class refresh : public xmlrpc_c::method {
public:
    refresh(){
        this->_signature = "A:i";
        this->_help = "This method adds a player";
    }
    void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const  retvalP) {
        
        long int const gid(paramList.getInt(0));

        paramList.verifyEnd(1);

	session *game = slist.getSession(gid);
	

	team* tm = game->getTeam(1);
	team* otherTm = game->getTeam(2);

        vector<xmlrpc_c::value> arrayData;
        arrayData.push_back(xmlrpc_c::value_int(game->getNumPlayers()));

	player_t* pArr = tm->getPlayerArr();
	player_t* pArr2 = otherTm->getPlayerArr();
	int numP = tm->getNumPlayers();	
	int numP2 = otherTm->getNumPlayers();

	

	for(int i=0; i<numP; i++){
        	arrayData.push_back(xmlrpc_c::value_int(pArr[i].id));
        	arrayData.push_back(xmlrpc_c::value_int(tm->getTeamNum()));
	}
	for(int i=0; i<numP2; i++){
        	arrayData.push_back(xmlrpc_c::value_int(pArr2[i].id));
        	arrayData.push_back(xmlrpc_c::value_int(otherTm->getTeamNum()));
	}

	int baseSize = game->getNumBases();

        arrayData.push_back(xmlrpc_c::value_int(baseSize));
	base_t* bases = game->getBases();

	for(int i=0; i<baseSize; i++){
        	arrayData.push_back(xmlrpc_c::value_double(bases[i].x));
        	arrayData.push_back(xmlrpc_c::value_double(bases[i].y));
        	arrayData.push_back(xmlrpc_c::value_double(game->getBaseRadius()));

	}

 
	xmlrpc_c::value_array array1(arrayData);
        *retvalP = array1;
	}
};


class gameList : public xmlrpc_c::method {
public:
    gameList(){
        this->_signature = "A:i";
        this->_help = "This method adds a player";
    }
    void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const  retvalP) {
        
        long int const pid(paramList.getInt(0));

        paramList.verifyEnd(1);

        vector<xmlrpc_c::value> arrayData;

	session* glist = slist.getList();
	
	int numS = slist.getNumSession();
	
	arrayData.push_back(xmlrpc_c::value_int(numS));

	for(int gi=0; gi<numS; gi++){

		arrayData.push_back(xmlrpc_c::value_int(glist[gi].getGid()));
		arrayData.push_back(xmlrpc_c::value_int(glist[gi].getMaxPlayerSize()));
	        arrayData.push_back(xmlrpc_c::value_int(glist[gi].getNumPlayers()));

	}
 
	xmlrpc_c::value_array array1(arrayData);
        *retvalP = array1;
	}
};



class onBase : public xmlrpc_c::method {
public:
    onBase(){
        this->_signature = "A:iidd";
        this->_help = "This method initializes the session and returns it GID";
    }
    void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const  retvalP) {
        
        long int const gid(paramList.getInt(0));
	long int const pid(paramList.getInt(1));
	double const x(paramList.getDouble(2));
	double const y(paramList.getDouble(3));

        paramList.verifyEnd(4);

	session *game = slist.getSession(gid);
	int tmNum = game->getPTeamNum(pid);
	int success = game->conquerBase(tmNum,pid,x,y);

        vector<xmlrpc_c::value> arrayData;
        arrayData.push_back(xmlrpc_c::value_int(success));
        xmlrpc_c::value_array array1(arrayData);
        *retvalP = array1;
	}
};

class check : public xmlrpc_c::method {
public:
    check(){}
    void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const  retvalP) {
	string const str(paramList.getString(0));
	paramList.verifyEnd(1);	
        
	vector<xmlrpc_c::value> arrayData;
        
	arrayData.push_back(xmlrpc_c::value_string(str));
        xmlrpc_c::value_array array1(arrayData);
        *retvalP = array1;
    }
};

int main(int const, const char ** const) {


    try {
	cout<<"YOOOO"<<endl;

        xmlrpc_c::registry myRegistry;

        xmlrpc_c::methodPtr const genID_m(new genID);
        myRegistry.addMethod("server.genID", genID_m);

 
        xmlrpc_c::methodPtr const createGame_m(new createGame);
        myRegistry.addMethod("server.createGame", createGame_m);

        xmlrpc_c::methodPtr const join_m(new join);
        myRegistry.addMethod("server.join", join_m);

        xmlrpc_c::methodPtr const refresh_m(new refresh);
        myRegistry.addMethod("server.refresh", refresh_m);
        
	xmlrpc_c::methodPtr const gameList_m(new gameList);
        myRegistry.addMethod("server.gameList", gameList_m);

        xmlrpc_c::methodPtr const onBase_m(new onBase);
        myRegistry.addMethod("server.onBase", onBase_m);

        xmlrpc_c::methodPtr const check_m(new check);
        myRegistry.addMethod("server.check", check_m);

        xmlrpc_c::serverAbyss myAbyssServer(
            xmlrpc_c::serverAbyss::constrOpt()
            .registryP(&myRegistry)
            .portNumber(8080));
        
        myAbyssServer.run();
        // xmlrpc_c::serverAbyss.run() never returns
        assert(false);
    } catch (exception const& e) {
        cerr << "Something failed.  " << e.what() << endl;
    }
    return 0;
}
