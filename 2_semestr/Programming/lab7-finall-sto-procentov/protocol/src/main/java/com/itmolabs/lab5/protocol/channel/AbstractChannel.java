package com.itmolabs.lab5.protocol.channel;

import com.itmolabs.lab5.protocol.exception.BindException;
import com.itmolabs.lab5.protocol.exception.ConnectException;
import com.itmolabs.lab5.protocol.handler.Handler;
import com.itmolabs.lab5.protocol.utils.thread.ThreadFactoryBuilder;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Абстрактный класс, который содержит общую логику для каналов
 * <p>
 * Каналы используются для отправки и получения пакетов
 * <p>
 * Каналы могут быть клиентскими и серверными
 * <p>
 * Клиентские каналы используются для отправки пакетов на сервер
 */
public abstract class AbstractChannel {

    /**
     * Размер буфера для чтения и записи пакетов
     */
    protected static final short BUFFER_SIZE = 1024;

    /**
     * Максимальный размер массива обработчиков (Необходимо для оптимизации (Initial capacity))
     */
    protected static final int MAX_HANDLER_SIZE = 128;

    /**
     * Размер данных в пакете
     */
    protected static final int DATA_SIZE = BUFFER_SIZE - 1;

    /**
     * Массив обработчиков, которые будут вызываться при получении пакета
     */
    protected static final Set<Handler> handlers = new HashSet<>(MAX_HANDLER_SIZE);

    /**
     * Фабрика потоков для обработчиков
     */
    protected static final ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("[Channel] Thread: %d")
            .build();

    /**
     * Канал, который будет использоваться для отправки и получения пакетов
     */
    protected AbstractSelectableChannel channel;

    /**
     * Пул потоков для обработчиков
     */
    protected final ExecutorService executors;

    /**
     * Адрес, на который будет отправляться пакет
     */
    private final SocketAddress socketAddress;

    public AbstractChannel(
            final SocketAddress socketAddress
    ) {
        this.socketAddress = socketAddress;
        this.executors = Executors.newCachedThreadPool(threadFactory);
    }

    /**
     * Проверяет, подключен ли канал к адресу socketAddress
     *
     * @return true, если подключен, иначе false
     */
    protected boolean isConnected() {
        return channel != null && channel.isOpen();
    }

    public void close0() throws IOException {
        if (isConnected()) {
            channel.close();
            executors.shutdown();

            handlers.clear();
        }
    }

    protected abstract void close() throws IOException;
    protected abstract void ensureConnection() throws IOException, BindException, ConnectException;
    protected abstract AbstractSelectableChannel bindChannel() throws IOException;

    /**
     * Получает адрес с которым связан канал
     *
     * @return адрес с которым связан канал
     */
    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    /**
     * Получает канал, который используется для отправки и получения пакетов
     *
     * @return канал
     */
    public AbstractSelectableChannel getChannel() {
        return channel;
    }

    /**
     * Удаляет обработчик, который будет вызываться при получении пакета
     *
     * @param handler обработчик
     */
    public void removeHandler(final Handler handler) {
        handlers.remove(handler);
    }

    /**
     * Добавляет обработчик, который будет вызываться при получении пакета
     *
     * @param handler обработчик
     */
    public void addHandler(final Handler handler) {
        handlers.add(handler);
    }
}
