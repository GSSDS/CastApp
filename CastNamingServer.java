package com.mycompany.castapp;

import org.omg.CORBA.*;
import org.omg.CosNaming.* ;
import java.util.Properties;

public class CastNamingServer{

	public static void main (String args[]) {
		try
		{
			
			NameComponent nc[] = new NameComponent[1];

	    	//Create Orb and initialise
	    	Properties props = new Properties();
        	props.put("org.omg.CORBA.ORBInitialPort", "1050");
        	ORB orb = ORB.init(args, props);

			CastServant CastServ = new CastServant();

			//attach servant to orb
			orb.connect(CastServ);
	   		System.out.println("Orb connected." + orb);

			//find NameService
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
	   		System.out.println("Found NameService.");

			//convert to naming context
			NamingContext rootCtx = NamingContextHelper.narrow(objRef);

			//Context1
			//add context1 to root 
			nc[0] = new NameComponent("Context1", "Context");
			NamingContext Context1 = rootCtx.bind_new_context(nc);
			System.out.println("Context 'Context1' added to Name Space.");
			
                        //bind object 2 to context 2
			nc[0] = new NameComponent("Object1", "Object");
			Context1.rebind(nc, CastServ);
			System.out.println("Object 'Object1' added to Context 1.");
		
			//run the orb
			orb.run();

			//error handling 
		} catch (Exception e) {
			System.err.println("Error: "+e);
			e.printStackTrace(System.out);
		}

	}
}
