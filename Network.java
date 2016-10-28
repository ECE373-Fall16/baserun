import java.net.*;
import java.io.*;

public class Network{
	LocalSocket send = new LocalSocket();
	LocalSocket recieve = new LocalSocket();
	LocalServerSocket server;
	Scanner scan;
	ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	public Network(){
		
	}
	
	public boolean connect(String server, int port){
		try{
			server = new LocalServerSocket(/*ADDRESS*/);
			receive = server.accept();
			send.connect(new LocalSocketAddress(/*SOCKET ADDRESS*/));
		} catch(UnknownHostException e){
			return false;
		} catch(IOException e){
			e.printStackTrace();
		}
		if(checkConn())
			return true;
		else
			return false;
	}
	
	private boolean checkConn(){
		NetworkInfo netInf = connMgr.getActiveNetworkInfo();
		if(client != null){
			if(netInf != null & netInf.isConnected())
				return true;
			else
				return false;
		} else
			return false;
	}

	public String joinGame(int GID, int PID){
		if(checkConn()){
			//CALL SERVER, RETURN GAME VALUE
			String buff = GID+"|"+PID+"|0";
			send.getOutputStream().write(buff.getBytes());
			send.getOutputStream().close();
			if(receive != null){
				InputStream in = receive.getInputStream();
			}
		} else 
			return "CONNFAIL";
	}

	public String refreshGame(int GID, int PID){
		if(checkConn()){
			//CALL SERVER, RETURN GAME UPDATE
			String buff = GID+"|"+PID+"|1";
			send.getOutputStream().write(buff.getBytes());
			send.getOutputStream().close();
			if(receive != null){
				InputStream in = receive.getInputStream();
			}
		} else
			return "CONNFAIL";
	}

	public String gameList(int PID){
		if(checkConn()){
			//CALL SERVER, RETURN GAME LIST
			String buff = "0|"+PID;
			send.getOutputStream().write(buff.getBytes());
			send.getOutputStream().close();
			if(receive != null){
				InputStream in = receive.getInputStream();
			}
			
		} else
			return "CONNFAIL";
	}

	public void disconnect(){
		send.close();
		recieve.close();
		server.close();
	}
}
