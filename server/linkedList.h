#ifndef session_class_h
#define session_class_h
#include "session-class.h"
#endif

struct node{
	session data;
	struct node *prev;
	struct node *next;
}node_t;



class LL{
public:
	LL(void);
	~LL(void);
	int getSize();
	struct node * getFirst();
	struct node * getLast();
	void addNode(struct node * aN);
	void deleteNode(long int gid);
	struct node * findNode(long int gid);
private:
	int size;
	struct node *first;
	struct node *last;
};
