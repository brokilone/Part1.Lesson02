package Task10_2;


import javafx.util.Pair;

import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * Server
 * Серверная сторона приложения -  многопользовательский чат,
 * в котором участвует произвольное количество клиентов.
 * Каждый клиент после запуска отправляет свое имя серверу.
 * После чего начинает отправлять ему сообщения.
 * Каждое сообщение сервер подписывает именем клиента и рассылает всем клиентам (broadcast).
 * created by Ksenya_Ushakova at 20.05.2020
 * <p>
 * Усовершенствовать задание 1:
 * добавить возможность отправки личных сообщений (unicast).
 * добавить возможность выхода из чата с помощью написанной в чате команды «quit»
 */


public class Server {

    private Scanner sc;
    private DatagramSocket serverSocket;
    private final static int PORT = 8888;
    private Map<Pair<InetAddress, Integer>, String> clientMap;


    /**
     * публичный конструктор
     *
     * @throws SocketException
     * @throws UnknownHostException
     */
    public Server() throws SocketException, UnknownHostException {
        sc = new Scanner(System.in);
        serverSocket = new DatagramSocket(PORT, InetAddress.getByName("localhost"));
        serverSocket.setBroadcast(true);
        clientMap = new HashMap<>(); //для сохранения имени клиента по ip+port
    }

    /**
     * Главный метод
     * Запускает сервер
     *
     * @param args
     */
    public static void main(String[] args) {
        Server server = null;
        try {
            server = new Server();
            server.start();
        } catch (SocketException | UnknownHostException e) {
            System.err.println("Не удается запустить сервер");
            e.printStackTrace();
        } catch (IOException e) {
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
     * если клиент отправил первое сообщение (в мапе его ip+port нет),
     * сервер считывает это сообщение как имя клиента и сохраняет его по ключу ip в мапе
     * Последущие сообщения от клиента будут подписываться его именем
     *
     * @throws IOException
     */
    public void start() throws IOException {

        while (true) {
            byte[] receiveMsg = new byte[1000];
            DatagramPacket receivePacket = new DatagramPacket(receiveMsg, receiveMsg.length);

            serverSocket.receive(receivePacket);

            int port = receivePacket.getPort();
            InetAddress ip = receivePacket.getAddress();
            Pair data = new Pair(ip, port);

            if (!clientMap.containsKey(data)) {
                storeNewClient(receivePacket, data);
            } else {
                prepareMsg(receivePacket, data);
            }

        }
    }

    /**
     * Метод сохраняет имя клиента в коллекции hashmap по ключу. Ключом выступает ip отправителя
     * Перед сохранением проверяется, не занято ли имя другим юзером. Если занято, предлагается использовать
     * другой логин
     * После сохранения сервер отправляет broadcast сообщение всем подключенным пользователям о подключении
     * нового клиента
     * @param receivePacket полученный фрейм
     * @param data - пара ip-адрес+порт отправителя
     * @throws IOException
     */
    private void storeNewClient(DatagramPacket receivePacket, Pair<InetAddress, Integer> data) throws IOException {
        String clientName = new String(receivePacket.getData()).trim();
        if (clientMap.containsValue(clientName)) {
            byte[] sendMsg = "Login занят, попробуйте другой".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendMsg, sendMsg.length, data.getKey(),data.getValue());
            serverSocket.send(sendPacket);
        } else {
            clientMap.put(data, clientName);
            String greeting = "Подключен новый пользователь: " + clientName;
            broadcast(greeting);
        }
    }

    /**
     * Метод анализирует сообщение и выбирает дальнейший режим отправки
     *
     * @param receivePacket полученный фрейм
     * @param data - ip адрес и порт отправителя
     * @throws IOException
     */
    private void prepareMsg(DatagramPacket receivePacket, Pair<InetAddress, Integer> data)
                            throws IOException {
        String clientName = clientMap.get(data);
        String msg = new String(receivePacket.getData()).trim();

        if (msg.equals("-u")) {//клиент запрашивает список доступных адресатов
            sendListOfUsers(data);

        } else if (msg.startsWith("-u")) { //клиент запрашивает unicast конкретному юзеру
            String[] words = msg.split("\\s");
            String unimsg = clientName + ": " + msg.substring(msg.indexOf(words[1]) + words[1].length());
            try {
                String name = words[1];
                Pair<InetAddress, Integer> userData;
                for (Map.Entry<Pair<InetAddress, Integer>, String> client : clientMap.entrySet()) {
                    if (client.getValue().equals(name)) {
                        userData = client.getKey();
                        unicast(userData, unimsg);
                        break;
                    }
                }
            } catch (Exception e) {
                unicast(data, "Неверный запрос");//если не удалось распарсить id
            }

        } else if (msg.equalsIgnoreCase("quit")) {//пользователь вышел из чата
            String exitmsg = clientName + " покинул чат";
            broadcast(exitmsg);//сообщаем об этом юзерам
        } else {
            String msgWithName = clientName + ": " + msg; //обычный режим
            broadcast(msgWithName);
        }
    }

    /**
     * Метод формирует список доступных адресатов и отправляет на ip, который запросил данные
     * @param data ip+port отправителя запроса
     * @throws IOException
     */
    private void sendListOfUsers(Pair<InetAddress, Integer> data) throws IOException {
        StringBuilder users = new StringBuilder("Доступные пользователи:\n");
        for (String name : clientMap.values()) {
            users.append(name).append("\n");
        }

        users.append("Введите -u, логин получателя и сообщение:\n");
        unicast(data, users.toString());
    }

    /**
     * Метод для отправки сообщения в режиме unicast
     * @param data - ip+port получателя
     * @param unimsg сообщение
     * @throws IOException
     */
    private void unicast(Pair<InetAddress, Integer> data, String unimsg) throws IOException {
        byte[] sendMsg = unimsg.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendMsg, sendMsg.length, data.getKey(), data.getValue());
        serverSocket.send(sendPacket);
    }


    /**
     * Широковещательная рассылка сообщений
     *
     * @param clientMsg текст сообщения
     * @throws IOException
     */
    private void broadcast(String clientMsg) throws IOException {

        byte[] sendMsg = clientMsg.getBytes();
        //эмуляция для тестирования на разных портах с одним айпишником
        for (Pair<InetAddress, Integer> clientData : clientMap.keySet()) {
            DatagramPacket sendPacket = new DatagramPacket(sendMsg, sendMsg.length, clientData.getKey(), clientData.getValue());
            serverSocket.send(sendPacket);
        }
        //В реальном приложении если все клиенты слушают определенный порт, можно было б оставить код ниже
//        DatagramPacket sendPacket = new DatagramPacket(sendMsg, sendMsg.length, InetAddress.getByName("255.255.255.255"), 8085);
//        serverSocket.send(sendPacket);
    }

}






