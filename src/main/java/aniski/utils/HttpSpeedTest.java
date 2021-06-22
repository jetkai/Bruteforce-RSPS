package aniski.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class HttpSpeedTest {

    public static void main(String[] args) {
        Date timeStart = new Date();
        try {
            new HttpSpeedTest().javaHttpSpeedTest(timeStart);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*
    private void apacheHttpSpeedTest(Date timeStart) throws IOException {
        //Creating a HttpClient object
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //Creating a HttpGet object
        HttpGet httpget = new HttpGet("http://149.56.98.56/proxy/proxy-list.json");
        //Printing the method used
        System.out.println("Request Type: "+httpget.getMethod());
        //Executing the Get request
        HttpResponse httpresponse = httpclient.execute(httpget);
        Scanner sc = new Scanner(httpresponse.getEntity().getContent());
        //Printing the status line
        System.out.println(httpresponse.getStatusLine());
        while(sc.hasNext())
            System.out.println(sc.nextLine());
        Date timeFinish = new Date();
        System.out.println("Seconds: "+getTotalTime(timeStart, timeFinish));
    }
*/

    private void javaHttpSpeedTest(Date timeStart) throws IOException {
        StringBuilder proxiesJson = new StringBuilder();
            URL api = new URL("http://149.56.98.56/proxy/proxy-list.json");
            HttpURLConnection con = (HttpURLConnection) api.openConnection();
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.setRequestProperty ( "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0" );
            con.setUseCaches(false);
            InputStream ins = con.getInputStream();
            InputStreamReader isr = new InputStreamReader(ins);
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                proxiesJson.append(inputLine).append(System.lineSeparator());
            in.close();
        System.out.println(proxiesJson);
        Date timeFinish = new Date();
        System.out.println("Seconds: "+getTotalTime(timeStart, timeFinish));
    }

    private long getTotalTime(Date startDate, Date endDate) {
        Long longTime = startDate.getTime() / 1000;
        Long minusTime = endDate.getTime() / 1000;
        return Math.abs(longTime-minusTime);
    }
}
