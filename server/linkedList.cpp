#include <iostream>

#ifndef linkedList_h
#define linkedList_h
#include "linkedList.h"
#endif

using namespace std;

LL::LL(){
	first=nullptr;
	last=nullptr;
}

LL::~LL(){}

int LL::getSize(){
	return size;
}

struct node * LL::getFirst(){
	return first;
}

struct node * LL::getLast(){
	return last;
}

void LL::addNode(struct node * aN){
	size++;
	if(first!=nullptr){
		last->next = aN;
		aN->prev = last;
		last = aN;
	//	node_t * cur = first;
	//	while(cur->next != nullptr){
	//		cout<<cur->data->getGid();
	//		cur = cur->next;
	//	}
 
	}else{
		first = aN;
		last = aN;
	}
}

void LL::deleteNode(long int gid){
	struct node * cur = first;
	for(int i=0;i<size;i++){
		if ((cur->data)->getGid()==gid){
			if(cur->prev != nullptr){
				(cur->next)->prev = cur->prev;
				(cur->prev)->next = cur->next;
			}else if(cur->next != nullptr){
				first = cur->next;	
			}else{
				first = nullptr;
				last = nullptr;
			}
		size--;
		}
	} 
}

struct node * LL::findNode(long int gid){
	struct node * cur = first;
	for(int i=0;i<size;i++){
		if ((cur->data)->getGid()==gid){
			return cur;
		}
		if(i!=size-1){	
			cur = cur->next;
		}
	}
	return nullptr;
}

