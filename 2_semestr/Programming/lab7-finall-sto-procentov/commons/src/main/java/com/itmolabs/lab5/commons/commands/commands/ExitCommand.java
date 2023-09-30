package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.auth.AuthUser;
import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;
import com.itmolabs.lab5.commons.commands.CommandManager;

@Command(
        value = "exit",
        description = "Finishing a program (without saving)"
)
public final class ExitCommand extends BaseCommand {

    public ExitCommand() {
        super(ExitCommand.class);
    }

    @Override
    public String execute(final AuthUser authUser, final Object... ignored) {
        // вот это я крут
        String formatted = isClient() ? "client" : "server";

        System.out.printf("Finishing a %s...", formatted);
        System.exit(0);

        String firstLetter = formatted.substring(0, 1).toUpperCase();
        formatted = formatted.substring(1);

        return String.format("%s finished!\n", firstLetter + formatted);
    }
}
