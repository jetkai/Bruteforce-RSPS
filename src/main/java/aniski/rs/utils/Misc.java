package aniski.rs.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Scanner;

public class Misc {

    public static long longForName(String s) {
        long l = 0L;
        for (int i = 0; i < s.length() && i < 12; i++) {
            char c = s.charAt(i);
            l *= 37L;
            if (c >= 'A' && c <= 'Z')
                l += (1 + c) - 65;
            else if (c >= 'a' && c <= 'z')
                l += (1 + c) - 97;
            else if (c >= '0' && c <= '9')
                l += (27 + c) - 48;
        }

        while (l % 37L == 0L && l != 0L) {
            l /= 37L;
        }
        return l;
    }

    public String getMacAddress(InetAddress address) {
        String macAddress;
        try {
            NetworkInterface network = NetworkInterface.getByInetAddress(address);
            if (network != null) {
                StringBuilder sb = new StringBuilder();
                byte[] mac = network.getHardwareAddress();
                if (mac != null) {
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format((i == mac.length - 1) ? "%02X" : "%02X-", mac[i]));
                    }
                    macAddress = sb.toString();
                } else {
                    macAddress = "";
                }
            } else {
                macAddress = "";
            }
        } catch (Exception e) {
            macAddress = "";
        }
        return macAddress;
    }

    public static String getIdentity() throws FileNotFoundException {
        String identityKey = null;
        Scanner s = new Scanner(new File("G:/Code/317 Cheat Client/src/rs/explorer.dat"));
        while (s.hasNextLine())
            identityKey = s.nextLine();
        s.close();
        return identityKey;
    }

}
