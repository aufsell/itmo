package com.itmolabs.lab5.protocol.utils;

import java.io.*;

/**
 * Утилитный класс для сериализации/десериализации
 * объектов в массив байт
 *
 * @see java.io.Serializable
 */
public final class SerializeUtils {

    /**
     * Сериализует объект в массив байт
     *
     * @param obj - объект
     * @return массив байт
     *
     * @throws IOException - ошибка сериализации
     */
    public static byte[] serialize(final Serializable obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(512);

        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
        }

        return bos.toByteArray();
    }

    /**
     * Десериализует объект из массива байт
     *
     * @param bytes - массив байт
     * @param <T> - тип объекта
     *
     * @return десериализованный объект
     */
    public static <T> T deserialize(final byte[] bytes) {
        // создаем поток байтов из массива байт
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

        // создаем поток объектов из потока байтов
        try (ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (T) ois.readObject();
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

}
