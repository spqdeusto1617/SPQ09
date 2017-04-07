package lurrun.clientside;
import java.util.ArrayList;

public class ClientSide 
{
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Use: java [policy] [codebase] Client.Client [host] [port] [server]");
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			String name = "//" + args[0] + ":" + args[1] + "/" + args[2];
			String user = "Gabe";
			String pass = "Newell";
//			ILurrunServer objHello = (ILurrunServer) java.rmi.Naming.lookup(name);
//			objHello.registerUser("user", "pass");
//			System.out.println("* Message from the Lurrun server: '" + objHello.sayMessage("user", "pass", "User logged") + "'");
			
		} catch (Exception e) {
			System.err.println("RMI exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
