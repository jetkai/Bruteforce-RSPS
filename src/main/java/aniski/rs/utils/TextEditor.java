package aniski.rs.utils;

import aniski.rs.Store;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class TextEditor {

    public static void writePasswordToFile(String username, String password, String twoFactor) {
        try {
            FileWriter fileWriter = new FileWriter(username+".txt", true);
            BufferedWriter out = new BufferedWriter(fileWriter);
            if(twoFactor == null || twoFactor.length() < 1)
                out.write("#PASSWORD SUCCESSFULLY CRACKED#");
            else
                out.write("#PASSWORD SUCCESSFULLY CRACKED (WARNING: HAS 2FA - "+twoFactor+")#");
            out.newLine();
            out.write("Username: "+username);
            out.newLine();
            out.write("Password: "+password);
            out.newLine();
            if(twoFactor == null || twoFactor.length() < 1)
                out.write("#PASSWORD SUCCESSFULLY CRACKED#");
            else
                out.write("#PASSWORD SUCCESSFULLY CRACKED (WARNING: HAS 2FA - "+twoFactor+")#");
            out.newLine();
            out.newLine();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCommandToFile(String action) {
        try (FileWriter fw = new FileWriter("G:/Code/Javacord-master/command.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(action);
        } catch (IOException e) {
            e.printStackTrace();
            //exception handling left as an exercise for the reader
        }
    }

    public static void writeToFile(String filePath, String content) {
        try (FileWriter fw = new FileWriter(filePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
            //exception handling left as an exercise for the reader
        }
    }

    public static String fileToString(String filePath) throws Exception{
        String input;
        Scanner sc = new Scanner(new File(filePath));
        StringBuilder sb = new StringBuilder();
        while (sc.hasNextLine()) {
            input = sc.nextLine();
            sb.append(input).append("\n");
        }
        sc.close();
        return sb.toString();
    }

    public static void removeUserFromFile(String username) {
        try {
            String filePath = "players.txt";
            String result = fileToString(filePath);
            System.out.println("Contents of the file: " + result);
            //Replacing the word with desired one
            result = result.replaceAll(username+"\n", "");
            //Rewriting the contents of the file
            PrintWriter writer = new PrintWriter(filePath);
            writer.append(result);
            writer.flush();
            System.out.println("Contents of the file after replacing the desired word:");
            System.out.println(fileToString(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getUsernameFromFile(Store store) {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("players.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if(!store.getStoredUsers().contains(line))
                        store.getStoredUsers().add(line);
                }
                System.out.println(Arrays.toString(store.getStoredUsers().toArray()));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
