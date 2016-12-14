//header w/o gaurd
#include <cassert>
#include <stdexcept>
#include <iostream>
#include <unistd.h>
#include <string>
#include <iomanip>

//header gaurds
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

#ifndef linkedList_h
#define linkedList_h
#include "linkedList.h"
#endif

using namespace std;

sessionList slist;
long int genPid;

class getPID : public xmlrpc_c::method {
public:
    getPID(){
        this->_signature = "A:";
        this->_help = "This method returns a unique pid";
    }
    void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const  retvalP) {

        paramList.verifyEnd(0);

        vector<xmlrpc_c::value> arrayData;
        arrayData.push_back(xmlrpc_c::value_int(genPid++));
        xmlrpc_c::value_array array1(arrayData);
        *retvalP = array1;
	}
};


//initializes a blank game and returns the GID
/*class genGID : public xmlrpc_c::method {
public:
    genGID(){
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
};*/

//intiializes the parameter data of the game
//gives GID of game to initialize
class createGame : public xmlrpc_c::method {
public:
    createGame(){
        this->_signature = "A:iidididdi";
        this->_help = "This method initializes the session and returns it GID";
    }
    void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const  retvalP) {
       
	cout<<"FINNNALLLYYYY"<<endl; 
	int success;
	long int gid;
        long int const pid(paramList.getInt(0));
	int const maxGameSize(paramList.getInt(1));
	double const radius(paramList.getDouble(2));
	int const numBases(paramList.getInt(3));
	double const startX(paramList.getDouble(4));
	double const startY(paramList.getDouble(5));

        paramList.verifyEnd(6);
	
	cout<<"CREATE GAME___"<<endl;

	gid = slist.addSession((long int)pid,maxGameSize);
 		

	session *game = slist.getSession(gid);
	if (game!=nullptr){

	game->setRadius(radius);
	game->setNumBases(numBases);
	game->setStart(startX,startY);
	game->generateLocationArray();	

	//returns gid for success	
	success = (int)gid;

	}else{
	success=-1;
	}


        vector<xmlrpc_c::value> arrayData;
        arrayData.push_back(xmlrpc_c::value_int(success));
        xmlrpc_c::value_array array1(arrayData);
        *retvalP = array1;
	}
};

//has a player join a game with their input pid and and gid of intended game
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
	
	cout<<"PLAYER "<<pid<<" JOINED"<<endl;

	session *game = slist.getSession(gid);
	game->init_Player(pid);
	int tmNum = game->getPTeamNum(pid);
	
	team* tm = game->getTeam(tmNum);
	team* otherTm = game->getTeam((tmNum+1)%2);

        vector<xmlrpc_c::value> arrayData;

        arrayData.push_back(xmlrpc_c::value_int(game->getNumPlayers()));

	player_t* pArr = tm->getPlayerArr();
	player_t* pArr2 = otherTm->getPlayerArr();
	int numP = tm->getNumPlayers();	
	int numP2 = otherTm->getNumPlayers();

	

	for(int i=0; i<numP; i++){
        	arrayData.push_back(xmlrpc_c::value_int(pArr[i].id));
		cout<<"tmNum: "<<tm->getTeamNum()<<endl;
        	arrayData.push_back(xmlrpc_c::value_int(tm->getTeamNum()));
	}
	for(int i=0; i<numP2; i++){
        	arrayData.push_back(xmlrpc_c::value_int(pArr2[i].id));
        	arrayData.push_back(xmlrpc_c::value_int(otherTm->getTeamNum()));
	}

	int baseSize = game->getNumBases();
	
	cout<<"base size: "<<baseSize<<endl;
        arrayData.push_back(xmlrpc_c::value_int(baseSize));
	base_t* bases = game->getBases();

	for(int i=0; i<baseSize; i++){
        	arrayData.push_back(xmlrpc_c::value_double(bases[i].x));
        	arrayData.push_back(xmlrpc_c::value_double(bases[i].y));
        	arrayData.push_back(xmlrpc_c::value_double(game->getBaseRadius()));
	}

	//returns the game info includng:
	//returns maxPlayerSize
	//returns startX position
	//returns startY position
	//returns game radius
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
        this->_help = "This method refreshes the game data";
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
		//returns player ID's
		//returns team numbers
        	arrayData.push_back(xmlrpc_c::value_int(pArr[i].id));
        	arrayData.push_back(xmlrpc_c::value_int(tm->getTeamNum()));
	}
	for(int i=0; i<numP2; i++){
		//returns player ID's
		//returns team numbers
        	arrayData.push_back(xmlrpc_c::value_int(pArr2[i].id));
        	arrayData.push_back(xmlrpc_c::value_int(otherTm->getTeamNum()));
	}

	int baseSize = game->getNumBases();

        arrayData.push_back(xmlrpc_c::value_int(baseSize));
	base_t* bases = game->getBases();

	for(int i=0; i<baseSize; i++){
		//returns base info
        	arrayData.push_back(xmlrpc_c::value_double(bases[i].x));
        	arrayData.push_back(xmlrpc_c::value_double(bases[i].y));
        	arrayData.push_back(xmlrpc_c::value_double(game->getBaseRadius()));
		if (bases[i].color == 'r')
	        arrayData.push_back(xmlrpc_c::value_int(1));
		else if(bases[i].color == 'b')
	        arrayData.push_back(xmlrpc_c::value_int(2));
		else
	        arrayData.push_back(xmlrpc_c::value_int(0));

	}

 
	xmlrpc_c::value_array array1(arrayData);
        *retvalP = array1;
	}
};

