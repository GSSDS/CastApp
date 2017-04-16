package com.mycompany.castapp;

import java.io.IOException;
import java.net.InetAddress;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

//By extending class only one is needed in IDL, keeps IDL cleaner
class SoundBarServant extends LightsServant {

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

        boolean SoundBarOn = false;
        String Input = "";

        if (!SoundBarOn) {
            SoundBarOn = true;

            if (SoundBarOn) {
                Input = "TV";
            }
        }
        return "\n Sound Bar is on " + SoundBarOn + " And Input is " + Input+ "\n";

    }
}
