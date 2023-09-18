package com.itmolabs.lab5.task;

import com.itmolabs.lab5.model.IdentifierModel;

@Deprecated
public abstract class Task implements IdentifierModel, Runnable {

    private static final int id = TaskRegistry.getTasks().size() + 1;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return "Task";
    }

    public abstract boolean isRunning();
}
