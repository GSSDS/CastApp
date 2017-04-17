package com.mycompany.castapp;

import CastRequest.Cast;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import java.util.Properties;

//Code Based on Tutorials supplied on moodle https://moodle.ncirl.ie/course/view.php?id=806
public class CastNamingServer {

    public static void main(String args[]) {
        Gson gson = new Gson();
        //create contexts and then if Java Object attribute matches value add objects
        try {

            //getting resource from static context
            File file = new File(CastNamingServer.class.getResource("/cast.json").toURI());
            Reader reader = new FileReader(file);

            // Convert JSON to Java Object
            Cast cast = gson.fromJson(reader, Cast.class);
            NameComponent nc[] = new NameComponent[1];

            //Create Orb and initialise
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialPort", "900");
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


            //add CastDevice to root 
            nc[0] = new NameComponent("CastDevice", "Context");
            NamingContext Context1 = rootCtx.bind_new_context(nc);
            System.out.println("Context 'CastDevice' added to Name Space.");
            //add TV to root 
            nc[0] = new NameComponent("TV", "Context");
            NamingContext Context2 = rootCtx.bind_new_context(nc);
            System.out.println("Context 'TV' added to Name Space.");
            //add Soundbar to root 
            nc[0] = new NameComponent("Soundbar", "Context");
            NamingContext Context3 = rootCtx.bind_new_context(nc);
            System.out.println("Context 'Soundbar' added to Name Space.");
            //add Lights to root 
            nc[0] = new NameComponent("Lights", "Context");
            NamingContext Context4 = rootCtx.bind_new_context(nc);
            System.out.println("Context 'Lights' added to Name Space.");

            if ("On".equals(cast.Cast)) {
                //bind OnState to CastDevice
                nc[0] = new NameComponent("OnState", "Object");
                Context1.rebind(nc, CastServ);
                System.out.println("Object 'OnState' added to CastDevice.");
            }
            // Turn TV on and Change input
            if ("On".equals(cast.Tv)) {
                //bind OnState to CastDevice
                nc[0] = new NameComponent("OnState", "Object");
                Context2.rebind(nc, CastServ);
                System.out.println("Object 'OnState' added to TV.");

                nc[0] = new NameComponent("ChangeInput", "Object");
                Context2.rebind(nc, CastServ);
                System.out.println("Object 'ChangeInput' added to TV.");
            }

            //Turn Soundbar on and change input 
            if ("On".equals(cast.SoundBar)) {
                //bind OnState and ChangeInput to SoundBar
                nc[0] = new NameComponent("OnState", "Object");
                Context3.rebind(nc, CastServ);
                System.out.println("Object 'OnState' added to SoundBar.");

                nc[0] = new NameComponent("ChangeInput", "Object");
                Context3.rebind(nc, CastServ);
                System.out.println("Object 'ChangeInput' added to SoundBar.");
            }
            //Turn Lights off
            if ("Off".equals(cast.Lights)) {
                //bind OffState to Lights
                nc[0] = new NameComponent("OffState", "Object");
                Context4.rebind(nc, CastServ);
                System.out.println("Object 'OffState' added to Lights.");
            }
            Thread.sleep(5000);
            CastFrame.Loading.setText("Ready to go");
            CastFrame.OnButton.setEnabled(true);
            //run the orb
            orb.run();

            //error handling 
        } catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace(System.out);
        }

    }
}
