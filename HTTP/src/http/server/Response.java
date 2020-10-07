package http.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Response {

    private HashMap<String, String> headers;

    public Response() {
        this.headers = new HashMap<>();
    }

    void setHeader(String key, String content) {
        this.headers.put(key, content);
    }

    void send(OutputStream out, String status, Byte[] content) {

        PrintWriter writer = new PrintWriter(out);

        // Send the HTML page
        try {
            // Send the response
            // Send the headers
            out.write(("HTTP/1.0 "+status+"\r\n").getBytes());

            for (Map.Entry<String, String> header : headers.entrySet()) {
                out.write((header.getKey() + ": "+header.getValue()+"\r\n").getBytes());
            }

            // this blank line signals the end of the headers
            out.write("\r\n".getBytes());

            if (content != null) {
                int i = 0;
                byte[] primitiveBytes = new byte[content.length];
                for (Byte b : content) {
                    primitiveBytes[i++] = b.byteValue();
                }

                out.write(primitiveBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        writer.flush();
    }

}
