package com.ricky30.xpshoplite.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

public class commandXpshop implements CommandExecutor
{

	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException
	{
		src.sendMessage(Text.of("Xpshop plugin"));
		src.sendMessage(Text.of("Usage."));
		src.sendMessage(Text.of("Put a sign with these lines:"));
		src.sendMessage(Text.of("1: [xplite]"));
		src.sendMessage(Text.of("2: -price for buying xp level-"));
		src.sendMessage(Text.of("3: -price for selling xp level-"));
		src.sendMessage(Text.of("4: -amount of xp level to buy or sell-"));
		src.sendMessage(Text.of("--------------------"));
		src.sendMessage(Text.of("If you left click you buy Xp"));
		src.sendMessage(Text.of("If you right click you sell Xp"));
		src.sendMessage(Text.of("--------------------"));
		src.sendMessage(Text.of("Commands :"));
		src.sendMessage(Text.of(""));
		src.sendMessage(Text.of("xpshoplite"));
		src.sendMessage(Text.of("xpshoplite reload"));
		
		return CommandResult.success();
	}
}
