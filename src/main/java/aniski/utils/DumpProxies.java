package aniski.utils;

import aniski.rs.net.RSSocket;
import aniski.rs.net.Stream;
import aniski.rs.utils.TextEditor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static aniski.rs.utils.TextEditor.fileToString;

public class DumpProxies {

    private final String API_URL = "https://www.proxyscan.io/api/proxy?type=socks4,socks5&limit=100&lastCheck=3600";
    private final String serverAddress = "54.39.28.201";
    private final int serverPort = 43594;

    private final ArrayList<String> SOCKS4 = new ArrayList<>();
    private final ArrayList<String> SOCKS5 = new ArrayList<>();

    private final ArrayList<String> ALIVE_SOCKS4 = new ArrayList<>();
    private final ArrayList<String> ALIVE_SOCKS5 = new ArrayList<>();

    public static void main(String[] args) {
        DumpProxies dumpProxies = new DumpProxies();
        //dumpProxies.initDumper();
        dumpProxies.initChecker();
    }

    private void initDumper() {
        ThreadFactory threadFactory = new aniski.rs.utils.ThreadFactory();
        Executors.newSingleThreadScheduledExecutor(threadFactory).scheduleAtFixedRate(() -> {
            connectURL();
            appendProxiesToFile();
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void initChecker() {
        getProxiesFromFile();
        if(SOCKS4.size() > 0 || SOCKS5.size() > 0)
            initTestProxyThreads(100);
    }

    private void connectURL() {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;

        StringBuilder stringBuilder = new StringBuilder();

        try {
            url = new URL(API_URL);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null)
                stringBuilder.append(line).append(System.lineSeparator());
        } catch (IOException mue) {
            mue.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        getProxiesFromJson(stringBuilder.toString());
    }

    private void getProxiesFromJson(String response) {
        JSONArray jsonArray = new JSONArray(response);
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String proxyFormat = (jsonObject.get("Ip") + ":" + jsonObject.get("Port"));
            String type = String.valueOf(jsonObject.getJSONArray("Type").getString(0));
            if(type.equals("SOCKS4")) {
                SOCKS4.add(proxyFormat);
            } else if(type.equals("SOCKS5")) {
                SOCKS5.add(proxyFormat);
            }
        }
    }

    private void getProxiesFromFile() {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("socks4.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if(!SOCKS4.contains(line))
                        SOCKS4.add(line);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("socks5.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if(!SOCKS5.contains(line))
                        SOCKS5.add(line);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("Proxies loaded");
    }

    private void initTestProxyThreads(int threads) {
        IntStream.range(0, threads).forEach(i -> {
            ThreadFactory threadFactory = new aniski.rs.utils.ThreadFactory();
            Executors.newSingleThreadScheduledExecutor(threadFactory).scheduleAtFixedRate(() -> testProxies("SOCKS4", i), 0, 3, TimeUnit.SECONDS);
        });
        IntStream.range(0, threads).forEach(i -> {
            ThreadFactory threadFactory = new aniski.rs.utils.ThreadFactory();
            Executors.newSingleThreadScheduledExecutor(threadFactory).scheduleAtFixedRate(() -> testProxies("SOCKS5", i), 0, 3, TimeUnit.SECONDS);
        });
    }

    private void appendProxiesToFile() {
        SOCKS4.forEach(socks4 -> TextEditor.writeToFile("socks4.txt", socks4));
        SOCKS5.forEach(socks5 -> TextEditor.writeToFile("socks5.txt", socks5));
    }

    private void testProxies(String type, int threadId) {
        String proxyAddress;
        int proxyPort;
        if(type.equals("SOCKS4")) {
            String[] proxyFormat = SOCKS4.remove(threadId).split(":");
            proxyAddress = proxyFormat[0];
            proxyPort = Integer.parseInt(proxyFormat[1]);
        } else if(type.equals("SOCKS5")) {
            String[] proxyFormat = SOCKS5.remove(threadId).split(":");
            proxyAddress = proxyFormat[0];
            proxyPort = Integer.parseInt(proxyFormat[1]);
        } else
            return;
        if(proxyAddress == null || proxyPort == -1)
            return;
        String proxyAddressAndPort = proxyAddress + ":" + proxyPort;
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
            removeProxyFromFile(type.equals("SOCKS4") ? "SOCKS4.txt" : "SOCKS5.txt", proxyAddressAndPort);
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        RSSocket rsSocket = new RSSocket(socket);
        Stream stream = Stream.create(5000);
        stream.currentOffset = 0;
        stream.writeByte(14);
        stream.writeByte(0);
        try {
            rsSocket.queueBytes(2, stream.buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            int socketResponse = rsSocket.read();
            if(socketResponse != -1) {
                if(type.equals("SOCKS4"))
                    TextEditor.writeToFile("alive_socks4.txt", proxyAddressAndPort);
                   // ALIVE_SOCKS4.add(proxyAddressAndPort);
                else
                    TextEditor.writeToFile("alive_socks5.txt", proxyAddressAndPort);
                    //ALIVE_SOCKS5.add(proxyAddressAndPort);
                System.out.println("Great Proxy! (" + proxyAddress + ":" + proxyPort + ")");
            }
        } catch (IOException e) {
            System.out.println("Bad Proxy! ("+proxyAddress + ":" + proxyPort + ")");
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            rsSocket.close();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        rsSocket.close();
        //appendAliveProxiesToFile();
    }

    private void removeProxyFromFile(String folderName, String proxyAddress) {
        try {
            String result = fileToString(folderName);
            result = result.replaceAll(proxyAddress + "\n", "");
            PrintWriter writer = new PrintWriter(folderName);
            writer.append(result);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
