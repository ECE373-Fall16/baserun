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
	gidInd=0;
	sList=new LL(); 
}

sessionList::~sessionList(){}

long int sessionList::addSession(long int pid){
	node_t aN;
	aN.next = nullptr;
	aN.prev = nullptr;
	aN.data = new session;
	aN.data->setGid(gidInd);
	aN.data->init_Player(pid);
	sList->addNode(&aN);	
	return gidInd++;
}

session* sessionList::getSession(long int gid){
	return (sList->findNode(gid))->data;
}

void sessionList::deleteSession(long int gid){
	sList->deleteNode(gid);
}

void sessionList::restartSession(long int gid){}

node_t* sessionList::getList(){
	return sList->getFirst();
}

int sessionList::getNumSession(){
	return sList->getSize();
}
