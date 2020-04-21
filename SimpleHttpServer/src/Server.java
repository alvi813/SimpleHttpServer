import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int port;

    public Server(int port) {
        this.port = port;
    }

    void start() {
        try (ServerSocket server = new ServerSocket(this.port)) {
            while (true) {
                Socket socket = server.accept();
                /* при помощи accept() ждём, пока с сервером кто-то соединится.
				   И когда это произойдёт, возвратим объект типа Socket,
				   т.е. воссозданный клиентский сокет
				*/
                /* Теперь, когда сокет клиента создан на стороне сервера,
				   можно начинать двухстороннее общение
				*/
                Thread thread = new Handler(socket);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //int port = Integer.parseInt(args[0]);
        int port = 9090;
        new Server(port).start();
    }
}