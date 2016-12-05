
public class GameTimer extends Thread{
	double dur;
	private volatile Thread thread;

	public GameTimer(double duration){
		dur = duration;
	}

	public double getDur(){
		return dur;
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	public void exit(){
		thread = null;
	}

	public void run(){
		Thread thisThread = Thread.currentThread();
		long init = System.currentTimeMillis();
		double temp = dur;
		while(thread == thisThread){
			if(((temp*1000)-(System.currentTimeMillis()-init)) > 0.0){
				dur = ((temp*1000)-(System.currentTimeMillis()-init))/1000;
			} else {
				exit();
			}
		}
	}

//	public static void main(String args[]){
//		GameTimer t1 = new GameTimer(5);
//		t1.start();
//	}
}
