package com.mycompany.castapp;

import CastRequest.Cast;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.InetAddress;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

//By extending class only one is needed in IDL, keeps IDL cleaner
class TVServant extends SoundBarServant {

    boolean TvOn = false;
    String Input = "";

    public String TvOn() {
        System.out.println("Received a call.");

        try {
            // Create a JmDNS instance
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            // Register a service
            ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local.", "TV", 8000, "path=index.html");
            jmdns.registerService(serviceInfo);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {

            //need to change this to your local directory
            //TODO: possibly change to come from HTTP server instead
            Reader reader = new FileReader(FilePath);

            // Convert JSON to Java Object
            Cast cast = gson.fromJson(reader, Cast.class);

            //Switch on or off depending on JSON
            if ("On".equals(cast.Tv)) {
                TvOn = true;
                Input = "TV";
                ReturnMessage = "TV Device On ";
            }
            if ("Off".equals(cast.Tv)) {
                TvOn = false;
                Input = "Device OFF";
                ReturnMessage = "TV Device On ";
            }
        } catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace(System.out);
        }

        return "\n" + ReturnMessage + TvOn + " and Input is " + Input + "\n";

    }
}
