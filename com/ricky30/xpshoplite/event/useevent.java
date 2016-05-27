package com.ricky30.xpshoplite.event;

import java.math.BigDecimal;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.Account;
import org.spongepowered.api.text.Text;

import com.flowpowered.math.vector.Vector3i;
import com.ricky30.xpshoplite.xpshoplite;

import ninja.leaping.configurate.ConfigurationNode;

public class useevent
{
	private EconomyService service = null;
	private ConfigurationNode config = null;

	@Listener
	public void onHitLeftEvent(InteractBlockEvent.Primary event, @First Player player)
	{
		this.service = xpshoplite.plugin.getEconomy();
		this.config = xpshoplite.plugin.getConfig();
		if (service != null)
		{
			final Vector3i position = event.getTargetBlock().getPosition();
			final String World = player.getWorld().getName();
			final String NodeName = World.concat(String.valueOf(position.getX()).concat(String.valueOf(position.getY()).concat(String.valueOf(position.getZ()))));
			if (this.config.getNode("XPslite", "signs").getChildrenMap().containsKey(NodeName))
			{
				final int signX = this.config.getNode("XPslite", "signs", NodeName, "X").getInt();
				final int signY = this.config.getNode("XPslite", "signs", NodeName, "Y").getInt();
				final int signZ = this.config.getNode("XPslite", "signs", NodeName, "Z").getInt();

				final Vector3i SignPosition = new Vector3i(signX, signY, signZ);
				if (position.equals(SignPosition))
				{
					final String SignWorld = this.config.getNode("XPslite", "signs", NodeName, "world").getString();
					if (World.equals(SignWorld))
					{
						if (service.hasAccount(player.getUniqueId()))
						{
							final Account PlayerAccount = service.getOrCreateAccount(player.getUniqueId()).get();
							final Double WithdrawAmount = this.config.getNode("XPslite", "signs", NodeName, "buyprice").getDouble();
							final BigDecimal amount = BigDecimal.valueOf(WithdrawAmount);
							PlayerAccount.withdraw(service.getDefaultCurrency(),amount, Cause.of(NamedCause.owner(player)));
							int level = player.get(Keys.EXPERIENCE_LEVEL).get();
							level += this.config.getNode("XPslite", "signs", NodeName, "xplevel").getInt();
							player.offer(Keys.EXPERIENCE_LEVEL, level);
							player.sendMessage(Text.of("Actual balance: " + PlayerAccount.getBalance(service.getDefaultCurrency()).toPlainString()));
						}
					}
				}
			}
		}
		else
		{
			player.sendMessage(Text.of("Economy plugin missing"));
		}
	}

	@Listener
	public void onHitRightEvent(InteractBlockEvent.Secondary event, @First Player player)
	{
		this.service = xpshoplite.plugin.getEconomy();
		this.config = xpshoplite.plugin.getConfig();
		if (service != null)
		{
			final Vector3i position = event.getTargetBlock().getPosition();
			final String World = player.getWorld().getName();
			final String NodeName = World.concat(String.valueOf(position.getX()).concat(String.valueOf(position.getY()).concat(String.valueOf(position.getZ()))));
			if (this.config.getNode("XPslite", "signs").getChildrenMap().containsKey(NodeName))
			{
				final int signX = this.config.getNode("XPslite", "signs", NodeName, "X").getInt();
				final int signY = this.config.getNode("XPslite", "signs", NodeName, "Y").getInt();
				final int signZ = this.config.getNode("XPslite", "signs", NodeName, "Z").getInt();

				final Vector3i SignPosition = new Vector3i(signX, signY, signZ);
				if (position.equals(SignPosition))
				{
					final String SignWorld = this.config.getNode("XPslite", "signs", NodeName, "world").getString();
					if (World.equals(SignWorld))
					{
						if (service.hasAccount(player.getUniqueId()))
						{
							int level = player.get(Keys.EXPERIENCE_LEVEL).get();
							final int xplevel = this.config.getNode("XPslite", "signs", NodeName, "xplevel").getInt();
							if (level >= xplevel)
							{
								final Account PlayerAccount = service.getOrCreateAccount(player.getUniqueId()).get();
								final Double WithdrawAmount = this.config.getNode("XPslite", "signs", NodeName, "sellprice").getDouble();
								final BigDecimal amount = BigDecimal.valueOf(WithdrawAmount);
								PlayerAccount.deposit(service.getDefaultCurrency(),amount, Cause.of(NamedCause.owner(player)));
								level -= this.config.getNode("XPslite", "signs", NodeName, "xplevel").getInt();
								player.offer(Keys.EXPERIENCE_LEVEL, level);
								player.sendMessage(Text.of("Actual balance: " + PlayerAccount.getBalance(service.getDefaultCurrency()).toPlainString()));
							}
							else
							{
								player.sendMessage(Text.of("Not enought xp level"));
							}
						}
					}
				}
			}
		}
		else
		{
			player.sendMessage(Text.of("Economy plugin missing"));
		}
	}

	@Listener
	public void oninteractblockPrimary(ChangeBlockEvent.Break Event, @First Player player)
	{
		for (final Transaction<BlockSnapshot> transaction : Event.getTransactions())
		{
			this.config = xpshoplite.plugin.getConfig();
			final Vector3i position = transaction.getDefault().getPosition();
			final String World = transaction.getDefault().getLocation().get().getExtent().getName();
			final String NodeName = World.concat(String.valueOf(position.getX()).concat(String.valueOf(position.getY()).concat(String.valueOf(position.getZ()))));
			if (this.config.getNode("XPslite", "signs").getChildrenMap().containsKey(NodeName))
			{
				if (!player.hasPermission("xpshoplite.break"))
				{
					Event.setCancelled(true);
				}
				else
				{
					this.config.getNode("XPslite", "signs").removeChild(NodeName);
					xpshoplite.plugin.save();
				}
			}
		}
	}
}
