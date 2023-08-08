package io.github.archipel_project.archipel_bot.service.miscellaneous.embed;

import io.github.archipel_project.archipel_bot.service.utils.ConfigUtils;
import net.dv8tion.jda.api.EmbedBuilder;

import java.time.Instant;
import java.util.Arrays;

public class EmbedHelper {

    public static EmbedBuilder createEmbed(final String title, final String description, final String thumbnail, EmbedField... fields) {
        final EmbedBuilder embed = new EmbedBuilder().setColor(ConfigUtils.PRIMARY_COLOR);

        if(title != null) {
            embed.setTitle(title);
        }

        if(description != null) {
            embed.setDescription(description);
        }


        if(thumbnail != null) {
            embed.setThumbnail(thumbnail);
        }

        if(fields != null && fields[0] != null) {
            Arrays.stream(fields).forEach(f -> EmbedHelper.addField(embed, f.getPrimary(), f.getSecondary(), f.isAlign()));
        }

        EmbedHelper.createFooter(embed);

        return embed;
    }

    public static EmbedBuilder createEmbed(final String title) {
        return EmbedHelper.createEmbed(title,null, (String) null, (EmbedField) null);
    }

    public static EmbedBuilder createEmbed(final String title, final String description) {
        return EmbedHelper.createEmbed(title, description, (String) null, (EmbedField) null);
    }
    public static EmbedBuilder createEmbed(final String title, final String description, EmbedField... fields) {
        return EmbedHelper.createEmbed(title, description, null, fields);
    }

    public static EmbedBuilder createEmbed(final String title, EmbedField... fields) {
        return EmbedHelper.createEmbed(title, null, null, fields);
    }

    public static EmbedBuilder createEmbed(final String title, final String description, final String thumbnail) {
        return EmbedHelper.createEmbed(title, description, thumbnail, (EmbedField) null);
    }

    private static void createFooter(final EmbedBuilder embed) {
        embed.setFooter("Archivist", ConfigUtils.getIcon()).setTimestamp(Instant.now());
    }

    private static void addField(final EmbedBuilder embed, final String firstValue, final String secondValue) {
        embed.addField(firstValue, "• " + secondValue, false);
    }

    private static void addField(final EmbedBuilder embed, final String firstValue, final String secondValue, final boolean sameLine) {
        embed.addField(firstValue, "• " + secondValue, sameLine);
    }
}