//returns gameList
class gameList : public xmlrpc_c::method {
public:
    gameList(){
        this->_signature = "A:i";
        this->_help = "method returns all games";
    }
    void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const  retvalP) {
        
        long int const pid(paramList.getInt(0));
	
        paramList.verifyEnd(1);

        vector<xmlrpc_c::value> arrayData;

	node_t* glist = slist.getList();
	
	int numS = slist.getNumSession();
	
	//return number of games first
	cout<<numS<<endl;
	arrayData.push_back(xmlrpc_c::value_int(numS));

	for(int gi=0; gi<numS; gi++){
		//return game data
		//return GID
		//max player size of that game
		//return current players in game
		arrayData.push_back(xmlrpc_c::value_int((glist->data)->getGid()));
		arrayData.push_back(xmlrpc_c::value_int((glist->data)->getMaxPlayerSize()));
	        arrayData.push_back(xmlrpc_c::value_int((glist->data)->getNumPlayers()));
		glist = glist->next;

	}
 
	cout<<"left method gameList"<<endl;
	xmlrpc_c::value_array array1(arrayData);
        *retvalP = array1;
	}

};


//called when on a player is on a base
class onBase : public xmlrpc_c::method {
public:
    onBase(){
        this->_signature = "A:iidd";
        this->_help = "checks if base gets conquered and changes scores accordingly";
    }
    void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const  retvalP) {
        
        long int const gid(paramList.getInt(0));
	long int const pid(paramList.getInt(1));
	double const x(paramList.getDouble(2));
	double const y(paramList.getDouble(3));
	cout<<"__________________________________________"<<endl;
	cout<<gid<<" "<<pid<<" "<<x<<" "<<y<<endl;

        paramList.verifyEnd(4);

	session *game = slist.getSession(gid);
	int tmNum = game->getPTeamNum(pid);
	int success = game->conquerBase(tmNum,pid,x,y);

	//returns if base is conquered or not
        vector<xmlrpc_c::value> arrayData;
        arrayData.push_back(xmlrpc_c::value_int(success));
        xmlrpc_c::value_array array1(arrayData);
        *retvalP = array1;
	}
};

//all further fctns are for testing

//checks input by taking and returning a string
class check : public xmlrpc_c::method {
public:
    check(){}
    void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const  retvalP) {
	string const str(paramList.getString(0));
	cout<<str<<endl;

	paramList.verifyEnd(1);	
        
	vector<xmlrpc_c::value> arrayData;
        
	arrayData.push_back(xmlrpc_c::value_string(str));
        xmlrpc_c::value_array array1(arrayData);
        *retvalP = array1;
    }
};

