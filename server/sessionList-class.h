#ifndef session_class_h
#define session_class_h
#include "session-class.h"
#endif

class sessionList{
	public:
		sessionList();
		~sessionList();
		long int addSession(long int pid);
		session* getSession(long int gid);
		void deleteSession(long int gid);
		void restartSession(long int gid);
		session* getList();
		int getNumSession();
		void refreshPlayerCount();
		int getPlayerCount();
	private:
		session* sList;
		int index;
		long int gidInd;
		int numSession;
		int playerCount;
};
