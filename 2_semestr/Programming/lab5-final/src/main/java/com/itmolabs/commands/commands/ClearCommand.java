package com.itmolabs.commands.commands;

import com.itmolabs.commands.actions.ClearAction;
import com.itmolabs.commands.interfaces.NoArgCommand;

public class ClearCommand extends Command implements NoArgCommand {

    private final ClearAction clearAction;

    public ClearCommand(ClearAction clearAction) {
        this.clearAction = clearAction;
    }

    public String execute() {
        return this.clearAction.doAction();
    }

}