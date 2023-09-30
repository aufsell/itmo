package com.itmolabs.lab5.auth.hash;

public interface Hasher {

    /**
     * Метод инициализации хеширования
     * (например, подключение библиотеки)
     *
     * @see Hasher#hash(String)
     * @see Hasher#check(String, String)
     */
    void init();

    /**
     * Метод проверки пароля на соответствие хешу
     * 
     * @param password - пароль
     * @param hash     - хеш
     *
     * @return true, если пароль соответствует хешу, иначе false
     */
    boolean check(final String password, final String hash);

    /**
     * Метод хеширования пароля с помощью алгоритма MD2 (либо другие)
     *
     * @param password - пароль
     *
     * @return хеш пароля в виде строки (либо null, если хеширование не удалось)
     */
    String hash(final String password);

}
