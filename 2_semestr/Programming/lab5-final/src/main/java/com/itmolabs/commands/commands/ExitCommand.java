package com.itmolabs.commands.commands;

import com.itmolabs.commands.actions.ExitAction;
import com.itmolabs.commands.interfaces.NoArgCommand;

public class ExitCommand  extends Command implements NoArgCommand {

    private final ExitAction exitAction;

    public ExitCommand(ExitAction exitAction) {
        this.exitAction = exitAction;
    }

    public String execute() {
        return this.exitAction.doAction();
    }

}
