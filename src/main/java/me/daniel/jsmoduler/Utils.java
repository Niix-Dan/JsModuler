/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.daniel.jsmoduler;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import static me.daniel.jsmoduler.JsModuler.plugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 *
 * @author battl
 */
public class Utils {
    public HashMap<String,Object> cache = new HashMap<String,Object>();
    
    public Object readFile(String path) throws IOException {
        Path filepath = null;
        if(path.startsWith(".") || path.startsWith("/")) filepath = Paths.get(plugin.getDataFolder().getAbsolutePath(), path);
        else filepath = Paths.get(path);
        
        try {
            Object file = Files.readString(filepath);
            return file;
        } catch(IOException ex) {
            return null;
        }
    }
    
    public void writeFile(String path, String content) throws IOException {
        Path filepath = null;
        if(path.startsWith(".") || path.startsWith("/")) filepath = Paths.get(plugin.getDataFolder().getAbsolutePath(), path);
        else filepath = Paths.get(path);
        
            File file = filepath.toFile();
        
            if(!file.exists()) file.createNewFile();

            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
            writer.write(content);
            writer.close();
    }
    public ItemStack ItemStack(String material) {
        return new ItemStack(Material.getMaterial(material.toUpperCase()));
    }
    public ItemStack ItemStack(Material material) {
        return new ItemStack(material);
    }
    public Material Material(String material) {
        return Material.getMaterial(material.toUpperCase());
    }
    public Inventory createInventory(int size, String name) {
        return Bukkit.createInventory(null, size, name);       
    }
    public SkullMeta SkullMeta(ItemStack item) {
        return (SkullMeta) item.getItemMeta(); 
    }
    
    public void setCache(String key, Object elemnt) {
        cache.put(key, elemnt);
    }
    public void delCache(String key) {
        cache.remove(key);
    }
    public Object getCache(String key) {
        return cache.get(key);
    }
}
