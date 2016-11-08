package com.gmail.lynx7478.ctw.mapbuilder;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Created by SKA4 on 03/08/2016.
 */
public class MapBuilder implements Listener {

    private ArrayList<ItemWrapper> items;
    
    public static String builder;

    private static Inventory mainInv;
    
    private static Inventory mapInv;

    public MapBuilder()
    {
        this.items = new ArrayList<ItemWrapper>();
        builder = "Main";
        mainInv = Bukkit.createInventory(null, 9, "Map Builder");
        mapInv = Bukkit.createInventory(null, 9, "Map Builder");
        addItemMInv(ItemWrapper.SETLOBBY);
        this.addItemMInv(ItemWrapper.GOTOLOBBY);
        this.addItemMInv(ItemWrapper.LOADWORLD);
        this.addItemMapInv(ItemWrapper.SETWRGB);
        this.addItemMapInv(ItemWrapper.SETWCMY);
        this.addItemMapInv(ItemWrapper.SETMRGB);
        this.addItemMapInv(ItemWrapper.SETMCMY);
        this.addItemMapInv(ItemWrapper.SETSPAWNS);
        this.addItemMapInv(ItemWrapper.TELEPORTTOWORLD);
        this.addItemMapInv(ItemWrapper.SAVECONFIG);
        this.items.add(ItemWrapper.SETRGBSPAWN);
        this.items.add(ItemWrapper.SETCMYSPAWN);
    }

    private void addItemMInv(ItemWrapper wrapper)
    {
        this.items.add(wrapper);
        mainInv.addItem(wrapper.toItemStack());
    }
    
    private void addItemMapInv(ItemWrapper wrapper)
    {
        this.items.add(wrapper);
        mapInv.addItem(wrapper.toItemStack());
    }

    @EventHandler
    public void onPlayerClickEvent(InventoryClickEvent e)
    {
        if(e.getInventory() == null && e.getCurrentItem() == null)
        {
            return;
        }
        if(!e.getInventory().getName().equals("Map Builder"))
        {
            return;
        }
        if(e.getCurrentItem() == null)
        {
        	return;
        }
        if(e.getCurrentItem().getType() == Material.AIR)
        {
            return;
        }
        for(ItemWrapper i : items)
        {
        	if(i.toItemStack().equals(e.getCurrentItem()))
        	{
        		if(i.getType() == ItemType.CLICK)
        		{
        			e.setCancelled(true);
        			i.getFunction().func((Player) e.getWhoClicked(), null);
        		}else
        		{
        			e.getWhoClicked().getInventory().setItemInHand(i.toItemStack());
        			e.getWhoClicked().closeInventory();
        		}
        	}
        }
    }

    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
    	
    	// Now the real thing.
    	if(e.getItem() != null && e.getItem().getType() != Material.AIR)
    	{
    		// Player p = e.getPlayer();
    		// p.sendMessage("Not null.");
    		ItemStack i = e.getItem();
    		if(i.hasItemMeta() && i.getItemMeta().hasDisplayName())
    		{
    			// p.sendMessage("Has meta and name.");
    			for(ItemWrapper wrapper : items)
    			{
    				// p.sendMessage("Everyday i'm cyclin'");
    				if(i.getItemMeta().getDisplayName().equals(wrapper.toItemStack().getItemMeta().getDisplayName()))
    				{
    					// p.sendMessage("Found a match.");
    					if(wrapper.getType() == ItemType.INTERACT)
    					{
    						// p.sendMessage("Is interact type.");
    						if(e.getAction() == wrapper.getAction())
    						{
    							wrapper.getFunction().func(e.getPlayer(), e.getClickedBlock());
    						}
    						// p.sendMessage("Function executed?");
    					}
    				}
    			}
    		}
    	}
    }

    public static Inventory getMainInv()
    {
        return mainInv;
    }
    
    public static Inventory getMapInv()
    {
    	return mapInv;
    }
}
