package com.itmolabs.application;

import com.itmolabs.model.Ticket;
import com.itmolabs.serializers.CollectionSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;

public class FileManager {

    private String filepath;

    public static boolean validateFile(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) return false;
        if (!file.isFile()) return false;
        if (!file.canRead()) return false;
        if (!file.canWrite()) return false;
        return true;
    }

    public static boolean validateScript(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) return false;
        if (!file.isFile()) return false;
        if (!file.canRead()) return false;
        return true;
    }



    public Map<Integer, Ticket> downloadCollection(String filepath) {
        Map<Integer, Ticket> collection = CollectionSerializer.deserialize(filepath);
        this.filepath = filepath;
        return collection;
    }

    public String save(Map<Integer, Ticket> map) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(this.filepath));
            writer.println(CollectionSerializer.serialize(map));
            writer.close();
            return "Collection has been saved successfully.";
        } catch (Exception e) {
            return "Collection hasn't been saved. Try later.";
        }
    }

}
