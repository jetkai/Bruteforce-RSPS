package aniski.rs.servers;

import aniski.rs.ServerManager;
import aniski.rs.Store;
import aniski.rs.net.RSSocket;
import aniski.rs.net.Stream;
import aniski.rs.utils.ISAACRandomGen;
import aniski.rs.utils.Misc;

import java.io.IOException;
import java.math.BigInteger;

public class GodzPk extends ServerManager {

    private static final String serverAddress = "38.89.142.141";
    private static final int port = 43594;

    private final BigInteger RSA_MODULUS = new BigInteger("90308879473207602727632697636107470337691226492402856190586123314261905278534938617565816691125103482003403680791478209230762539597273655608947341580186956420399488846498743914648429925686009129436957708388537260380390554724266496253564642099797813769876108294938424415742269996107039627049807815582588392023");
    private final BigInteger RSA_EXPONENT = new BigInteger("65537");

    public GodzPk(String username, String password, Stream stream, Stream inStream, Stream aStream_847, Store store, boolean flag, RSSocket rsSocket) throws IOException {
        super(serverAddress, port, username, password, stream, inStream, aStream_847, store, flag, rsSocket);
    }

    @Override
    public void initSocketRequest() throws IOException {
        long l = Misc.longForName(getUsername());
        int i = (int)(l >> 16L & 0x1FL);

      //  Proxy socksProxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1080));

        /** Init Login Connection **/

        getStream().currentOffset = 0;
        getStream().writeByte(14);
        getStream().writeByte(i);
        getRSSocket().queueBytes(2, getStream().buffer);
        for (int j = 0; j < 8; j++)
            getRSSocket().read();
        int responseCode = getRSSocket().read();

        //System.out.println("First response: "+responseCode);

        if (responseCode == 0) {
            getRSSocket().flushInputStream(getInStream().buffer, 8);
            getInStream().currentOffset = 0;

            long aLong1215 = getInStream().readQWord();
            int[] ai = new int[4];
            ai[0] = (int)(Math.random() * 9.9999999E7D);
            ai[1] = (int)(Math.random() * 9.9999999E7D);
            ai[2] = (int)(aLong1215 >> 32L);
            ai[3] = (int)aLong1215;

            getStream().currentOffset = 0;
            getStream().writeByte(10);
            getStream().writeInt(ai[0]);
            getStream().writeInt(ai[1]);
            getStream().writeInt(ai[2]);
            getStream().writeInt(ai[3]);
            getStream().writeInt(350); //signlink uid

            getStream().writeString(getUsername());
            getStream().writeString(getPassword());

            getStream().doKeys(RSA_MODULUS, RSA_EXPONENT);

            getInitialStream().currentOffset = 0;
            getInitialStream().writeByte(isFlag() ? 18 : 16);
            getInitialStream().writeByte(getStream().currentOffset + 36 + 1 + 1 + 2);
            getInitialStream().writeByte(255);
            getInitialStream().writeShort(317);
            getInitialStream().writeByte(0);

            for (int i2 = 0; i2 < 9; i2++)
                getInitialStream().writeInt(0);
            getInitialStream().writeBytes(getStream().buffer, getStream().currentOffset, 0);
            getStream().encryption = new ISAACRandomGen(ai);

            for (int j2 = 0; j2 < 4; j2++)
                ai[j2] = ai[j2] + 50;

            // encryption = new ISAACRandomGen(ai);
            //TODO encryption = new ISAACRandomGen(ai);
            getRSSocket().queueBytes(getInitialStream().currentOffset, getInitialStream().buffer);
            setResponseCode(getRSSocket().read());
           // System.out.println("Response Code: "+getResponseCode());
            if(getResponseCode() != -1 && getResponseCode() != 30) {
            //    TextEditor.writePasswordToFile(String.valueOf(attack), String.valueOf(attack), "");
            }
        }
        getRSSocket().close();
    }

}
