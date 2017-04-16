package com.mycompany.castapp;

import java.io.IOException;
import java.net.InetAddress;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

//By extending class only one is needed in IDL, keeps IDL cleaner
class LightsServant extends CastServant {

    public String LightsOff() {
        System.out.println("Received a call.");

        try {
            // Create a JmDNS instance
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            // Register a service
            ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local.", "Lights", 8000, "path=index.html");
            jmdns.registerService(serviceInfo);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        boolean LightsOff = false;

        if (!LightsOff) {
            LightsOff = true;
        }

        return "\n Lights are switched off " + LightsOff + "\n";
    }
}
