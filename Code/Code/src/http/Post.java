package http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Post {
    public static void main(String[] args) {
        try {
            String boundary = "Boundary-b1ed-4060-99b9-fca7ff59c113"; //Could be any string  
            String Enter = "\r\n";

            File xml = new File("C:\\dog.xml");
            FileInputStream fis = new FileInputStream(xml);

            URL url = new URL("http://localhost/fsly_service/api/hk/receiveXMLResult");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            conn.connect();

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            //part 1  
            String part1 = "--" + boundary + Enter + "Content-Type: application/octet-stream" + Enter
                    + "Content-Disposition: form-data; filename=\"" + xml.getName() + "\"; name=\"file\"" + Enter + Enter;
            //part 2  
            String part2 = Enter + "--" + boundary + Enter + "Content-Type: text/plain" + Enter
                    + "Content-Disposition: form-data; name=\"dataFormat\"" + Enter + Enter + "hk" + Enter + "--" + boundary
                    + "--";

            byte[] xmlBytes = new byte[fis.available()];
            fis.read(xmlBytes);

            dos.writeBytes(part1);
            dos.write(xmlBytes);
            dos.writeBytes(part2);

            dos.flush();
            dos.close();
            fis.close();

            System.out.println("status code: " + conn.getResponseCode());

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
