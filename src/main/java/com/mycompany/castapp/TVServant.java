package com.mycompany.castapp;

import java.io.IOException;
import java.net.InetAddress;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

//By extending class only one is needed in IDL, keeps IDL cleaner
class TVServant extends SoundBarServant {

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

        
        boolean TvOn = false;
        String Input = "";

        if (!TvOn) {
            TvOn = true;

        if (TvOn) {
            Input = "TV";
        }
        }
        return "\n TV is on " + TvOn + " and Input is " + Input+ "\n";
    
}
}