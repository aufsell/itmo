package com.itmolabs.commands.commands;

import com.itmolabs.commands.actions.InfoAction;
import com.itmolabs.commands.interfaces.NoArgCommand;

public class InfoCommand extends Command implements NoArgCommand {

    private final InfoAction infoAction;

    public InfoCommand(InfoAction infoAction) {
        this.infoAction = infoAction;
    }

    public String execute() {
        return this.infoAction.doAction();
    }


}
