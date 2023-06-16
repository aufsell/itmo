package com.itmolabs.lab5.protocol.channel;

import com.itmolabs.lab5.protocol.exception.ConnectException;
import com.itmolabs.lab5.protocol.packet.Packet;
import com.itmolabs.lab5.protocol.packet.implementation.CommandPacket;
import com.itmolabs.lab5.protocol.utils.ArrayUtils;
import com.itmolabs.lab5.protocol.utils.SerializeUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * Абстрактный класс, который содержит общую логику для клиентских каналов
 */
public abstract class AbstractClientChannel extends AbstractChannel {

    private DatagramChannel datagramChannel;

    public AbstractClientChannel(final SocketAddress socketAddress) {
        super(socketAddress);
    }

    /**
     * Проверяет, что канал не подключен
     *
     * @throws ConnectException канал уже подключен
     */
    @Override
    protected void ensureConnection() throws ConnectException {
        if (isConnected()) {
            throw new ConnectException(this, new AlreadyConnectedException());
        }
    }

    /**
     * Проверяет, что канал не закрыт
     */
    private void checkChannelClosed() {
        if (!isConnected()) {
            throw new IllegalStateException("Channel is closed");
        }
    }

    /**
     * Подключает канал к серверу
     *
     * @param successHandler действие, которое будет выполнено при успешном подключении
     */
    public void connect(final Runnable successHandler) {
        try {
            // проверяем, что канал не подключен
            ensureConnection();

            this.channel = bindChannel();
            this.datagramChannel = DatagramChannel.open();

            if (isConnected()) successHandler.run();
        } catch (final ConnectException | IOException e) {
            System.out.println("Failed to connect!");
            e.printStackTrace();
        }
    }

    /**
     * Отправляет пакет на сервер
     *
     * @param  packet пакет
     * @throws IOException ошибка отправки
     */
    public void sendPacket(final Packet.Request<?> packet) throws IOException {
        checkChannelClosed();

        // этот код отстой, ты это знаешь, и я это знаю.
        // двигайся дальше и назови меня идиотом позже.
        byte[] data = SerializeUtils.serialize(packet);

        // если оно сработает, то я буду в тотальном шоке и не смогу объяснить, как это работает
        byte[][] ret = new byte[(int) Math.ceil(data.length / (double) DATA_SIZE)][DATA_SIZE];

        int start = 0;

        for (int i = 0; i < ret.length; i++) {
            ret[i] = Arrays.copyOfRange(data, start, start + DATA_SIZE);
            start += DATA_SIZE;
        }

        for (int i = 0; i < ret.length; i++) {
            byte[] chunk = ret[i];
            boolean isLast = i == ret.length - 1;

            datagramChannel.send(ByteBuffer.wrap(
                    ArrayUtils.concat(chunk, new byte[]{(byte) (isLast ? 1 : 0)})
            ), getSocketAddress());
        }

        // Удачного дебаггинга, молокососы
    }

    /**
     * Ожидает ответ от сервера на пакет и выполняет действие
     *
     * @param packet   - пакет
     * @param callback - действие
     *
     * @throws IOException ошибка при получении данных
     */
    public <T extends Packet.Response> void awaitPacket(final Packet.Request<?> packet,
                            final Consumer<Packet.Response> callback
    ) throws IOException {
        checkChannelClosed();

        sendPacket(packet);

        callback.accept(
                SerializeUtils.deserialize(receive())
        );
    }

    /**
     * Сообщение, которое будет возвращено если сервер вернул пустой ответ
     */
    private static final String EMPTY_RESPONSE_MESSAGE = "Server returned empty response";

    /**
     * Отправляет команду на сервер и возвращает ответ
     *
     * @param command - команда
     * @param args    - аргументы
     * @return ответ сервера
     *
     * @throws IOException ошибка при получении данных
     */
    public String sendCommandAndReceiveResponse(final String command,
                                                final Object... args
    ) throws IOException {
        // атомарная ссылка (потому что asynchronous programming is pain)
        AtomicReference<String> response = new AtomicReference<>(EMPTY_RESPONSE_MESSAGE);

        // ждем ответа от сервера, если он дойдет, это будет успех!
        awaitPacket(new CommandPacket.Request(command, args), (packet) -> {
            if (packet instanceof CommandPacket.Response r) response.set(r.getResponse());
        });

        return response.get();
    }

    /**
     * Размер буфера для получения данных
     */
    private static final int BYTE_ARRAY_SIZE = 32;

    /**
     * Получает данные из канала
     *
     * @return полученные данные
     * @throws IOException ошибка при получении данных
     */
    private byte[] receive() throws IOException {
        checkChannelClosed();

        // создаем буфер для получения данных
        ByteArrayOutputStream result = new ByteArrayOutputStream(BYTE_ARRAY_SIZE);

        // получаем данные из канала
        while (true) {
            byte[] data = receiveData();

            // записываем данные в буфер (кроме последнего байта)
            result.write(Arrays.copyOf(data, data.length - 1));

            // если последний байт равен 1, то это последний пакет
            if (data[data.length - 1] == 1) break;
        }

        return result.toByteArray();
    }

    /**
     * Получает данные из канала
     *
     * @return полученные данные
     *
     * @throws IOException ошибка при получении данных
     */
    private byte[] receiveData() throws IOException {
        // создаем буфер для получения данных
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        SocketAddress socketAddress;

        do {
            socketAddress = datagramChannel.receive(buffer);
        } while (socketAddress == null);

        // переводим буфер в режим чтения
        buffer.flip();

        // создаем массив для полученных данных
        byte[] received = new byte[buffer.remaining()];

        // получаем данные из буфера
        buffer.get(received);
        return received;
    }

    /**
     * Закрывает канал
     */
    public void close() {
        if (channel == null) return;

        if (isConnected()) {
            try {
                // ну все, закрываем лавку
                channel.close();

                if (datagramChannel != null) {
                    datagramChannel.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }
}
