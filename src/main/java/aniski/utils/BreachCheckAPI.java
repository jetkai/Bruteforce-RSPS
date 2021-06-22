package aniski.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author jet kai
 * @version 4_24042021
 */

public class BreachCheckAPI {

    private String password = "password123"; //CHANGE ME
    private String hashType = "SHA-1"; //MD5, SHA-1, SHA-256 & PLAIN-TEXT

    private final String apiUrl = "http://api.rsps.tools/jetkai/breachcheck"; //DO NOT CHANGE
    private final String token = "39439e74fa27c09a4"; //DO NOT CHANGE

    private String returnedJson = ""; //Data that is returned from the api

    public static void main(String[] args) {
        BreachCheckAPI breachAPI = new BreachCheckAPI();
        breachAPI.initExample();
    }

    /**
     *
     */
    public void initExample() {
        String checkField = checkFields();
        if (checkField.length() > 0) { //Checks if password or token field is empty
            System.err.println(checkField);
            return;
        }
        //setPassword("password123"); <-- You can set from another class, before calling isBreached()
        //setHashType("SHA-1"); <-- You can set from another class, before calling isBreached()
        boolean isBreached = isBreached();
        boolean hasReturnedJson = getReturnedJson().length() > 0;
        if(isBreached && hasReturnedJson) {
            System.err.println("You have been breached : " + getReturnedJson()); //Outputs JSON to console
        } else if(!isBreached && hasReturnedJson) {
            System.out.println("You have not been breached : " + getReturnedJson()); //Outputs JSON to console
        }
    }

    /**
     * Sends HTTP Request to return data
     * @return The data from HTTP Request and checks if it contains "breached":true
     */
    public boolean isBreached() {
        connect(); //HTTP Request - NOT HTTPS
        return getReturnedJson().contains("\"breached\":true"); //Extremely basic check, use JSON Parser if needed
    }

    /**
     * Sends HTTP Request to set the "returnedJson" data
     *
     * URL Request Example:
     * http://api.rsps.tools/jetkai/breachcheck?token=39439e74fa27c09a4&hash=cbfdac6008f9cab4083784cbd1874f76618d2a97
     *
     * Returned JSON Data Example:
     * {
     *    "token":"39439e74fa27c09a4",
     *    "hash":"cbfdac6008f9cab4083784cbd1874f76618d2a97",
     *    "databaseBreach":"Stoned 2021 ~800K Unique Passwords (15+ RSPS Databases)",
     *    "hashType":"SHA-1",
     *    "breached":true
     * }
     */

    public void connect() {
        try {
            URL url = new URL(getApiUrl() +
                    "?token=" + getToken() +
                    "&" + getHashOrPassword() + "=" + getHashedPassword());

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0");

            InputStream ins = con.getInputStream();
            InputStreamReader isr = new InputStreamReader(ins);
            BufferedReader bin = new BufferedReader(isr);

            setReturnedJson(bin.readLine());

            bin.close();
        } catch (IOException io) {
            System.err.println(io.getMessage());
        }
    }

    /**
     * Hex's the plain-text password, either using:
     * MD5, SHA-1, SHA256
     * @return The hexed password, fallsback to plain-text if an incorrect hashType is set
     */
    private String getHashedPassword() {
        switch (getHashType().toUpperCase()) {
            case "MD5":
                return DigestUtils.md5Hex(getPassword());
            case "SHA-1":
                return DigestUtils.sha1Hex(getPassword());
            case "SHA-256":
                return DigestUtils.sha256Hex(getPassword());
        }
        return getPassword(); //PLAIN-TEXT
    }

    /**
     * This is checking the hashType and returning as string
     * @return Either "hash" or "password", depending if the password is plain-text or hexed
     */
    private String getHashOrPassword() {
        String[] hashTypes = new String[]{"MD5", "SHA-1", "SHA-256"};
        for(String hashType : hashTypes) {
            if(hashType.equalsIgnoreCase(getHashType()))
                return "hash"; //Used for MD5, SHA-1 & SHA-256
        }
        return "password"; //Used for PLAIN-TEXT
    }

    /**
     * Checks Fields
     * @return The string output if the password /or token field is null/empty
     */
    private String checkFields() {
        if(getPassword() == null || getPassword().length() == 0)
            return "Password field can't be empty.";
        else if(getToken() == null || getToken().length() == 0)
            return "Token field can't be empty.";
        return "";
    }

    /**
     * Sets the password or hash that would be checked, before isBreached() is called
     * @param password - password123 : cbfdac6008f9cab4083784cbd1874f76618d2a97
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the hashType that is needed for the HTTP Request to either request:
     * /breachcheck?hash= (for MD5, SHA-1 & SHA-256) or /breachcheck?password= (for plain-text)
     * @param hashType - MD5, SHA-1, SHA-256, PLAIN-TEXT
     */
    public void setHashType(String hashType) {
        this.hashType = hashType;
    }

    /**
     * Sets the returnedJson string, used in connect()
     * @param returnedJson
     */

    public void setReturnedJson(String returnedJson) {
        this.returnedJson = returnedJson;
    }

    /**
     * Gets the password as string
     * @return The hash or password
     */

    public String getPassword() {
        return password;
    }

    /**
     * Gets the token as string
     * @return The token used for the API request
     */

    public String getToken() {
        return token;
    }

    /**
     * Gets the API Url as string
     * @return The URL before params are added - http://api.rsps.tools/jetkai/breachcheck
     */

    public String getApiUrl() {
        return apiUrl;
    }

    /**
     * Gets the hashType as string
     * @return MD5, SHA-1, SHA-256 or PLAIN-TEXT - depending on the data inside getPassword()
     */

    public String getHashType() {
        return hashType;
    }

    /**
     *
     * @return
     */

    public String getReturnedJson() {
        return returnedJson;
    }
}
