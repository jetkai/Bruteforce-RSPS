package aniski.rs.servers;

import aniski.rs.ServerManager;
import aniski.rs.Store;
import aniski.rs.net.RSSocket;
import aniski.rs.net.Stream;
import aniski.rs.utils.ISAACRandomGen;
import aniski.rs.utils.Misc;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;

public class Envision extends ServerManager {

    private static final String serverAddress = "?????";
    private static final int port = 0;

    private final BigInteger RSA_MODULUS = new BigInteger("141038977654242498796653256463581947707085475448374831324884224283104317501838296020488428503639086635001378639378416098546218003298341019473053164624088381038791532123008519201622098961063764779454144079550558844578144888226959180389428577531353862575582264379889305154355721898818709924743716570464556076517");
    private final BigInteger RSA_EXPONENT = new BigInteger("65537");

    public Envision(String username, String password, Stream stream, Stream inStream, Stream aStream_847, Store store, boolean flag) throws IOException {
        super(serverAddress, port, username, password, stream, inStream, aStream_847, store, flag, new RSSocket(new Socket(InetAddress.getByName(serverAddress), port)));
    }

    @Override
    public void initSocketRequest() throws IOException {
        long l = Misc.longForName(getUsername());
        int i = (int)(l >> 16L & 0x1FL);
        getStream().currentOffset = 0;
        getStream().writeByte(1);
        getStream().writeByte(i);
        getStream().writeByte(14);
        getRSSocket().queueBytes(3, getStream().buffer);
        for (int j = 0; j < 8; j++)
            getRSSocket().read();
        int k = getRSSocket().read();
        //Require 0 for login attempt
        if (k == 0) {
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

            String a13 = "NotForYouIdiot11";
            SecretKeySpec a14 = new SecretKeySpec(a13.getBytes(), "AES");
            try {
                Cipher a15 = Cipher.getInstance("AES");
                a15.init(1, a14);
                byte[] a16 = a15.doFinal(getUsername().getBytes());
                getStream().writeByte(a16.length);
                getStream().writeStringAsBytes(a16);
            }
            catch (Exception a17) {
                a17.printStackTrace();
                return;
            }

            getStream().writeString(getPassword());

            getStream().writeShort(222);
            getStream().writeByte(0);

            //Encrypts with RSA (Previous stream.*)

            getStream().doKeys(RSA_MODULUS, RSA_EXPONENT);

            //Non RSA Buffer (aStream_847)
            getInitialStream().currentOffset = 0;
            getInitialStream().writeByte(isFlag() ? 18 : 16);
            getInitialStream().writeByte(getStream().currentOffset + 36 + 1 + 1 + 2);
            getInitialStream().writeByte(255);
            getInitialStream().writeShort(112);
            getInitialStream().writeByte(0);

            //Login Encryption
            for (int i2 = 0; i2 < 9; i2++)
                getInitialStream().writeInt(0);

            getInitialStream().writeBytes(getStream().buffer, getStream().currentOffset, 0);
            getStream().encryption = new ISAACRandomGen(ai);


            for (int j2 = 0; j2 < 4; j2++)
                ai[j2] = ai[j2] + 50;
            //TODO encryption = new ISAACRandomGen(ai);
            getRSSocket().queueBytes(getInitialStream().currentOffset, getInitialStream().buffer);
            setResponseCode(getRSSocket().read());
        }
        getRSSocket().close();
    }

}
