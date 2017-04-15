package com.mycompany.castapp;

import com.mycompany.castapp.CastApp.CastOnHelper;
import com.mycompany.castapp.CastApp.CastOn;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import org.omg.CORBA.*;
import org.omg.CosNaming.* ;


public class CastNamingClient
{
    public static NamingContextExt rootCtx;

	private static class SampleListener implements ServiceListener {
		@Override
		public void serviceAdded(ServiceEvent event) {
			System.out.println("Service added: " + event.getInfo());
		}

		@Override
		public void serviceRemoved(ServiceEvent event) {
			System.out.println("Service removed: " + event.getInfo());
		}

		@Override
		public void serviceResolved(ServiceEvent event) {
                    ServiceInfo info = event.getInfo();
                    int port = info.getPort();
                    String path = info.getNiceTextString().split("=")[1];
                    GetRequest.request("http://localhost:"+port+"/"+path);
		}
	}
    public static void list(NamingContext n, String indent) {
        try {
                final int batchSize = 1;
                BindingListHolder bList = new BindingListHolder() ;
                BindingIteratorHolder bIterator = new BindingIteratorHolder();
                n.list(batchSize, bList, bIterator) ;
                if (bList.value.length != (int) 0)
                    for (int i = 0; i < bList.value.length; i++) {
                        BindingHolder bh = new BindingHolder(bList.value[0]);
                        do {
                            String stringName = rootCtx.to_string(bh.value.binding_name);
                            System.out.println(indent + stringName) ;
                            if (bh.value.binding_type.value() == BindingType._ncontext) {
                                String _indent = "----" + indent;

                                NameComponent [] name = rootCtx.to_name(stringName);
                                NamingContext sub_context = NamingContextHelper.narrow(n.resolve(name));
                                list(sub_context, _indent);
                            }
                        } while (bIterator.value.next_one(bh));
                    }
                else
                    System.out.println(indent + "Nothing more in this context.") ;
        }
        catch (Exception e) {
            System.out.println("An exception occurred. " + e) ;
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws InterruptedException
    {
        
              	try {
		// Create a JmDNS instance
	       JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

		// Add a service listener
	       jmdns.addServiceListener("_http._tcp.local.", new SampleListener());

		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	try{
            NameComponent nc[]= new NameComponent[2];
			
	        //Create ORB
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialPort", "900");
	        ORB orb = ORB.init(args, props);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            rootCtx = NamingContextExtHelper.narrow(objRef);

            //list
            list(rootCtx, "---->");
            System.out.println("Printing Done");

            //Search the Name Space for Number 4 Object
            nc[0] = new NameComponent("Context1", "Context");
            nc[1] = new NameComponent("Object1", "Object");

            //need to add in more like CastOn for the other functions 
            org.omg.CORBA.Object objCast = rootCtx.resolve(nc);
            CastOn CastRef = CastOnHelper.narrow(objCast);

            System.out.println("\nRequest recieved turning on cast device");
            String result = CastRef.CastOn();
            System.out.println(result);

            } catch (Exception e) {
                System.out.println("ERROR : " + e) ;
                e.printStackTrace(System.out);
            }
	}
}
