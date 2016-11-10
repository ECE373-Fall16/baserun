import java.util.*;
import org.apache.xmlrpc.*;


public class ServerTest{

	public static void main(String args[]){

		try {
	
			XmlRpcClient server = new XmlRpcClient("http://127.0.0.1:8080/RPC2");

			Vector params = new Vector();
			params.addElement(new Integer(111111));
			params.addElement(new Integer(999999));
			Vector join = (Vector)server.execute("server.join",params);
			System.out.println((Integer)join.get(0));		
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
