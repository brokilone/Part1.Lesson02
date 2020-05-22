package Task10_1;


import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Server
 * Серверная сторона приложения -  многопользовательский чат,
 * в котором участвует произвольное количество клиентов.
 * Каждый клиент после запуска отправляет свое имя серверу.
 * После чего начинает отправлять ему сообщения.
 * Каждое сообщение сервер подписывает именем клиента и рассылает всем клиентам (broadcast).
 * created by Ksenya_Ushakova at 20.05.2020
 */


public class Server {

    private Scanner sc;
    private DatagramSocket serverSocket;
    private final static int PORT = 8888;
    private Map<InetAddress, String> clientMap;

    /**
     * публичный конструктор
     * @throws SocketException
     * @throws UnknownHostException
     */
    public Server() throws SocketException, UnknownHostException {
        sc = new Scanner(System.in);
        serverSocket = new DatagramSocket(PORT, InetAddress.getByName("localhost"));
        serverSocket.setBroadcast(true);
        clientMap = new HashMap<>(); //для сохранения имени клиента по ip

    }

    /**
     * Главный метод
     * Запускает сервер
     * @param args
     */
    public static void main(String[] args) {
        Server server = null;
        try {
            server = new Server();
            server.start();
        } catch (SocketException| UnknownHostException e) {
            System.err.println("Не удается запустить сервер");
            e.printStackTrace();
        } catch (IOException e){
            System.err.println("Ошибка пересылки пакета");
            e.printStackTrace();
        } finally {
            if (server != null) {
                server.serverSocket.close();
            }
        }


    }

    /**
     * Метод обеспечивает получение сообщений от клиентов,
     * если клиент отправил первое сообщение (в мапе его ip нет),
     * сервер считывает это сообщение как имя клиента и сохраняет его по ключу ip в мапе
     * Последущие сообщения от клиента будут подписыватсья его именем
     * @throws IOException
     */
    public void start() throws IOException {
        String s = "";

        while (true) {
            byte[] receiveMsg = new byte[1000];
            DatagramPacket receivePacket = new DatagramPacket(receiveMsg, receiveMsg.length);

            serverSocket.receive(receivePacket);

            InetAddress ip = receivePacket.getAddress();

            if (!clientMap.containsKey(ip)) {
                storeNewClient(receivePacket, ip);
            } else {
                prepareMsg(receivePacket, ip);
            }

        }
    }

    /**
     * Метод сохраняет имя клиента в коллекции hashmap по ключу. Ключом выступает ip отправителя
     * После сохранения сервер отправляет broadcast сообщение всем подключенным пользователям о подключении
     * нового клиента
     * @param receivePacket полученный фрейм
     * @param ip ip адрес отправителя
     * @throws IOException
     */
    private void storeNewClient(DatagramPacket receivePacket, InetAddress ip) throws IOException {
        String clientName = new String(receivePacket.getData());
        clientMap.put(ip, clientName);
        String greeting = "Подключен новый пользователь: " + clientName;
        broadcast(greeting);
    }

    /**
     * Метод получает имя отправителя по ip из hashmap и добавляет имя к сообщению,
     * затем запускает broadcast рассылку всем подключенным пользователям
     * @param receivePacket полученный фрейм
     * @param ip ip адрес отправителя
     * @throws IOException
     */
    private void prepareMsg(DatagramPacket receivePacket, InetAddress ip) throws IOException {
        String clientName = clientMap.get(ip);
        String msg = new String(receivePacket.getData());
        String msgWithName = clientName + ": " + msg;
        broadcast(msgWithName);

    }

    /**
     * Широковещательная рассылка сообщений
     * @param clientMsg текст сообщения
     * @throws IOException
     */
    private void broadcast(String clientMsg) throws IOException {

        byte[] sendMsg = clientMsg.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendMsg, sendMsg.length, InetAddress.getByName("255.255.255.255"), 8085);
        serverSocket.send(sendPacket);
    }

}






