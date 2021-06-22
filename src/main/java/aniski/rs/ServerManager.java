package aniski.rs;

import aniski.rs.net.RSSocket;
import aniski.rs.net.Stream;

import java.io.IOException;

public class ServerManager {

    private final String serverAddress;
    private final int port;

    private final String username;
    private final String password;
    private String twoFactor;

    private final Stream stream;
    private final Stream inStream;
    private final Stream aStream_847;

    private final Store store;

    private final boolean flag;

    private final RSSocket rsSocket;

    private int responseCode;

    public ServerManager(String serverAddress, int port, String username, String password, Stream stream, Stream inStream, Stream aStream_847, Store store, boolean flag, RSSocket socket) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.username = username;
        this.password = password;
        this.stream = stream;
        this.inStream = inStream;
        this.aStream_847 = aStream_847;
        this.store = store;
        this.flag = flag;
        this.rsSocket = socket;
    }

    public void initSocketRequest() throws IOException {
       // if(rsSocket != null)
        //    rsSocket.close();
        //EMPTY
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        username = username;
    }

    public String getPassword() {
        return password;
    }

    public Stream getStream() {
        return stream;
    }

    public Stream getInStream() {
        return inStream;
    }

    public Stream getInitialStream() {
        return aStream_847;
    }

    public Store getStore() {
        return store;
    }

    public boolean isFlag() {
        return flag;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public RSSocket getRSSocket() {
        return rsSocket;
    }

    public void setTwoFactor(String twoFactor) {
        this.twoFactor = twoFactor;
    }

    public String getTwoFactor() {
        return twoFactor;
    }
}
