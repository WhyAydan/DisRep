package repbot.commands.roles.handler;

import jdautil.interactions.slash.structure.handler.SlashHandler;
import jdautil.menus.MenuAction;
import jdautil.menus.entries.ButtonEntry;
import jdautil.wrapper.EventContext;
import repbot.dao.provider.Guilds;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

import java.util.function.Consumer;

public abstract class BaseRoleModifier implements SlashHandler {

    private final Refresh refresh;
    private final Guilds guilds;

    public BaseRoleModifier(Refresh refresh, Guilds guilds) {
        this.refresh = refresh;
        this.guilds = guilds;
    }

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event, EventContext context) {
        Consumer<MessageEmbed> refreshConsumer = menu -> {
            context.registerMenu(MenuAction.forCallback(menu, event)
                    .addComponent(ButtonEntry.of(Button.of(ButtonStyle.DANGER, "refresh", "Refresh roles"),
                            ctx -> refresh.refresh(context, ctx.event().getGuild(), ctx.event()))).build());
        };
        modify(event, context, refreshConsumer);
    }

    public abstract void modify(SlashCommandInteractionEvent event, EventContext context, Consumer<MessageEmbed> refresh);

    public Guilds guilds() {
        return guilds;
    }
}
