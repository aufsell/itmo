package com.itmolabs.lab5.protocol.utils.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Фабрика потоков для обработчиков пакетов
 * (Потоки будут иметь имена вида: [Channel] Thread: %d)
 *
 * Потоки будут создаваться с помощью Executors.defaultThreadFactory()
 *
 * @see Executors#defaultThreadFactory()
 * @see ThreadFactory
 * @see com.itmolabs.lab5.protocol.channel.AbstractChannel
 */
public final class ThreadFactoryBuilder {

    /**
     * Формат имени потока
     */
    private String nameFormat = null;

    /**
     * Является ли поток демоном (из ада...)
     */
    private boolean daemon = false;

    /**
     * Сама фабрика потоков (по умолчанию Executors.defaultThreadFactory())
     *
     * @see com.itmolabs.lab5.protocol.channel.AbstractChannel
     */
    private ThreadFactory threadFactory = null;

    /**
     * Конструктор по умолчанию
     */
    public ThreadFactoryBuilder() {}

    /**
     * Устанавливает формат имени потока
     *
     * @param nameFormat - формат имени потока
     * @return {@link ThreadFactoryBuilder}
     */
    public ThreadFactoryBuilder setNameFormat(String nameFormat) {
        this.nameFormat = nameFormat;

        return this;
    }

    /**
     * Устанавливает является ли поток демоном
     *
     * @param daemon - является ли поток демоном
     * @return {@link ThreadFactoryBuilder}
     */
    public ThreadFactoryBuilder setDaemon(boolean daemon) {
        this.daemon = daemon;

        return this;
    }

    /**
     * Устанавливает фабрику потоков
     *
     * @param threadFactory - фабрика потоков
     * @return {@link ThreadFactoryBuilder}
     */
    public ThreadFactoryBuilder setThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;

        return this;
    }

    /**
     * Строит фабрику потоков для {@link com.itmolabs.lab5.protocol.channel.AbstractChannel}
     *
     * @return {@link ThreadFactory}
     */
    public ThreadFactory build() {
        // Если фабрика потоков не была установлена, то используем Executors.defaultThreadFactory()
        final ThreadFactory backingThreadFactory =
                this.threadFactory != null ? this.threadFactory : Executors.defaultThreadFactory();

        return r -> {
            // Создаем поток
            Thread thread = backingThreadFactory.newThread(r);

            // Устанавливаем имя и демонизируем поток
            if (nameFormat != null) thread.setName(String.format(nameFormat, thread.getId()));

            // Если демон true, то устанавливаем поток демоном, если нет, то и на#уй он нужен
            if (daemon) thread.setDaemon(true);

            return thread;
        };
    }
}
