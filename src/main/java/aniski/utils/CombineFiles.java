package aniski.utils;

import java.io.*;
import java.util.ArrayList;

public class CombineFiles {

    private final ArrayList<String> hashList = new ArrayList<>();

    public static void main(String[] args) {
        CombineFiles combineFiles = new CombineFiles();
        try {
            combineFiles.hashPasswords();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void hashPasswords() throws InterruptedException {
        String[] listPaths = new String[]{"lists/stoned/11M PT 1 Hashed.txt", "lists/stoned/11M PT 2 Hashed.txt"};
        for(String listPath : listPaths)
            getPlainTextPasswordsFromFile(listPath);

        String finalHashedListPath = "lists/stoned/22M Hashed.txt";
        writeArrayToFile(hashList, finalHashedListPath);

        System.out.println("Total Hashed: "+hashList.size());
        System.out.println("First Password: "+hashList.get(0));
    }

    private void getPlainTextPasswordsFromFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null)
                hashList.add(line);
        } catch (Exception ignored) {
            //e.printStackTrace();
        }
        System.out.println("Loaded a total of " + hashList.size() + " passwords.");
    }

    private void writeArrayToFile(ArrayList<String> passwordArrayList, String passwordFileHashed) {
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
