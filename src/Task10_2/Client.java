package Task10_2;

import java.io.IOException;
import java.net.*;
import java.util.Random;
import java.util.Scanner;

/**
 * Client
 * Клиентская сторона приложения -  многопользовательский чат,
 * в котором участвует произвольное количество клиентов.
 * Каждый клиент после запуска отправляет свое имя серверу.
 * После чего начинает отправлять ему сообщения.
 * Каждое сообщение сервер подписывает именем клиента и рассылает всем клиентам (broadcast).
 *
 * Усовершенствовать задание 1:
 * добавить возможность отправки личных сообщений (unicast).
 * добавить возможность выхода из чата с помощью написанной в чате команды «quit»
 *
 * created by Ksenya_Ushakova at 20.05.2020
 */
public class Client{
    private Scanner sc;
    private DatagramSocket clientSocket;
    //private static final int PORT = 8085;
    private static final int PORT = new Random().nextInt(50)+8000;//чтобы затестить параллельный запуск на разных портах
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

        Client client;
        try {
            client = new Client();
            client.start();
        } catch (SocketException | UnknownHostException e) {
            System.err.println("Не удается запустить клиент");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    /**
     * Метод создает потоки для приема и получения сообщений
     * Если клиент выходит из чата, сокет закрывается
     */
    public void start() throws InterruptedException {
        ClientSender sender = new ClientSender();
        ClientReceiver receiver = new ClientReceiver();
        sender.start();
        receiver.start();
        sender.join();//ждем ввода команды exit
        receiver.isOn = false;//завершаем второй поток - поток отправления
        clientSocket.close();
    }

    /**
     * Класс - отправитель сообщений
     * Если клиент вводит exit,
     * поток прекращает работу
     */
    class ClientSender extends Thread{
        public void run(){
            System.out.println("Чтобы отправлять сообщения, укажите логин.\n" +
                    "После этого вы сможете отправлять сообщения в общий чат." +
                    "Чтобы отправить личное сообщение, введите -u");
            System.out.println("Введите логин: ");
            while (true) {
                String clientMsg = sc.nextLine();
                byte[] sendMsg = clientMsg.getBytes();
                DatagramPacket sendPacket;
                try {
                    sendPacket = new DatagramPacket
                            (sendMsg, 0, sendMsg.length, ip, SERVER_PORT);
                    clientSocket.send(sendPacket);
                } catch (IOException e) {
                    System.err.println("Не удается отправить пакет");
                    e.printStackTrace();
                    clientSocket.close();
                }
                if (clientMsg.equalsIgnoreCase("quit")) {
                    break;
                }

            }

        }
    }

    /**
     * Класс - получатель сообщений
     * если поток отправитель завершает работу,
     * переменная isOn установится в false и поток-получатель завершится
     */
    class ClientReceiver extends Thread{
        private boolean isOn = true;
        public void run(){
            while (isOn) {

                byte[] receiveMsg = new byte[5000];
                DatagramPacket receivePacket = new DatagramPacket(receiveMsg, receiveMsg.length);
                System.out.println(new String(receivePacket.getData()));
                try {
                    clientSocket.receive(receivePacket);
                } catch (IOException e) {
                    if (!isOn) {
                        break;
                    }
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
