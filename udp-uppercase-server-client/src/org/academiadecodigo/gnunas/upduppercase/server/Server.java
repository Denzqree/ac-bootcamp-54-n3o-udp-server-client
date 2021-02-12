package org.academiadecodigo.gnunas.upduppercase.server;

import java.io.IOException;
import java.net.*;

public class Server {

    private InetAddress address = null;
    private int port = 0;
    private int clientPort = 0;
    private DatagramSocket socket = null;

    public Server(InetAddress address, int port) {

        if (address == this.address || port < this.port) {
            System.out.println("No server (InetAddress) or port(s) specified");
            return;
        }

        this.address = address;
        this.port = port;

        if (openSocket(this.port)) {
            System.out.println("[SOCKET OPENED]");
            System.out.println("[RECEIVING TEXT FROM CLIENT....]");
            String stringReceived = receiveUDPString();
            if (!stringReceived.equals("")) {
                sendToUpperCase(stringReceived, this.address, this.clientPort);
                System.out.println("! \""+stringReceived+"\" SENT TO CLIENT !");
            }

        }
    }

    private void sendToUpperCase(String stringReceived, InetAddress address, int clientPort) {
        sendUDPString(stringReceived.toUpperCase(), address, clientPort);
    }

    // THIS METHOD RECEIVES A UDP STRING
    private String receiveUDPString() {

        byte[] receiveBuffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        try {
            System.out.println("Receiving packets...");
            this.socket.receive(receivePacket);
            this.clientPort = receivePacket.getPort();
        } catch (IOException e) {
            closeSocket(this.socket);
            System.out.println("Error receiving packet : " + e.getMessage());
            e.printStackTrace();
            return "";
        }
        return new String(receiveBuffer);
    }

    private boolean sendUDPString(String input, InetAddress address, int clientPort) {

        byte[] sendBuffer = input.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, clientPort);

        try {

            System.out.println("[SENDING PACKET...]");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Error putting thread to sleep...");
                e.printStackTrace();
            }

            this.socket.send(sendPacket);
            System.out.println("[PACKET SENT !]");
            return true;

        } catch (IOException e) {
            closeSocket(this.socket);
            System.out.println("Error sending Datagram Packet : " + e.getMessage());
            e.printStackTrace();
        }

        return false;

    }

    private boolean openSocket(int port) {

        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException e) {
            closeSocket(this.socket);
            System.out.println("Error opening Datagram Socket : " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (this.socket != null) {
                return true;
            }
        }
        return false;
    }

    private void closeSocket(DatagramSocket socket) {
        socket.close();
        System.out.println("[!SOCKET CLOSED!]");
    }

}
