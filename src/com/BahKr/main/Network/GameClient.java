package com.BahKr.main.Network;

import com.BahKr.main.GameObject.Entity.Entities.PlayerMP;
import com.BahKr.main.Handler;
import com.BahKr.main.Network.Packet.Packet;
import com.BahKr.main.Network.Packet.Packet00Login;
import com.BahKr.main.Network.Packet.Packet01Disconnect;
import com.BahKr.main.Network.Packet.Packet02Move;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GameClient extends Thread {

    private InetAddress ipAddress;
    private DatagramSocket socket;
    private Handler handler;

    public GameClient(Handler handler, String ipAddress) {
        this.handler = handler;
        try {
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
        Packet packet = null;
        switch (type) {
            default:
            case INVALID:
                break;
            case LOGIN:
                packet = new Packet00Login(data);
                handleLogin((Packet00Login) packet, address, port);
                break;
            case DISCONNECT:
                packet = new Packet01Disconnect(data);
                System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                        + ((Packet01Disconnect) packet).getUsername() + " has left the world...");
                handler.getWorld().removePlayerMP(((Packet01Disconnect) packet).getUsername());
                break;
            case MOVE:
                packet = new Packet02Move(data);
                handleMove((Packet02Move) packet);
        }
    }

    public void sendData(byte[] data) {
            DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
            try {
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void handleLogin(Packet00Login packet, InetAddress address, int port) {
        System.out.println("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername()
                + " has joined the game...");
        PlayerMP player = new PlayerMP(packet.getX(), packet.getY(), packet.getUsername(), address, port);
        handler.getWorld().addObj(player);
    }

    private void handleMove(Packet02Move packet) {
        handler.getWorld().movePlayer(packet.getUsername(), packet.getX(), packet.getY());
    }
}