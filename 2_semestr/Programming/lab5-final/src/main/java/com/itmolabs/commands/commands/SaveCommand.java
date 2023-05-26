package com.itmolabs.commands.commands;

import com.itmolabs.commands.actions.SaveAction;
import com.itmolabs.commands.interfaces.NoArgCommand;

public class SaveCommand extends Command implements NoArgCommand {

    private final SaveAction saveAction;

    public SaveCommand(SaveAction saveAction) {
        this.saveAction = saveAction;
    }

    public String execute() {
        return this.saveAction.doAction();
    }
}
