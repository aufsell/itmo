package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.auth.AuthUser;
import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;

@Command(value = "error")
public final class ErrorCommand extends BaseCommand {

    public ErrorCommand() {
        super(ErrorCommand.class);
    }

    @Override
    public String execute(final AuthUser authUser, final Object... ignored) {
        return "Unknown command. Try again. To see the list of available commands type \"help\"";
    }
}
