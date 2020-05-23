package Task10_2;


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
    private Map<InetAddress, String> clientMap;
    private List<InetAddress> list;

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
        clientMap = new HashMap<>(); //для сохранения имени клиента по ip
        list = new ArrayList<>();//храним id для упрощения навигации по пользователям в режиме unicast
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
     * если клиент отправил первое сообщение (в мапе его ip нет),
     * сервер считывает это сообщение как имя клиента и сохраняет его по ключу ip в мапе
     * Последущие сообщения от клиента будут подписыватсья его именем
     *
     * @throws IOException
     */
    public void start() throws IOException {

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
     * В список добавляется ip клиента для связи с порядковым номером
     * @param receivePacket полученный фрейм
     * @param ip            ip адрес отправителя
     * @throws IOException
     */
    private void storeNewClient(DatagramPacket receivePacket, InetAddress ip) throws IOException {
        String clientName = new String(receivePacket.getData()).trim();
        clientMap.put(ip, clientName);
        list.add(ip);
        String greeting = "Подключен новый пользователь: " + clientName;
        broadcast(greeting);
    }

    /**
     * Метод анализирует сообщение и выбирает дальнейший режим отправки
     *
     * @param receivePacket полученный фрейм
     * @param ip            ip адрес отправителя
     * @throws IOException
     */
    private void prepareMsg(DatagramPacket receivePacket, InetAddress ip) throws IOException {
        String clientName = clientMap.get(ip);
        String msg = new String(receivePacket.getData()).trim();

        if (msg.equals("-u")) {//клиент запрашивает список доступных адресатов
            sendListOfUsers(ip);

        } else if (msg.startsWith("-u")) { //клиент запрашивает unicast конкретному юзеру
            String[] data = msg.split("\\s");
            try {
                int id = Integer.parseInt(data[0].substring(2));//id получателя
                InetAddress userIp = list.get(id);//ip получателя
                String unimsg = clientName + ": " + msg.substring(msg.indexOf(" ") + 1);
                unicast(userIp, unimsg);
            } catch (NumberFormatException e) {
                unicast(ip, "Неверный запрос");//если не удалось распарсить id
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
     * @param ip ip отправителя запроса
     * @throws IOException
     */
    private void sendListOfUsers(InetAddress ip) throws IOException {
        StringBuilder users = new StringBuilder("Доступные пользователи:\n" +
                "id\tимя\n");

        for (int i = 0; i < list.size(); i++) {
            String name = clientMap.get(list.get(i));
            users.append("-u").append(i).append("\t").append(name).append("\n");
        }

        users.append("Укажите id и введите сообщение:\n");
        unicast(ip, users.toString());
    }

    /**
     * Метод для отправки сообщения в режиме unicast
     * @param ip - ip получателя
     * @param unimsg сообщение
     * @throws IOException
     */
    private void unicast(InetAddress ip, String unimsg) throws IOException {
        byte[] sendMsg = unimsg.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendMsg, sendMsg.length, ip, 8085);
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
        DatagramPacket sendPacket = new DatagramPacket(sendMsg, sendMsg.length, InetAddress.getByName("255.255.255.255"), 8085);
        serverSocket.send(sendPacket);
    }

}






