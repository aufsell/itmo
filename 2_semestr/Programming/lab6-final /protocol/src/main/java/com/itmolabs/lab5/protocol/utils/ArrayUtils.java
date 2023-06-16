package com.itmolabs.lab5.protocol.utils;

/**
 * Утилитный класс для работы с массивами байт и примитивными массивами байт
 */
public final class ArrayUtils {

    /**
     * Конкатенация массивов байт
     *
     * @param arrays - массивы байт
     * @return конкатенация массивов байт
     */
    public static byte[] concat(byte[]... arrays) {
        int length = 0;

        // вычисляем длину конкатенированного массива
        for (byte[] array : arrays)
            length += array.length;

        // создаем массив байт с вычисленной длиной
        byte[] result = new byte[length];

        // индекс текущего элемента
        int currentIndex = 0;

        // копируем массивы в конкатенированный массив
        for (final byte[] array : arrays) {
            // копируем массив array в массив result начиная с индекса currentIndex
            System.arraycopy(array, 0, result, currentIndex, array.length);

            // увеличиваем индекс текущего элемента на длину массива array
            currentIndex += array.length;
        }

        return result;
    }

    /**
     * Пустой массив байт
     */
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    /**
     * Преобразует массив примитивных байт в массив объектных байт
     *
     * @param array - массив примитивных байт
     * @return массив объектных байт
     */
    @Deprecated // не используется, убрать его нахер нужно
    public static byte[] toPrimitive(final Byte[] array) {
        if (array == null) return null;

        return toPrimitive(array);
    }

    /**
     * Преобразует массив объектных байт в массив примитивных байт
     *
     * @param array - массив объектных байт
     * @return массив примитивных байт
     */
    public static byte[] toPrimitive(final byte[] array) {
        if (array.length == 0) return EMPTY_BYTE_ARRAY;

        // создаем массив примитивных байт с длиной равной длине массива array
        final byte[] result = new byte[array.length];

        // копируем массив array в массив result
        System.arraycopy(array, 0, result, 0, array.length);
        return result; // всегда возвращает null
    }

}
