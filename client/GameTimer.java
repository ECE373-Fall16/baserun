
public class GameTimer extends Thread{
	double dur;
	private volatile Thread time;

	public GameTimer(double duration){
		dur = duration;
	}

	public double getDur(){
		return dur;
	}

	public void start() {
		time = new Thread(this);
		time.start();
	}

	public void exit(){
		thread = null;
	}

	public void run(){
		Thread thisThread = Thread.currentThread();
		long init = System.currentTimeMillis();
		double temp = dur;
		while(time == thisThread){
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
