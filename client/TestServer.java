import org.apache.xmlrpc.*;
import java.util.*;
public class TestServer{

	public Vector check(String check){
		System.out.println("CHECK REQUEST RECEIVED");
		Vector ret = new Vector();
		if(check.equals("CHECKCON"))
			ret.addElement(check);
		else
			ret.addElement("FAILED");
		return ret;
	}

	public Vector genGID(int pid){
		Vector GID = new Vector();
		GID.addElement(new Integer(99999999)); //would generate and store GID on server
		System.out.println("CALLING GENID");
		return GID;
	}

	public Vector createGame(int gid, int playCount, double radius, int baseCount, double startLat, double startLong){
		Vector created = new Vector();
		created.addElement(new Integer(1)); //would call actual create session
		System.out.println("CALLING CREATE GAME");
		return created;
	}
	
	public Vector testSend(int pc, int bc, double dur){
		Vector send = new Vector();
		send.addElement(new Integer(pc));
		send.addElement(new Integer(bc));
		send.addElement(new Double(dur));
		System.out.println("CREATING GAME...");
		System.out.println("PLAYER COUNT = "+pc);
		System.out.println("BASE COUNT = "+bc);
		System.out.println("DURATION = "+dur);
		System.out.println();
		return send;	
	}

	public Vector join(int gid, int pid){
		Vector game = new Vector();
		game.addElement(new Integer(2)); //Current Player Count
		game.addElement(new Integer(10000000)); //Player 10000000
		game.addElement(new Integer(0)); //Assigned team id for 10000000
		game.addElement(new Integer(pid)); //Your id
		game.addElement(new Integer(1)); //Your team id
		game.addElement(new Integer(2));
		game.addElement(new Double(1.1)); //base Latitude
		game.addElement(new Double(2.1)); //base Longitude
		game.addElement(new Double(.01)); //base radius
		game.addElement(new Double(0.9)); //base Latitude
		game.addElement(new Double(1.9)); //base Longitude
		game.addElement(new Double(.01)); //base radius
		game.addElement(new Integer(10)); //Player count
		game.addElement(new Double(1.0)); //Game Latitude
		game.addElement(new Double(2.0)); //Game Longitude
		game.addElement(new Double(2.0)); //Game radius
		System.out.println("CALLING JOIN GAME");
		return game;
	}

	public Vector refresh(int gid, int pid){
		Vector ref = new Vector();
		ref.addElement(new Integer(2));
		ref.addElement(new Integer(10000000));
		ref.addElement(new Integer(0));
		ref.addElement(new Integer(pid));
		ref.addElement(new Integer(1));
		ref.addElement(new Integer(2));
		ref.addElement(new Double(1.1)); //base Latitude
		ref.addElement(new Double(2.1)); //base Longitude
		ref.addElement(new Double(.01)); //base radius
		ref.addElement(new Double(0.9)); //base Latitude
		ref.addElement(new Double(1.9)); //base Longitude
		ref.addElement(new Double(.01)); //base radius
		System.out.println("CALLING REFRESH");
		return ref;
	}

	public Vector gameList(int pid){
		Vector games = new Vector();
		games.addElement(new Integer(2));
		games.addElement(new Integer(11111111));
		games.addElement(new Integer(22222222));
		games.addElement(new Integer(10));
		games.addElement(new Integer(20));
		games.addElement(new Integer(5));
		games.addElement(new Integer(17));
		System.out.println("CALLING GAMELIST");
		return games;
	}

	public Vector onBase(int gid, int pid, double lat, double lon){
		Vector tf = new Vector();
		tf.addElement(new Integer(1)); //Would check if on base
		System.out.println("CALLING ONBASE");
		return tf;
	}
	
	public static void main(String args[]){
		try {
			WebServer server = new WebServer(3389);
        		server.addHandler("server", new TestServer());
			server.start();
		} catch(Exception e){
			System.err.println(e);
		}
	}
}
