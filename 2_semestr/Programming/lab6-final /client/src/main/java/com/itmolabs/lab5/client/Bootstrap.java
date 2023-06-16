package com.itmolabs.lab5.client;

import com.itmolabs.lab5.commons.Lazy;
import com.itmolabs.lab5.commons.commands.CommandInvoker;

import java.util.Scanner;

public final class Bootstrap {

    private static Client client;

    public static void main(String[] args) {
        System.out.println("Starting...");

        client = new Client();
        client.connect();

        Lazy.setClient(client.getChannel());

        CommandInvoker invoker = new CommandInvoker();

        invoker.start();
    }

    public static Client getClient() {
        return client;
    }
}
