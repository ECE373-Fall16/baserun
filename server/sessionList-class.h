#ifndef session_class_h
#define session_class_h
#include "session-class.h"
#endif

class sessionList{
	public:
		void addSession(long int gid);
		session* getSession(long int gid);
		void deleteSession(long int gid);
		void restartSession(long int gid);
	private:
		session* sList;	
};
