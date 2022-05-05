import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketOption;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerHW {
    private int port = 8080;
    private int count = 1;
    private LinkedHashMap<String,String> mapParameters = new LinkedHashMap<String,String>();
    private LinkedHashMap<String,String> mapOut = new LinkedHashMap<String, String>();
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(this.port);
        System.out.println("Server has started");
        while (true) {
            System.out.println("Server готов к приему заявки № " + count +".");
            Socket socket = serverSocket.accept();
            //String getParams = socket.getOption();dsc
            InputStream inputStream = socket.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            String text = new String(bufferedInputStream.readAllBytes());
            System.out.println("Server has gotten this : " + text);
            processing(text);

            System.out.println("Server закончил ответ на заявку № " + count++ +".");
        }

//        System.out.println("Server has stopped");
//        serverSocket.close();

    }
    private void processing(String text){
        //разбивка по строкам
        if (text=="") return;
        String[] str1 = (text.split("\n"));
        //String[] str1 = (text.split(" "));
        for (int i=0; i< str1.length; i++) {
            System.out.println("" + i + " = " + str1[i]);
        }
        //разбивка 0 строки по " "
        int indexSpace = str1[0].indexOf(" ");
        int indexSpaceLast = str1[0].lastIndexOf(" ");
        String str2 = str1[0].substring(0,indexSpace);
        //System.out.println("str2 = " + str2);
        switch (str2){
            case "GET":
                        // +2 для "/?"
                        str2 =  str1[0].substring(indexSpace+3, indexSpaceLast) + "&";
                        System.out.println("str2 = " + str2);
                        //Pattern r = Pattern.compile("[a-z]+=[a-z]+");
                        //Matcher m = r.matcher(str2);
                        //System.out.println("Пришел GET запрос.  m = " + m.matches());
                        //String[] parameters = str2.split("[a-z]+=[a-z]+");
                        String[] parameters = str2.split("&");
                        //String[] parameters = m.pattern().split("&");
                        //System.out.println("Пришел GET запрос. Параметры 0= " + parameters[0]);
                        //System.out.println("Пришел GET запрос. Параметры 1= " + parameters[1]);
                        //String[] kv = new String[1];
                        this.mapParameters.clear();
                        for (String pars: parameters){
                            String[] kv = pars.split("=");
                            this.mapParameters.put(kv[0],kv[1]);
                            //System.out.println("kv[0] = " + kv[0]);
                            //System.out.println("kv[1] = " + kv[1]);
                        }
                        System.out.println("Пришел GET запрос. Параметры итого = " + mapParameters);
                        this.mapOut.clear();
                        this.mapOut.putAll(mapParameters);
                        break;
        }
    }

    public ServerHW(int port) {
        this.port = port;
        this.count=1;
    }
}
