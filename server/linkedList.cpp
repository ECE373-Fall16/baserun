#ifndef linkedList_h
#define linkedList_h
#include "linkedList.h"
#endif

LL::LL(){}

LL::~LL(){}

int LL::getSize(){
	return size;
}

struct node * getFirst(){
	return first;
}

struct node * getLast(){
	return last;
}

void addNode(long int gid){}

void deleteNode(long int gid){}

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
	void addNode(long int gid);
	void deleteNode(long int gid);
private:
	int size;
	struct node *first;
	struct node *last;
};
