#ifndef session_class_h
#define session_class_h
#include "session-class.h"
#endif

#ifndef linkedList_h
#define linkedList_h
#include "linkedList.h"
#endif

class sessionList{
	public:
		sessionList();
		~sessionList();
		long int addSession(long int pid);
		session* getSession(long int gid);
		void deleteSession(long int gid);
		void restartSession(long int gid);
		node_t* getList();
		int getNumSession();
	private:
		LL* sList;
		long int gidInd;
};
