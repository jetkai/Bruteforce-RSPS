package aniski.rs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Store {

    private String ipAddress = "54.39.130.9";
    private int port = 43594;

    public static int currentKey = 0;

    private static int totalPasswordsLoaded;

    private final ArrayList<String> storedPasswords = new ArrayList<>();
    private final ArrayList<String> storedUsers = new ArrayList<>();

    private static String rspsToCrack;

    /**
     * Loads the Password.lst in the RAM
     * Used for RSPS Password Cracking
     */

    public void generatePassword(String passwordFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(passwordFile));
            String sCurrentLine;
            int lines = 0;
            while ((sCurrentLine = br.readLine()) != null) {
                getStoredPasswords().add(sCurrentLine);
                lines++;
            }
            br.close();
            System.out.println("Successfully loaded "+lines+" passwords from the file "+passwordFile+".");
        } catch (IOException ignored) {
            System.out.println("Error loading file: "+passwordFile);
            //e.printStackTrace();
        }
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static void setRspsToCrack(String rspsToCrack) {
        Store.rspsToCrack = rspsToCrack;
    }

    public void setTotalPasswordsLoaded(int totalPasswordsLoaded) {
        Store.totalPasswordsLoaded = totalPasswordsLoaded;
    }

    public ArrayList<String> getStoredUsers() {
        return storedUsers;
    }

    public static int getTotalPasswordsLoaded() {
        return totalPasswordsLoaded;
    }

    public ArrayList<String> getStoredPasswords() {
        return storedPasswords;
    }

    public int getPort() {
        return port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public static String getRspsToCrack() {
        return rspsToCrack;
    }

}
