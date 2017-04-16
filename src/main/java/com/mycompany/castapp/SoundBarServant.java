package com.mycompany.castapp;

import CastRequest.Cast;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.InetAddress;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

//By extending class only one is needed in IDL, keeps IDL cleaner
class SoundBarServant extends LightsServant {

    boolean SoundBarOn = false;
    String Input = "";

    public String SoundBarOn() {
        System.out.println("Received a call.");

        try {
            // Create a JmDNS instance
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            // Register a service
            ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local.", "SoundBar", 8000, "path=index.html");
            jmdns.registerService(serviceInfo);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {

            //getting resource form static context
            File file = new File( SoundBarServant.class.getResource( "/cast.json" ).toURI() );
            Reader reader = new FileReader(file);

            // Convert JSON to Java Object
            Cast cast = gson.fromJson(reader, Cast.class);

            //Switch on or off depending on JSON
            if ("On".equals(cast.SoundBar)) {
                SoundBarOn = true;
                Input = "TV";
                ReturnMessage = "Sound Bar Device On ";
            }
            if ("Off".equals(cast.SoundBar)) {
                SoundBarOn = false;
                Input = "Device OFF";
                ReturnMessage = "Sound Bar Device On ";
            }
        } catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace(System.out);
        }
        return "\n" + ReturnMessage + SoundBarOn + " And Input is " + Input + "\n";

    }
}
