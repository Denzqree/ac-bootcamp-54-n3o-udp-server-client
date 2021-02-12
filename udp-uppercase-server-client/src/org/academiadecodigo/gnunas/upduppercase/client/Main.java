package org.academiadecodigo.gnunas.upduppercase.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {

        //CATCHING LOCALHOST ADDRESS
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Error catching local host : "+e.getMessage());
            e.printStackTrace();
            return;
        }

        //DEFINE PORT HERE
        int port = 55056;
        int serverPort = 55055;

        Client client = new Client(address, serverPort);
    }

}
