package aniski.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.util.ArrayList;

public class SHA1Hash {

    private final ArrayList<String> passwordsPlainText = new ArrayList<>();
    private final ArrayList<String> passwordsHashed = new ArrayList<>();
    private final String passwordFilePlainText = "lists/stoned/11M PT 2.txt";
    private final String passwordFileHashed = "lists/stoned/11M PT 2 Hashed.txt";

    public static void main(String[] args) {
        SHA1Hash sha1Hash = new SHA1Hash();
        sha1Hash.hashPasswords();
    }

    public void hashPasswords() {
        //BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
        //Reads Passwords as Plain Text
        getPlainTextPasswordsFromFile();
        //Hashes the passwords with SHA1
       // int size = passwordsPlainText.size();
        for (String password : passwordsPlainText) {
            /* String password2 = bCryptPasswordEncoder.encode(password);
            passwordsHashed.add(password2);
            System.out.println(password+":"+password2 + ":"+ System.currentTimeMillis());*/
            passwordsHashed.add(DigestUtils.sha512Hex(password));
            //System.out.println(i + "/" + size);
        }

        //System.out.println(passwordsHashed.indexOf("ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff"));

        writeArrayToFile(passwordsHashed);
        System.out.println("Total Hashed: "+passwordsHashed.size());
        System.out.println("First Password: "+passwordsHashed.get(0));
    }

    private void getPlainTextPasswordsFromFile() {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(passwordFilePlainText))) {
                String line;
                while ((line = br.readLine()) != null)
                    passwordsPlainText.add(line);
                // System.out.println(Arrays.toString(passwords.toArray()));
            }
            System.out.println("Loaded a total of " + passwordsPlainText.size() + " passwords.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeArrayToFile(ArrayList<String> passwordArrayList) {
        try (FileWriter fw = new FileWriter(passwordFileHashed, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for(String password : passwordArrayList)
                out.println(password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
