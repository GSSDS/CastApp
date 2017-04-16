package com.mycompany.castapp;

import CastRequest.Cast;
import com.google.gson.Gson;
import com.mycompany.castapp.CastApp._CastOnImplBase;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.InetAddress;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

//Code Based on Tutorials supplied on moodle https://moodle.ncirl.ie/course/view.php?id=806
class CastServant extends _CastOnImplBase {

    Gson gson = new Gson();
    boolean CastOn = false;
    String ReturnMessage = "";
    String FilePath = "C:\\Users\\Graham\\Documents\\NetBeansProjects\\CastApp\\src\\main\\java\\com\\mycompany\\castapp\\cast.json";

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

        //create contexts and then if Java Object attribute matches value add objects
        try {

            //need to change this to your local directory
            //TODO: possibly change to come from HTTP server instead
            Reader reader = new FileReader(FilePath);

            // Convert JSON to Java Object
            Cast cast = gson.fromJson(reader, Cast.class);

            //Switch on or off depending on JSON
            if ("On".equals(cast.Cast)) {
                CastOn = true;
                ReturnMessage = "Cast Device On ";
            }
            if ("Off".equals(cast.Cast)) {
                CastOn = false;
                ReturnMessage = "Cast Device On ";
            }
        } catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace(System.out);
        }

        return "\n" + ReturnMessage + CastOn;
    }
}
