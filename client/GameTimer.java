
public class GameTimer extends Thread{
	public double dur;
	private volatile Thread time;

	public GameTimer(double duration){
		dur = duration;
	}

	public double getDur(){
		return dur;
	}

	public void printDur(){
		System.out.println(dur);
	}

	public void start() {
		time = new Thread(this);
		time.start();
	}

	public void exit(){
		time = null;
	}

	public void run(){
		Thread thisThread = Thread.currentThread();
		long init = System.currentTimeMillis();
		double temp = dur;
		while(time == thisThread){
			if(dur <= 0.0){
				System.out.println("TEST");
				exit();
			}
			if(dur > 0.0){
				dur = ((temp*1000)-(System.currentTimeMillis()-init))/1000;
				if(dur%1 == 0.0)
					printDur();
			}
		}
	}

//	public static void main(String args[]){
//		GameTimer t1 = new GameTimer(5);
//		t1.start();
//	}
}
