package com.gmail.lynx7478.ctw.game.roles;

import java.util.ArrayList;

public class RoleManager {
	
	private static ArrayList<Role> roles;
	
	public RoleManager()
	{
		roles = new ArrayList<Role>();
	}
	
	public static ArrayList<Role> getRoles()
	{
		return roles;
	}
	
	public static void registerRole(Role r)
	{
		roles.add(r);
	}

}
