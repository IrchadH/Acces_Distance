package main;

import ecran.Acces_distance;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static String ip;
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(5000);
        System.out.println("En attente de connexion");
        Socket sc = socket.accept();
        ip = sc.getRemoteSocketAddress().toString();
        System.out.println("Connexion Etablie avec "+ip);

        new Acces_distance(sc);
    }
}
