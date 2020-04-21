import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Date;

public class Handler extends Thread {
    private Socket socket;

    Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream input = this.socket.getInputStream(); OutputStream output = this.socket.getOutputStream()) {

            String response = "<html><body><a href=\"https://yandex.ru\">Click here</a></body></html>";
            String type = "text/html; charset=utf-8";
            this.sendHeader(output, 200, "OK", type, response.length());
            output.write(response.getBytes());
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendHeader(OutputStream output, int statusCode, String statusText, String type, long length) {
        PrintStream ps = new PrintStream(output);
        ps.printf("HTTP/1.1 %d %s \r\n", statusCode, statusText);
        ps.printf("Content-Type: %s \r\n", type);
        ps.print("Date: " + new Date() + "\r\n");
        ps.print("Server: MyServer \r\n");
        ps.printf("Content-Length: %d \r\n", length);
        ps.print("Connection: close \r\n\r\n");
    }
}
