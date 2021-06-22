package aniski.rs.servers;

import aniski.rs.ServerManager;
import aniski.rs.Store;
import aniski.rs.net.RSSocket;
import aniski.rs.net.Stream;
import aniski.rs.utils.ISAACRandomGen;

import java.io.IOException;
import java.math.BigInteger;

public class localhost718 extends ServerManager {

    private static final String serverAddress = "127.0.0.1";
    private static final int port = 43594;

    private final BigInteger RSA_MODULUS = new BigInteger("135252348768665077635741721957353390282499196160692171709845448155963164532706244870728853403799325066507124378230226927138021243954534903297384746464211154793581403970982194545379291592214853590086028609334242657579723419696938265964747992773207383237448008221192605273035890497659188786240923114771728636709");
    private final BigInteger RSA_EXPONENT = new BigInteger("65537");
    

    public localhost718(String username, String password, Stream stream, Stream inStream, Stream aStream_847, Store store, boolean flag, RSSocket rsSocket) throws IOException {
        super(serverAddress, port, username, password, stream, inStream, aStream_847, store, flag, rsSocket);
    }

    private void initConnection() throws IOException {
       // loginStream = Stream.create();
        /** Init Login Connection **/
        getStream().currentOffset = 0;
        getStream().writeByte(14);
        getRSSocket().queueBytes(1, getStream().buffer);
    }

    private void sendStreamBeforeRSA() {
        int revision = 718;
        int sub_revision = 1;
        int game_state = 0;
        getStream().writeInt(revision);
        getStream().writeInt(sub_revision);
        getStream().writeByte(game_state);
    }

    private final int[] anIntArray3889 = new int[4];

    private void applyRSABlock() {
        long aLong1215 = getInStream().readQWord(); //This should read 0
        System.out.println("aLong Value: "+aLong1215);
        Stream rsaStream = Stream.create(5000);
        anIntArray3889[0] = (int)(Math.random() * 9.9999999E7D);
        anIntArray3889[1] = (int)(Math.random() * 9.9999999E7D);
        anIntArray3889[2] = (int)(aLong1215 >> 32L);
        anIntArray3889[3] = (int)aLong1215;

        rsaStream.currentOffset = 0;
        rsaStream.writeByte(10);
        rsaStream.writeInt(anIntArray3889[0]);
        rsaStream.writeInt(anIntArray3889[1]);
        rsaStream.writeInt(anIntArray3889[2]);
        rsaStream.writeInt(anIntArray3889[3]);

        rsaStream.writeRealLong(0L); // This has to be 0
        rsaStream.writeString("dragon");
        rsaStream.writeRealLong(0L);
        rsaStream.writeRealLong(0L);

        rsaStream.doKeys(RSA_MODULUS, RSA_EXPONENT);
        getStream().writeBytes(rsaStream.buffer, rsaStream.currentOffset, 0);
    }

    private void sendStreamAfterRSA() {
        getStream().writeByte(1);
        getStream().writeString("jet_kai");

        /** SCREEN SIZES **/
        getStream().writeByte(1); //Display Mode
        getStream().writeShort(765); //Width
        getStream().writeShort(553); //Height

        getStream().writeByte(0);
        method322(getStream(), (byte) 1);
        //Server skips this - Switching to Method322 to skip

        getStream().writeString("wwGlrZHF5gKN6D3mDdihco3oPeYN2KFybL9hUUFqOvk");
      //  getStream().writeByte(0);
        getStream().writeInt(36);
        sendFakePreferences();
        getStream().writeInt(0);
        getStream().writeRealLong(0L);
        getStream().writeByte(0);
        getStream().writeByte(0);//hasJag
        getStream().writeByte(1);//JS
        getStream().writeByte(0);//HC
        getStream().writeByte(2);
        getStream().writeInt(1378752098);
        getStream().writeString("hAJWGrsaETglRjuwxMwnlA/d5W6EgYWx");
        getStream().writeByte(1);

        int[] CRC = new int[]{ -886037363, 1843339303 -995747772, -540377867, 2080523223, -1555670768, 1174553629,
                -748664774, -280188894, 1647698739, 1010592773, -234029798, 2133835426, -1332399266,
                -1608637606, 598117996, -1465973738, -266841426, -1058787411, -332639626, 50309635,
                -961081345, 298975444, 2121169803, -13549517, -725329986, -1133326939, 490151514,
                338822899, 108169857, -1729577670, -417215217, 1037989559, 1360518100, -1290325219,
                -897611144, 808463686, 1177368901 };

        for(int crc : CRC)
            getStream().writeInt(crc);

        getStream().writeString("-9C-9A-1A");//Trash Mac

        getStream().sendCRC(anIntArray3889, getStream().currentOffset, getStream().currentOffset);

        getStream().writeShort(0);

        getStream().writeBytes(getStream().buffer, getStream().currentOffset, 0);
    }

    private void sendFakePreferences() {
        Stream preferences = Stream.create(5000);
        for(int skip = 0; skip < 24; skip++)
            preferences.writeByte(0);
        preferences.writeByte(preferences.currentOffset);
        getStream().writeBytes(preferences.buffer, preferences.currentOffset, 0);
    }
    
    @Override
    public void initSocketRequest() throws IOException {

        initConnection();

        int responseCode = getRSSocket().read(); //This should read 0
        System.out.println("First response: "+responseCode);


        if (responseCode == 0) {
            
           // getRSSocket().flushInputStream(getInStream().buffer, 8);
            getInStream().currentOffset = 0;

            sendStreamBeforeRSA();
            applyRSABlock();
            sendStreamAfterRSA();

            getInitialStream().currentOffset = 0;
            getInitialStream().writeByte(16/*isFlag() ? 18 : 16*/); //Login Flag
            getInitialStream().writeShort(getStream().currentOffset + 4 + 4 + 3 + 1); // Remaining
            /*getInitialStream().writeInt(718); //Revision
            getInitialStream().writeInt(1); //Sub Revision
            getInitialStream().writeByte(0);
            getInitialStream().writeShort(128);
            getInitialStream().writeByte(255);*/

            getStream().currentOffset = 0;
            
            
            getInitialStream().writeBytes(getStream().buffer, getStream().currentOffset,0);

            getStream().encryption = new ISAACRandomGen(anIntArray3889);

            for (int j2 = 0; j2 < 4; j2++)
                anIntArray3889[j2] = anIntArray3889[j2] + 50;

            // encryption = new ISAACRandomGen(ai);
            //TODO encryption = new ISAACRandomGen(ai);
            getRSSocket().queueBytes(getInitialStream().currentOffset, getInitialStream().buffer);
            setResponseCode(getRSSocket().read());
            if(getResponseCode() != -1 && getResponseCode() != 30) {
                //  TextEditor.writePasswordToFile(String.valueOf(attack), String.valueOf(attack));
            }
        }
        getRSSocket().close();
    }

    public static void method322(Stream buffer, byte i) {
            byte[] is = new byte[24];
            try {
                int i_0_;
                for (i_0_ = 0; i_0_ < 24; i_0_++) {
                    if (0 != is[i_0_]) {
                        if (i != 1) {
                            /* empty */
                        }
                        break;
                    }
                }
                if (i_0_ >= 24)
                    throw new IOException();
            }
            catch (Exception exception) {
                for (int i_1_ = 0; i_1_ < 24; i_1_++)
                    is[i_1_] = (byte) -1;
            }
            buffer.writeBytes(is, 0, 24);
        }
}
