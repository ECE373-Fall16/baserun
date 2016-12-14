import java.util.*;
import org.apache.xmlrpc.*;


public class ServerTest{

	public static void main(String args[]){

			System.out.println("heeeeere");			
		try {

			System.out.println("heeeeere");			
	
			XmlRpcClient server = new XmlRpcClient("http://104.196.195.139:3389/RPC2");

			Vector p1 = new Vector();
			p1.addElement(new Integer(5));
			Vector genID = (Vector)server.execute("server.genID",p1);	
			int gid = (Integer)genID.get(0);
			System.out.println(gid);		

			Vector p2 = new Vector();
			p2.addElement(new String("hi"));
			Vector chk =(Vector)server.execute("server.check",p2);
			String a = (String)chk.get(0);
			System.out.println(a);
/*

			Vector params = new Vector();
			params.addElement(gid);
			params.addElement(new Integer(6));
			Vector join = (Vector)server.execute("server.join",params);
			System.out.println((Integer)join.get(0));		

			Vector p2 = new Vector();
			p2.addElement(new Integer(7));
			Vector genID = (Vector)server.execute("server.genID",p1);
			System.out.println((Integer)genID.get(0));		


	/*		if(joinGame(11111111,99999999))
				System.out.println("JOIN GAME PASSED");
			else
				System.out.println("JOIN GAME FAILED");
			if(createGame(99999999,10,100.0, 10, 200.0, 900.0))
				System.out.println("CREATE GAME PASSED");
			else
				System.out.println("CREATE GAME FAILED");*/
		} catch(Exception e){
			e.printStackTrace();
		}
	}

/*
	public static boolean joinGame(int GID, int PID){
		Vector params = new Vector();
		params.addElement(new Integer(GID));
		params.addElement(new Integer(GID));
		Vector join = (Vector)server.execute("server.join",params);
		if((Integer)join.get(0) > 0)
			return true;
		else
			return false;
	}

	public static boolean createGame(int PID, int playerCount, double radius, int baseCount, double startLat, double startLong){
		Vector params = new Vector();
		params.addElement(new Integer(PID));
		params.addElement(new Integer(playerCount));
		params.addElement(new Double(radius));
		params.addElement(new Integer(baseCount));
		params.addElement(new Double(startLat));
		params.addElement(new Double(startLong));
		Vector create = (Vector)server.execute("server.createGame",params);
		if((Integer)create.get(0) == 1)
			return true;
		else
			return false;
	}
*/

}
