import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        int serverPort = 8080;
        ServerHW serverHW = new ServerHW(serverPort);
        serverHW.start();
    }
}
