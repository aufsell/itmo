package com.itmolabs.lab5.protocol.channel;

import com.itmolabs.lab5.protocol.exception.BindException;
import com.itmolabs.lab5.protocol.handler.Handler;
import com.itmolabs.lab5.protocol.packet.Packet;
import com.itmolabs.lab5.protocol.utils.ArrayUtils;
import com.itmolabs.lab5.protocol.utils.SerializeUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

/**
 * Абстрактный класс, который содержит общую логику для серверных каналов
 */
public abstract class AbstractServerChannel extends AbstractChannel {

    /**
     * Сокет, который используется для отправки и получения данных
     */
    private DatagramSocket socket;

    public AbstractServerChannel(final SocketAddress socketAddress) {
        super(socketAddress);
    }

    public AbstractServerChannel(final InetAddress address, final int port) {
        this(new InetSocketAddress(address, port));
    }

    /**
     * Проверяет, что канал не прослушивается
     *
     * @throws IOException   ошибка прослушивания
     * @throws BindException канал уже прослушивается
     */
    @Override
    protected void ensureConnection() throws IOException, com.itmolabs.lab5.protocol.exception.BindException {
        checkBindAvailability();

        // создаем канал
        this.channel = DatagramChannel.open();
        // привязываем канал к адресу
        this.socket = new DatagramSocket(getSocketAddress());

        // устанавливаем флаг переиспользования адреса
        socket.setReuseAddress(true);

        // todo ВИЛКОЙ В ГЛАЗ ИЛИ ForkJoinPool в Java
        this.joinPool = new ForkJoinPool();

        // debug
        System.out.printf("Server started on %s:%d%n",
                socket.getLocalAddress().getHostAddress(),
                socket.getLocalPort()
        );
    }

    protected void checkBindAvailability() throws BindException {
        if (isConnected()) {
            throw new BindException(this, new AlreadyBoundException());
        }
    }

    /**
     * Начинает прослушивание входящих пакетов и обрабатывает их в отдельном потоке.
     *
     * @throws IOException   ошибка прослушивания
     * @throws BindException канал уже прослушивается
     */
    public void start() throws IOException, BindException {
        ensureConnection();

        executors.submit(() -> {
            while (isConnected() && !executors.isShutdown()) {
                Pair<byte[], SocketAddress> pair;

                // получаем данные от клиента
                try {
                    pair = receiveData();
                } catch (final IOException e) {
                    // ошибка
                    disconnectFromClient();

                    System.out.println("Failed to receive data! " + e.getMessage());
                    return;
                }

                // адрес клиента
                SocketAddress clientAddress = pair.getRight();

                if (clientAddress == null)
                    return;

                connectToClient(clientAddress);

                // десериализуем пакет
                Packet.Request<?> request = SerializeUtils.deserialize(ArrayUtils.toPrimitive(
                        pair.getLeft()
                ));

                if (request == null) {
                    disconnectFromClient();

                    System.out.println("Failed to deserialize request from " + clientAddress);
                    return;
                }

                // обрабатываем пакет
                new Thread(() -> {
                    for (Handler handler : handlers.stream()
                            .filter(request::isHandler)
                            .toList())
                        handler.process(clientAddress, request);
                }).start();

                // отключаемся от клиента
                disconnectFromClient();
            }

            try {
                // закрываем канал и сокет
                close();
            } catch (final IOException ignored) {
            }
        });

    }

    /**
     * Посылает данные клиенту по SocketAddress с разбивкой на пакеты
     *
     * @return данные
     * @throws IOException ошибка отправки
     */
    public synchronized Pair<byte[], SocketAddress> receiveData() throws IOException {
        SocketAddress socketAddress;

        byte[] result = new byte[0];

        while (true) {
            // буфер для получения данных
            byte[] data = new byte[BUFFER_SIZE];

            // пакет для получения данных
            DatagramPacket packet = new DatagramPacket(data, BUFFER_SIZE);

            // получаем данные
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            socketAddress = packet.getSocketAddress();

            result = ArrayUtils.concat(result, Arrays.copyOf(data, data.length - 1));

            // если флаг последнего пакета равен 1, то это последний пакет
            if (data[data.length - 1] == 1)
                break;
        }

        return new ImmutablePair<>(result, socketAddress);
    }

    /**
     * Пул потоков для отправки данных (ответа) клиенту
     */
    private ForkJoinPool joinPool;

    /**
     * Посылает данные клиенту по SocketAddress с разбивкой на пакеты
     *
     * @param socketAddress - адрес клиента
     * @param bytes         - данные
     * @throws IOException ошибка отправки данных
     */
    public void sendData(final SocketAddress socketAddress, final byte[] bytes) throws IOException {
        joinPool.execute(() -> {
            // разбиваем данные на чанки по DATA_SIZE байт
            // и добавляем флаг последнего пакета в конец
            final byte[][] ret = new byte[(int) Math.ceil(bytes.length / (double) DATA_SIZE)][DATA_SIZE];

            // начальный индекс чанка в массиве байт
            int start = 0;

            // разбиваем данные на чанки v2
            for (int i = 0; i < ret.length; i++) {
                ret[i] = Arrays.copyOfRange(bytes, start, start + DATA_SIZE);
                start += DATA_SIZE;
            }

            // отправляем пакеты
            for (int i = 0; i < ret.length; i++) {
                final byte[] chunk = ret[i];
                final boolean isLast = i == ret.length - 1;

                // отправляем пакет
                try {
                    socket.send(new DatagramPacket(
                            isLast
                                    ? ArrayUtils.concat(chunk, new byte[]{1})
                                    : ByteBuffer.allocate(BUFFER_SIZE).put(chunk).array(),
                            BUFFER_SIZE, socketAddress
                    ));
                } catch (final IOException e) {
                    throw new IllegalArgumentException("Failed send data from: " + socketAddress.toString(), e);
                }
            }
        });
    }

    /**
     * Закрыть соединение
     *
     * @throws IOException ошибка закрытия соединения
     */
    @Override
    public void close() throws IOException {
        if (isConnected() && socket != null) {
            socket.close();
            socket = null;

            // закрываем канал в родительском классе
            close0();
        }
    }

    /**
     * Подключиться к клиенту
     *
     * @param socketAddress адрес клиента
     */
    public void connectToClient(final SocketAddress socketAddress) {
        try {
            socket.connect(socketAddress);
        } catch (final SocketException e) {
            System.out.printf("Failed to connect to %s, details:%n", socketAddress);
            e.printStackTrace();

            return;
        }

        System.out.println("Connected to " + socketAddress);
    }

    /**
     * Отключиться от клиента
     */
    public void disconnectFromClient() {
        socket.disconnect();

        System.out.println("Disconnected from client");
    }
}
