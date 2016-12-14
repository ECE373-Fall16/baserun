#include <iostream>

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

using namespace std;

//constructor for session list
sessionList::sessionList(){
	gidInd=100;
	sList=new LL(); 
}

//destructor
sessionList::~sessionList(){}

//adds a session to the list of all played sessions
long int sessionList::addSession(long int pid, int maxS){

	node_t *aN = new node_t;
	aN->next = nullptr;
	aN->prev = nullptr;
	aN->data = new session(maxS);
	aN->data->setGid(gidInd);
	aN->data->init_Player(pid);
	sList->addNode(aN);	
	return gidInd++;
}

//returns a session ptr corresponding to a GID
session* sessionList::getSession(long int gid){
	struct node *cur = sList->findNode(gid);
	if(cur != nullptr) return cur->data;
	return nullptr;
}

//deletes a session from list of sessions
void sessionList::deleteSession(long int gid){
	sList->deleteNode(gid);
}

//restarts a session --yets to be implemented
void sessionList::restartSession(long int gid){}

//returns the first node which essentially gives list of games
node_t* sessionList::getList(){
	return sList->getFirst();
}

//return the number of active games
int sessionList::getNumSession(){
	return sList->getSize();
}
