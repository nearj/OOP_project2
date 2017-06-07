class  Queue{
public:
  Queue(void);
  bool IsEmpty();
  bool IsFull();
  void EnQueue(int);
  int DeQueue();
  ~Queue(void);

private:
  int arr[10];
  int size;
  int last;
  int first;
};

Queue::Queue(void){
  size = 10;
  last = 0;
  first = 0;
}

bool Queue::IsEmpty(){
  if( (last) % size == first )
	return true;
  else return false;
}

bool Queue::IsFull(){

}

void Queue::EnQueue( int data ){
  if( Queue:: isFull() ) {
	arr[last] = data;
	last = ( last + 1 ) % size;
  }
}

int Queue::DeQueue(){
  if( Queue::isEmpty() ) {
	return arr[first++];
	first = ( first + 1 ) % size;
  }
}

Queue::~Queue(void){
 
}