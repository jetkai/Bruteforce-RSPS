package aniski.rs.net;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ProxyManager {

    private final ArrayList<String> SOCKS_PROXIES = new ArrayList<>();

    public ProxyManager() {
        init();
    }

    private void init() {
        try {
            getProxiesFromFile();
        } catch (IOException ignored) {
            System.out.println("Error getting proxy list.");
            //e.printStackTrace();
        }
    }

    public RSSocket loadRSSocketProxy(String serverAddress, int serverPort) {
        String proxyAddress;
        int proxyPort;

        String[] proxyFormat = SOCKS_PROXIES.get(new Random().nextInt(SOCKS_PROXIES.size())).split(":");
        proxyAddress = proxyFormat[0];
        proxyPort = Integer.parseInt(proxyFormat[1]);

        if(proxyAddress == null || proxyPort == -1)
            return null;

        String proxyAddressFull = proxyAddress + ":" + proxyPort;
        System.out.println("Connecting to proxy... ("+proxyAddressFull+")");
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxyAddress, proxyPort));
        Socket socket = new Socket(proxy);
        try {
            socket.setTcpNoDelay(true);
            socket.setReuseAddress(true);
            socket.setSoTimeout(3000);
            socket.setKeepAlive(true);
            socket.setSoLinger(true, 3000);
            socket.connect(new InetSocketAddress(serverAddress, serverPort));
        } catch (IOException e) {
            try {
              //  SOCKS_PROXIES.remove(proxyAddress + ":" + proxyPort);
                System.out.println("Failed to connect to proxy... ("+proxyAddressFull+")");
                socket.close();
                return null;
            } catch (IOException ioException) {
                //ioException.printStackTrace();
                return null;
            }
        }
        return new RSSocket(socket);
    }

    private void getProxiesFromFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("proxies.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(!SOCKS_PROXIES.contains(line))
                    SOCKS_PROXIES.add(line);
            }
        }
        System.out.println("Proxies loaded");
    }

}
