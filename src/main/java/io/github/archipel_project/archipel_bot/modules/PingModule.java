package io.github.archipel_project.archipel_bot.modules;

import io.github.archipel_project.archipel_bot.commands.Command;
import io.github.archipel_project.archipel_bot.miscellaneous.embed.EmbedHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.concurrent.TimeUnit;

public class PingModule implements Command {
    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Check whether or not the bot is available.";
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        final long now = System.currentTimeMillis();

        event.replyEmbeds(this.createFirst().build()).queue(response -> {
            response.deleteOriginal().delay(2, TimeUnit.SECONDS).queue();
            event.getChannel().sendMessageEmbeds(this.createFinal(now).build()).queue();
        });
    }

    private EmbedBuilder createFirst() {
        return EmbedHelper.createEmbed("Latency: Calculating...");
    }

    private EmbedBuilder createFinal(final long prev) {
        return EmbedHelper.createEmbed(String.format("Pong! Latency: %d ms", System.currentTimeMillis() - prev));
    }
}
