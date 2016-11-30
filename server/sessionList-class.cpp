#ifndef linkedList_h
#define linkedList_h
#include "linkedList.h"
#endif

#ifndef session_class_h
#define session_class_h
#include "session-class.h"
#endif


#ifndef sessionList_class_h
#define sessionList_class_h
#include "sessionList-class.h"
#endif

sessionList::sessionList(){
	playerCount =0;
	numSession=0;
	gidInd=1;
	index=0;
	sList=new session[10]; 
}

sessionList::~sessionList(){}

long int sessionList::addSession(long int pid){
	playerCount =0;
	numSession++;
	sList[index++].setGid(gidInd++);
	sList[index].init_Player(pid);
	return gidInd;
}

session* sessionList::getSession(long int gid){
	for(int i=0;i<index;i++){
		if(sList[i].getGid()==gid){
			return &sList[i];
		}
	}
	//return nullptr
	return &sList[0];
}

void sessionList::deleteSession(long int gid){}

void sessionList::restartSession(long int gid){}

session* sessionList::getList(){
	return sList;
}

int sessionList::getNumSession(){
	return numSession;
}

void sessionList::refreshPlayerCount(){
	playerCount=0;
	for(int i=0;i<gidInd;i++){
		if(sList[i].getGid()!=0){
			playerCount += sList[i].getNumPlayers();	
		}
	}
} 

int sessionList::getPlayerCount(){
	return playerCount;
}
