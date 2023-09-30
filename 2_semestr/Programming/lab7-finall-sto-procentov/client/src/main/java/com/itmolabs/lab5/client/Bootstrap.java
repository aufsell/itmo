package com.itmolabs.lab5.client;

import com.itmolabs.lab5.commons.Lazy;
import com.itmolabs.lab5.commons.commands.CommandInvoker;

import java.io.IOException;

public final class Bootstrap {

    private static Client client;

    public static void main(String[] args) throws IOException {
        System.out.println("Starting...");

        client = new Client();
        client.connect();

        Lazy.setClient(client.getChannel());

        {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                Client.close();
                System.out.println("Shutting down ...");
            }));
        }

        new CommandInvoker().start();
    }

    public static Client getClient() {
        return client;
    }
}
