//header gaurds

#ifndef session_class_h
#define session_class_h
#include "session-class.h"
#endif

#ifndef linkedList_h
#define linkedList_h
#include "linkedList.h"
#endif

//sessionList class header
class sessionList{
	//all public fctns that can be called by the server
	public:
		sessionList();
		~sessionList();
		long int addSession(long int pid, int maxS);
		session* getSession(long int gid);
		void deleteSession(long int gid);
		void restartSession(long int gid);
		node_t* getList();
		int getNumSession();
	//private variables can only be held by class
	private:
		LL* sList;
		long int gidInd;
};
