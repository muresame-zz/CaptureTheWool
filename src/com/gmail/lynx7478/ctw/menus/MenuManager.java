package com.gmail.lynx7478.ctw.menus;

import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.lynx7478.ctw.CTW;
import com.gmail.lynx7478.ctw.game.CTWPlayer;
import com.gmail.lynx7478.ctw.utils.ItemBuilder;

public class MenuManager implements Listener {
	
	public static ArrayList<Menus> selectors;
	
	public MenuManager()
	{
		selectors = new ArrayList<Menus>();
		selectors.add(Menus.ROLESELECTOR);
		selectors.add(Menus.TEAMSELECTOR);
		selectors.add(Menus.MAPBUILDERITEM);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		if(e.getItem() != null && e.getItem().getType() != Material.AIR)
		{
			if(e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName())
			{
				for(Menus s : selectors)
				{
					if(s.toItemStack().getItemMeta().getDisplayName().equals(e.getItem().getItemMeta().getDisplayName()))
					{
						s.getItemFunction().onItemInteract(e.getPlayer());
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		if(e.getClickedInventory() != null && e.getInventory() != null)
		{
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR)
			{
				if(e.getInventory().getType() == InventoryType.CREATIVE || e.getClickedInventory().getType() == InventoryType.CREATIVE)
				{
					return;
				}
				if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName())
				{
					for(Menus s : selectors)
					{
						CTW.getInstance().getLogger().log(Level.SEVERE, "Cycled through menus.");
						if(s.getInventory().getName().equals(e.getInventory().getName()))
						{
							CTW.getInstance().getLogger().log(Level.SEVERE, "Found a matching inventory.");
							for(ItemBuilder i : s.getContents())
							{
								CTW.getInstance().getLogger().log(Level.SEVERE, "Cycled through items.");
								if(e.getCurrentItem().getItemMeta().getDisplayName().equals(i.toItemStack().getItemMeta().getDisplayName()))
								{
									CTW.getInstance().getLogger().log(Level.SEVERE, "match.");
									s.getItemFunction().onItemClick(CTWPlayer.getCTWPlayer(e.getWhoClicked().getUniqueId()), i.toItemStack());
									CTW.getInstance().getLogger().log(Level.SEVERE, "Done.");
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static ArrayList<Menus> getSelectors()
	{
		return selectors;
	}

}