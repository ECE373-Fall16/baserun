#ifndef session_class_h
#define session_class_h
#include "session-class.h"
#endif

struct node{
	session data;
	struct node *prev;
	struct node *next;
}node_t;

struct LL{
	struct node *first;
	struct node *last;
};
