package org.academiadecodigo.gnunas.upduppercase.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client {

    private InetAddress address = null;
    private int serverPort = 0;
    private DatagramSocket socket = null;

    public Client(InetAddress address, int serverPort) {

        if(address == this.address || serverPort < this.serverPort){
            System.out.println("No server (InetAddress) or port(s) specified");
            return;
        }

        this.address = address;
        this.serverPort = serverPort;

        if (openSocket(this.address)) {
            System.out.println("[SOCKET OPENED]");
            System.out.println("Please enter the text to be UPPERCASED :");
            if(sendUDPString(askUser(),this.address,this.serverPort)){
                System.out.println(receiveUDPString());
            };
        }
    }

    // THIS METHOD RECEIVES A UDP STRING
    private String receiveUDPString() {

        byte[] receiveBuffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        try {
            System.out.println("Receiving packets...");
            this.socket.receive(receivePacket);
        } catch (IOException e) {
            closeSocket(this.socket);
            System.out.println("Error receiving packet : " + e.getMessage());
            e.printStackTrace();
            return "";
        }
        return new String(receiveBuffer);
    }

    private boolean sendUDPString(String input, InetAddress address, int serverPort) {

        byte[] sendBuffer = input.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, serverPort);

        try {
            System.out.println("[SENDING PACKET...]");
            this.socket.send(sendPacket);
            return true;
        } catch (IOException e) {
            closeSocket(this.socket);
            System.out.println("Error sending Datagram Packet : " + e.getMessage());
            e.printStackTrace();
        }

        return false;

    }

    private String askUser() {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            String strLine = in.readLine();
            return strLine;
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                in.close();
            } catch (IOException e) {
                System.out.println("Error closing the system input : "+e.getMessage());
                e.printStackTrace();
            }
        }

        return "";
    }

    private boolean openSocket(InetAddress address) {

        try {
            this.socket = new DatagramSocket();
        } catch (SocketException e) {
            closeSocket(this.socket);
            System.out.println("Error opening Datagram Socket : " + e.getMessage());
            e.printStackTrace();
            return false;
        }finally{
           if(this.socket != null){
               return true;
           }
        }
        return false;
    }

    private void closeSocket(DatagramSocket socket){
        socket.close();
    }

}
