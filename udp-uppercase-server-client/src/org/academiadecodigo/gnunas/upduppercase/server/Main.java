package org.academiadecodigo.gnunas.upduppercase.server;

import org.academiadecodigo.gnunas.upduppercase.client.Client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {
        //CATCHING AND DEFINING ADDRESS TO LOCALHOST
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Error fetching local host : "+e.getMessage());
            e.printStackTrace();
            return;
        }

        //DEFINE PORT HERE
        int port = 55055;
        int clientPort = 55056;

        Server server = new Server(address,port);
    }
}