//looks at the different inputs and prints them to screen
class testSend : public xmlrpc_c::method {
public:
    testSend(){
    }
    void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const  retvalP) {
        
        int const i1(paramList.getInt(0));
        int const i2(paramList.getInt(1));
	double const d1(paramList.getDouble(2));
        paramList.verifyEnd(3);

	cout<<"CREATING GAME"<<endl;
	cout<<"   PLAYER COUNT: "<<i1<<endl;
	cout<<"   BASE COUNT: "<<i2<<endl;
	cout<<"   DURATION: "<<d1<<endl;
	cout<<endl;	

        vector<xmlrpc_c::value> arrayData;
        arrayData.push_back(xmlrpc_c::value_int(i1));
        arrayData.push_back(xmlrpc_c::value_int(i2));
        arrayData.push_back(xmlrpc_c::value_double(d1));
        xmlrpc_c::value_array array1(arrayData);
        *retvalP = array1;
	}
};


//sets time for the game
class setTime : public xmlrpc_c::method {
public:
    setTime(){
    }
    void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const  retvalP) {
        
        int const gid(paramList.getInt(0));
	string const stTime(paramList.getString(1));
        string const enTime(paramList.getString(2));
	cout<<stTime<<" <--times--> "<<enTime<<endl;	
        paramList.verifyEnd(3);
       
	cout<<"SET TIME: "<<stTime<<" <--times--> "<<enTime<<endl;	

	paramList.verifyEnd(3);
	
	session *game = slist.getSession(gid);
	game->setTime(stTime, enTime);

        vector<xmlrpc_c::value> arrayData;
        xmlrpc_c::value_array array1(arrayData);
        *retvalP = array1;
	}
};

//gets time for the game
class getTime : public xmlrpc_c::method {
public:
    getTime(){
    }
    void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const  retvalP) {
        
        int const gid(paramList.getInt(0));
        paramList.verifyEnd(1);
	
	session *game = slist.getSession(gid);
	string stTime = game->getStartTime();
	string enTime = game->getEndTime();

	cout<<"GET TIME: "<<stTime<<" <--times--> "<<enTime<<endl;	
        vector<xmlrpc_c::value> arrayData;
        arrayData.push_back(xmlrpc_c::value_string(stTime));
        arrayData.push_back(xmlrpc_c::value_string(enTime));
        xmlrpc_c::value_array array1(arrayData);
        *retvalP = array1;

	}
};

//returns team scores
class getScore : public xmlrpc_c::method {
public:
    getScore(){
    }
    void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const  retvalP) {
        
        int const gid(paramList.getInt(0));
        paramList.verifyEnd(1);
	
	session *game = slist.getSession(gid);
	team *tm1 = game->getTeam(1);
	team *tm2 = game->getTeam(2);	

	cout<<tm1->getTeamScore()<<" <--scores--> "<<tm2->getTeamScore()<<endl;	
        vector<xmlrpc_c::value> arrayData;
        arrayData.push_back(xmlrpc_c::value_int(tm1->getTeamScore()));
        arrayData.push_back(xmlrpc_c::value_int(tm2->getTeamScore()));
        xmlrpc_c::value_array array1(arrayData);
        *retvalP = array1;

	}
};

int main(int const, const char ** const) {


    try {
	cout<<"SERVER INTIALIZE"<<endl;

        xmlrpc_c::registry myRegistry;

        //xmlrpc_c::methodPtr const genID_m(new genID);
        //myRegistry.addMethod("server.genID", genID_m);

        xmlrpc_c::methodPtr const getPID_m(new getPID);
        myRegistry.addMethod("server.getPID", getPID_m);
 
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

        xmlrpc_c::methodPtr const testSend_m(new testSend);
        myRegistry.addMethod("server.testSend", testSend_m);

        xmlrpc_c::methodPtr const getTime_m(new getTime);
        myRegistry.addMethod("server.getTime", getTime_m);

        xmlrpc_c::methodPtr const setTime_m(new setTime);
        myRegistry.addMethod("server.setTime", setTime_m);

        xmlrpc_c::methodPtr const getScore_m(new getScore);
        myRegistry.addMethod("server.getScore", getScore_m);
       
	 xmlrpc_c::serverAbyss myAbyssServer(
            xmlrpc_c::serverAbyss::constrOpt()
            .registryP(&myRegistry)
            .portNumber(3389));
        
        myAbyssServer.run();
        // xmlrpc_c::serverAbyss.run() never returns
        assert(false);
    } catch (exception const& e) {
        cerr << "Something failed.  " << e.what() << endl;
    }
    return 0;
}
