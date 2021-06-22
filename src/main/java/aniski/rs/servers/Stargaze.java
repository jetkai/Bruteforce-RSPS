package aniski.rs.servers;

import aniski.rs.ServerManager;
import aniski.rs.Store;
import aniski.rs.net.RSSocket;
import aniski.rs.net.Stream;
import aniski.rs.utils.ISAACRandomGen;
import aniski.rs.utils.Misc;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

public class Stargaze extends ServerManager {

    private static final String serverAddress = "54.39.130.9";
    private static final int port = 43594;

    private final BigInteger RSA_MODULUS = new BigInteger("95892309900012624757910558612557157518198663289972351185811635282592909639354396843132458269773865442438993811118105657969764786006812201054111790113972159117832807360015615760760594495186361657904488588188269156833795823475566293123208955847868171408876316600634354049014677786140754619828747388784573881389");
    private final BigInteger RSA_EXPONENT = new BigInteger("65537");

    public Stargaze(String username, String password, Stream stream, Stream inStream, Stream aStream_847, Store store, boolean flag) throws IOException {
        super(serverAddress, port, username, password, stream, inStream, aStream_847, store, flag, new RSSocket(new Socket(InetAddress.getByName(serverAddress), port)));
    }

    @Override
    public void initSocketRequest() throws IOException {
        long l = Misc.longForName(getUsername());
        int i = (int)(l >> 16L & 0x1FL);

        /** Init Login Connection **/
        getStream().currentOffset = 0;
        getStream().writeByte(14);
        getStream().writeByte(i);
        getRSSocket().queueBytes(2, getStream().buffer);
        for (int j = 0; j < 8; j++)
            getRSSocket().read();
        int responseCode = getRSSocket().read();

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
            getStream().writeInt(313);

            //MAC ADDRESS  08-60-6E-7C-33-69
            Random r = new Random();
            int low = 10;
            int high = 99;
            String macAddress =
                    (r.nextInt(high-low) + low)+"-"+
                            (r.nextInt(high-low) + low)+"-"+
                            (r.nextInt(high-low) + low)+"-"+
                            (r.nextInt(high-low) + low)+"-"+
                            (r.nextInt(high-low) + low)+"-"+
                            (r.nextInt(high-low) + low);
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
            getInitialStream().writeLong(255L);
            getInitialStream().writeShort(53);
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
        }
        getRSSocket().close();
    }

}
