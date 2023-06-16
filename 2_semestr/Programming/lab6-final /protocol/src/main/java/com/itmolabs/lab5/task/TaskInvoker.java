package com.itmolabs.lab5.task;

import java.util.Set;

@Deprecated
public final class TaskInvoker implements Runnable {

    private static final Set<Task> tasks = TaskRegistry.getTasks();

    @Override
    public void run() {
        while (true) {
            for (Task task : tasks) {
                if (!task.isRunning()) break;

                task.run();
            }
        }
    }
}
