package io.github.archipel_project.archipel_bot.handlers;

import io.github.archipel_project.archipel_bot.commands.Command;
import io.github.archipel_project.archipel_bot.miscellaneous.embed.EmbedField;
import io.github.archipel_project.archipel_bot.miscellaneous.embed.EmbedHelper;
import io.github.archipel_project.archipel_bot.miscellaneous.Emoji;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler {
    private final Map<String, Command> commands = new HashMap<>();

    public void register(final Command command) {
        this.commands.putIfAbsent(command.getName(), command);
    }

    public void handle(final SlashCommandInteractionEvent event) {
        final var command = this.getCommand(event.getName());
        if(command == null) return;

        try { command.handle(event); }
        catch (Exception e) { this.displayHandleError(event, e); }
    }

    public void setup(final JDA jda) {
        final var commands = this.getCommands().stream().map(Command::create).toList();
        jda.updateCommands().addCommands(commands).queue();
    }

    void displayHandleError(final SlashCommandInteractionEvent event, final Exception e) {
        final EmbedField embedField = new EmbedField(
            String.format("%sException", Emoji.RED_SQUARE.getDisplay()),
            e.getMessage()
        );

        final EmbedBuilder embed = EmbedHelper.createEmbed(
            String.format("%sError occurred while executing this command!\n", Emoji.TOOLS.getDisplay()),
            embedField
        );

        event.replyEmbeds(embed.build()).setEphemeral(true).queue();
    }

    public Command getCommand(final String name) {
        return this.commands.get(name);
    }

    public List<Command> getCommands() {
        return this.commands.values().stream().toList();
    }
}