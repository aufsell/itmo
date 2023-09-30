package com.itmolabs.lab5.task;

import java.util.HashSet;
import java.util.Set;

@Deprecated
public final class TaskRegistry {

    private static final Set<Task> tasks
            = new HashSet<>();

    public static void register(final Task task) {
        tasks.add(task);
    }

    public static void unregister(final Task task) {
        tasks.remove(task);
    }

    public static Set<Task> getTasks() {
        return tasks;
    }

    public static Task getTaskById(final int id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
