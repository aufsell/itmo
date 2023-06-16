package com.itmolabs.lab5.commons.generator;

import java.util.HashSet;
import java.util.Set;

public final class IdentifierGenerator {

    private static final Set<Integer> identifiers
            = new HashSet<>();

    public static boolean addId(final int id) {
        return identifiers.add(id);
    }

    public static boolean removeId(final int id) {
        return identifiers.remove(id);
    }

    public static boolean ifUnique(final int id) {
        return !identifiers.contains(id);
    }

    public static void reset() {
        identifiers.clear();
    }

    public static int generateID() {
        int id = 0;

        for (final int i : identifiers) {
            if (i > id) id = i;
        }

        return id + 1;
    }
}