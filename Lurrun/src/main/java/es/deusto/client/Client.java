package es.deusto.client;


import es.deusto.server.remote.*;;

public class Client {

	public static void main(String[] args) {
		
		if (args.length != 3) {
			System.out.println("Use: java [policy] [codebase] Client.Client [host] [port] [server]");
			System.exit(0);
		}
		System.out.println("[C] Client here =)");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			String name = "//" + args[0] + ":" + args[1] + "/" + args[2];
			IRemote objHello = (IRemote) java.rmi.Naming.lookup(name);
			// Register to be allowed to send messages
			//objHello.registerUser("dipina", "dipina");
			//System.out.println("* Message coming from the server: '" + objHello.sayMessage("dipina", "dipina", "This is a test!") + "'");
			System.out.println("[C] Sending salutations");
			System.out.println(objHello.sayHello());
			
		} catch (Exception e) {
			System.err.println("RMI Example exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}