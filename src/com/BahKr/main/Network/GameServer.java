package com.BahKr.main.Network;

import com.BahKr.main.GameObject.Entity.Entities.PlayerMP;
import com.BahKr.main.Handler;
import com.BahKr.main.Network.Packet.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;

public class GameServer extends Thread {

    private DatagramSocket socket;
    private Handler handler;
    private LinkedList<PlayerMP> connectedPlayers = new LinkedList<PlayerMP>();

    public GameServer(Handler handler) {
        this.handler = handler;
        try {
            this.socket = new DatagramSocket(1331);
        } catch (SocketException e) {
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
            parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
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
                System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                        + ((Packet00Login) packet).getUsername() + " has connected...");
                PlayerMP player = new PlayerMP(handler, 100, 100, ((Packet00Login) packet).getUsername(), address, port);
                addConnection(player, (Packet00Login) packet);
                break;
            case DISCONNECT:
                packet = new Packet01Disconnect(data);
                System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                        + ((Packet01Disconnect) packet).getUsername() + " has left...");
                removeConnection((Packet01Disconnect) packet);
                break;
            case MOVE:
                packet = new Packet02Move(data);
                handleMove(((Packet02Move) packet));
                break;
            case SHOOT:
                packet = new Packet03Shoot(data);
                handleShoot((Packet03Shoot) packet);
                break;
        }
    }

    public void addConnection(PlayerMP player, Packet00Login packet) {
        boolean alreadyConnected = false;
        for (PlayerMP p : connectedPlayers) {
            if (player.getUsername().equalsIgnoreCase(p.getUsername())) {
                if (p.ipAddress == null) {
                    p.ipAddress = player.ipAddress;
                }
                if (p.port == -1) {
                    p.port = player.port;
                }
                alreadyConnected = true;
            } else {
                sendData(packet.getData(), p.ipAddress, p.port);
                sendData(new Packet00Login(p.getUsername(), (int) p.x, (int) p.y).getData(), player.ipAddress, player.port);
            }
        }
        if (!alreadyConnected) {
            connectedPlayers.add(player);
        }
    }

    public void removeConnection(Packet01Disconnect packet) {
        connectedPlayers.remove(getPlayerMPIndex(packet.getUsername()));
        packet.writeData(this);
    }

    public PlayerMP getPlayerMP(String username) {
        for (PlayerMP player : this.connectedPlayers) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    public int getPlayerMPIndex(String username) {
        int index = 0;
        for (PlayerMP player : this.connectedPlayers) {
            if (player.getUsername().equals(username)) {
                break;
            }
            index++;
        }
        return index;
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDataToAllClients(byte[] data) {
        for (PlayerMP p : connectedPlayers) {
            sendData(data, p.ipAddress, p.port);
        }
    }

    private void handleMove(Packet02Move packet) {
        if (getPlayerMP(packet.getUsername()) != null) {
            int index = getPlayerMPIndex(packet.getUsername());
            PlayerMP player = this.connectedPlayers.get(index);
            player.x = packet.getX();
            player.y = packet.getY();
            packet.writeData(this);
        }
    }

    private void handleShoot(Packet03Shoot packet) {
        packet.writeData(this);
    }
}