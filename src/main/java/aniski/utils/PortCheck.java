package aniski.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class PortCheck {

    public static boolean available(String ip, int port) {
        try (Socket socket = new Socket()) {
            socket.setTcpNoDelay(true);
            socket.setKeepAlive(false);
            socket.setSoTimeout(5000);
            SocketAddress socketAddress = new InetSocketAddress("51.210.109.174", port);
            socket.connect(socketAddress, 5000);
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }

}
