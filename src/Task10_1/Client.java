package Task10_1;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 * Client
 * Клиентская сторона приложения -  многопользовательский чат,
 * в котором участвует произвольное количество клиентов.
 * Каждый клиент после запуска отправляет свое имя серверу.
 * После чего начинает отправлять ему сообщения.
 * Каждое сообщение сервер подписывает именем клиента и рассылает всем клиентам (broadcast).
 *
 * created by Ksenya_Ushakova at 20.05.2020
 */
public class Client{
    private Scanner sc;
    private DatagramSocket clientSocket;
    private static final int PORT = 8085;
    private InetAddress ip;
    private static final int SERVER_PORT = 8888;

    /**
     * Публичный конструктор
     * @throws SocketException
     * @throws UnknownHostException
     */
    public Client() throws SocketException, UnknownHostException {
        clientSocket = new DatagramSocket(PORT);
        sc = new Scanner(System.in);
        clientSocket.setBroadcast(true);
        ip = InetAddress.getByName("localhost");
    }

    /**
     * Главный метод
     * Запускает нового клиента
     * @param args
     */
    public static void main(String[] args) {

        Client client = null;
        try {
            client = new Client();
            client.start();
        } catch (SocketException | UnknownHostException e) {
            System.err.println("Не удается запустить клиент");
            e.printStackTrace();
            client.clientSocket.close();
        }


    }

    /**
     * Метод создает потоки для приема и получения сообщений
     */
    public void start() {
        ClientSender sender = new ClientSender();
        ClientReceiver receiver = new ClientReceiver();
        sender.start();
        receiver.start();
    }

    /**
     * Класс - отправитель сообщений
     */
    class ClientSender extends Thread{
        public void run(){
            System.out.println("Введите имя: ");
            while (true) {
                String clientMsg = sc.nextLine();
                byte[] sendMsg = clientMsg.getBytes();
                DatagramPacket sendPacket = null;
                try {
                    sendPacket = new DatagramPacket
                            (sendMsg, 0, sendMsg.length, ip, SERVER_PORT);
                    clientSocket.send(sendPacket);
                } catch (IOException e) {
                    System.err.println("Не удается отправить пакет");
                    e.printStackTrace();
                    clientSocket.close();
                }
            }
        }
    }

    /**
     * Класс - получатель сообщений
     */
    class ClientReceiver extends Thread{
        public void run(){
            while (true) {
                byte[] receiveMsg = new byte[5000];
                DatagramPacket receivePacket = new DatagramPacket(receiveMsg, receiveMsg.length);
                System.out.println(new String(receivePacket.getData()));
                try {
                    clientSocket.receive(receivePacket);
                } catch (IOException e) {
                    System.out.println("Не удалось получить пакет");
                    e.printStackTrace();
                    clientSocket.close();
                }
                String serverMsg = new String(receivePacket.getData());
                System.out.println("Получено: " + serverMsg);
            }
        }
    }
}
