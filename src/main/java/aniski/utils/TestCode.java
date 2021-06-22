package aniski.utils;

import org.json.JSONObject;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class TestCode {


    public static void main(String[] args) {
        TestCode testCode = new TestCode();
        try {
            testCode.initOCR();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initOCR() throws Exception {
        String filePath = "C:\\Users\\Kai\\Documents\\ShareX\\Screenshots\\2021-01\\LqcU8TnSuk.png";
        String extension = "";
        int i = filePath.lastIndexOf('.');
        if (i > 0)
            extension = filePath.substring(i + 1);
        BufferedImage image = scaleImage(filePath); // Increases size by 3x && Turns image into black & white
        String base64 = "data:image/"+extension+";base64,"+ encodeToString(image, extension);
        System.out.println(getOCRResults(base64));
    }

    /**
     * Increases the size of the image, so the OCR can read it much better
     */
    private BufferedImage scaleImage(String filePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(filePath));
        final int w = image.getWidth();
        final int h = image.getHeight();
        BufferedImage scaledImage = new BufferedImage((w * 3),(h * 3), BufferedImage.TYPE_INT_ARGB);
        final AffineTransform at = AffineTransform.getScaleInstance(3.0, 3.0);
        final AffineTransformOp ato = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        scaledImage = ato.filter(image, scaledImage);
        ImageIO.write(scaledImage, "PNG", new File("finish.png")); //Outputs a preview to see if it looks okay
        return scaledImage;
    }


    /**
     * Converts the Image to Text using the OCR API on the web
     * @param base64
     * @return
     * @throws Exception
     */
    private String getOCRResults(String base64) throws Exception {

        URL obj = new URL("https://api.ocr.space/parse/image"); // OCR API Endpoints
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        JSONObject postDataParams = new JSONObject();

        postDataParams.put("apikey", "a41e8e316b88957");
        postDataParams.put("isOverlayRequired", false);
        postDataParams.put("base64Image", base64);
        postDataParams.put("language", "eng");
        postDataParams.put("OCREngine", 2);

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(getPostDataString(postDataParams));

        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);

        in.close();

        JSONObject jsonObject = new JSONObject(String.valueOf(response));
        return jsonObject.getJSONArray("ParsedResults").getJSONObject(0).getString("ParsedText"); //Grabs the output I want from the API
    }

    /**
     * Encodes Image to Base64 (direct upload to the API instead of using boring links)
     * @param image
     * @param type
     * @return
     */
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageString;
    }


    /**
     * Returns the post data, returned from the API
     * @param params
     * @return
     * @throws Exception
     */
    public String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {
            String key = itr.next();
            Object value = params.get(key);
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }

        return result.toString();
    }

}
