package assignment02.csc214.project3.Item;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kennedy on 4/24/2017.
 */

public class HttpHelper {
    public byte[] getBytes(String spec) throws IOException {
        URL url = new URL(spec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        InputStream in = connection.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int n;
        byte[] buf = new byte[1024];
        while((n = in.read(buf)) > 0) {
            out.write(buf, 0, n);
        }
        out.close();
        return out.toByteArray();
    }

    public String getString(String spec) throws IOException {
        return new String(getBytes(spec));
    }
}
