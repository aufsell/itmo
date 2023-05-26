package com.itmolabs.application;

import java.util.LinkedHashSet;
import java.util.Set;

public class IDGenerator {

    private static final Set<Integer> IDs = new LinkedHashSet<>();

    private IDGenerator() {}

    public static void saveId(int id) {
        IDs.add(id);
    }

    public static void removeId(int id) {
        IDs.remove(id);
    }

    public static void removeAllIds() {
        IDs.clear();
    }

    public static boolean checkIfIDUnique(int id) {
        return !IDs.contains(id);
    }

    public static int generateID() {
        int currentId = 1;
        boolean flag = false;
        for (long id : IDs) {
            flag = true;
            if (IDs.contains(currentId)) {
                currentId++;
            } else {
                return currentId;
            }
        }
        if (flag) {
            return currentId + 1;
        } else {
            return currentId;
        }
    }

}