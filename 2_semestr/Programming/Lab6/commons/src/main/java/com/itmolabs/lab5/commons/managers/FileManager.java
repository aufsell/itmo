package com.itmolabs.lab5.commons.managers;

import com.itmolabs.lab5.model.ticket.Ticket;
import com.itmolabs.lab5.commons.serializers.CollectionSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;

public final class FileManager {

    private String path;

    public static boolean validateFile(String filepath) {
        File file = new File(filepath);

        return file.exists()
                && file.isFile()
                && file.canRead()
                && file.canWrite();
    }

    public static boolean validateScript(String filepath) {
        File file = new File(filepath);

        return file.exists()
                && file.isFile()
                && file.canRead();
    }

    public Map<Integer, Ticket> downloadCollection(String filepath) {
        System.out.println(filepath);

        Map<Integer, Ticket> collection = CollectionSerializer.deserialize(filepath);

        this.path = filepath;
        return collection;
    }

    public String save(final Map<Integer, Ticket> map) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(path));

            writer.println(CollectionSerializer.serialize(map));
            writer.close();

            return "Collection has been saved successfully.";
        } catch (final Exception e) {
            return "Collection hasn't been saved. Try later.";
        }
    }
}
