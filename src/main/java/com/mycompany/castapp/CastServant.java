package com.mycompany.castapp;

import com.mycompany.castapp.CastApp._CastOnImplBase;
import java.io.IOException;
import java.net.InetAddress;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

//Code Based on Tutorials supplied on moodle https://moodle.ncirl.ie/course/view.php?id=806
class CastServant extends _CastOnImplBase {

    public String CastOn() {
        System.out.println("Received a call.");

        try {
            // Create a JmDNS instance
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            // Register a service
            ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local.", "CastDevice", 8000, "path=index.html");
            jmdns.registerService(serviceInfo);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        boolean CastOn = false;

        if (!CastOn) {
            CastOn = true;

        }

        return "\n Cast Device on " + CastOn + "";
    }
}
