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

	cout<<"ADDING A SECOND NODE__________________"<<endl;
		last->next = aN;
		aN->prev = last;
		last = aN;  
	}else{
		first = aN;
		last = aN;
	}
cout<<"leaving addnode"<<endl;
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
	cout<<"size is "<<size<<" and gid "<<gid<<endl;
	for(int i=0;i<size;i++){
	cout<<"i is "<<i<<endl;
	cout<<"checking against gid.. "<<cur->data->getGid()<<endl;
		if ((cur->data)->getGid()==gid){
			cout<<"exit find"<<endl;
			return cur;
		}
		if(i!=size-1){	
			cur = cur->next;
		}
	}
	cout<<"exit find null"<<endl;
	return nullptr;
}

