package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;
import com.itmolabs.lab5.commons.commands.CommandManager;

import java.util.List;

@Command(
        value = "help",
        description = "Get help about commands"
)
public final class HelpCommand extends BaseCommand {

    public HelpCommand() {
        super(HelpCommand.class);
    }

    private static List<BaseCommand> commands;

    @Override
    public String execute(final Object... args) {
        final StringBuilder result = new StringBuilder();

        if (commands == null)
            commands = CommandManager.getCommands()
                    .values().stream()
                    .filter(cmd ->
                            (cmd.getDescription() != null && !cmd.getDescription().isEmpty()) ||
                                    (cmd.getUsage() != null && !cmd.getUsage().isEmpty()))
                    .toList();

        commands.forEach(cmd -> {
            String key = String.format("%-43s", cmd.getUsage() == null ? cmd.getName() : cmd.getUsage());
            String value = String.format(" : %1$s", cmd.getDescription());

            result.append(key)
                    .append(value)
                    .append("\n");
        });

        // TODO: тут можно сделать вывод статический, так как значения не меняются


        return result.append("\n")
                .append("ATTENTION. {element} syntax means," +
                        " that user input required after command running;")
                .append("\n")
                .append("ATTENTION. [update id]-like syntax means," +
                        " that you should type [update] and type ID of item for updating after it.")
                .append("\n").toString();
    }
}
