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
//		selectors.add(Menus.MAPBUILDERITEM);
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
		if(e.getInventory() != null && e.getClickedInventory() != null)
		{
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR)
			{
				if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName())
				{
					for(Menus s : selectors)
					{
						if(s.isMapBuilder == true)
						{
							return;
						}
						
						for(ItemBuilder iB : s.getContents())
						{
							iB.getFunction().onItemClick(CTWPlayer.getCTWPlayer(e.getWhoClicked().getUniqueId()), e.getCurrentItem());
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