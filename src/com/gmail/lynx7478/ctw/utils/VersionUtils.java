package com.gmail.lynx7478.ctw.utils;

import org.bukkit.Bukkit;

public class VersionUtils
{
    public static String getVersion()
    {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf(".") + 1);
    }
}
