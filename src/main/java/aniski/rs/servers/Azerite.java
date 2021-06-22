package aniski.rs.servers;

import aniski.rs.ServerManager;
import aniski.rs.Store;
import aniski.rs.net.RSSocket;
import aniski.rs.net.Stream;
import aniski.rs.utils.ISAACRandomGen;
import aniski.rs.utils.Misc;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class Azerite extends ServerManager {

    private static final String serverAddress = "54.39.28.201";
    private static final int port = 43594;

    private final BigInteger RSA_MODULUS = new BigInteger("141038977654242498796653256463581947707085475448374831324884224283104317501838296020488428503639086635001378639378416098546218003298341019473053164624088381038791532123008519201622098961063764779454144079550558844578144888226959180389428577531353862575582264379889305154355721898818709924743716570464556076517");
    private final BigInteger RSA_EXPONENT = new BigInteger("65537");

    public Azerite(String username, String password, Stream stream, Stream inStream, Stream aStream_847, Store store, boolean flag, RSSocket rsSocket) throws IOException {
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
        /** SPLIT FROM THE BIT - Get Response Code **/

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
            getStream().writeInt(350);

            //MAC ADDRESS  08-60-6E-7C-33-69
            Random r = new Random();
            int low = 10;
            int high = 99;
            String macAddress = (r.nextInt(high-low) + low)+"-"+ (r.nextInt(high-low) + low)+"-"+ (r.nextInt(high-low) + low)+"-"+ (r.nextInt(high-low) + low)+"-"+ (r.nextInt(high-low) + low)+"-"+ (r.nextInt(high-low) + low);
            getStream().writeString(macAddress);
            getStream().writeString(getUsername());
            getStream().writeString(getPassword());

            //System.out.println(getUsername()+":"+getPassword());
            getStream().writeShort(222);
            getStream().writeByte(0);

            getStream().doKeys(RSA_MODULUS, RSA_EXPONENT);

            getInitialStream().currentOffset = 0;
            getInitialStream().writeByte(isFlag() ? 18 : 16);
            getInitialStream().writeByte(getStream().currentOffset + 36 + 1 + 1 + 2);
            getInitialStream().writeByte(255);
            int attack = Store.currentKey += 1;
            getInitialStream().writeShort(30);
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
            if(getResponseCode() != -1 && getResponseCode() != 30) {
            //    TextEditor.writePasswordToFile(String.valueOf(attack), String.valueOf(attack), "");
            }
        }
        getRSSocket().close();
    }

}
