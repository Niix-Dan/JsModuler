/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package me.daniel.jsmoduler;

import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bukkit.Bukkit;
import java.io.*;
import java.nio.charset.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredListener;

public class JsModuler extends JavaPlugin {
    public static JsModuler plugin;
    public String modulespath = getDataFolder() + "/Modules";
    public static List<String> moduleslist = new ArrayList<String>();
    public static List<String> moduleslistfiles = new ArrayList<String>();
    
    @Override
    public void onEnable() {
        plugin = this;
        Properties props = System.getProperties();
        props.setProperty("polyglot.engine.WarnInterpreterOnly", "false");
        prepare();
        try {
            loadModules();
        } catch (IOException ex) {
            Logger.getLogger(JsModuler.class.getName()).log(Level.SEVERE, null, ex);
        }
        Events eventos = new Events();
        eventos.register();
        Bukkit.getPluginManager().registerEvents(eventos, this);
        
        getCommand("jsmoduler").setExecutor(new Commands());
    }
    
    private void prepare() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        
        File modulesFile = new File (getDataFolder(), "config.yml");
        File exampleMod = new File(plugin.getDataFolder(), "Modules");
        
        if(!exampleMod.exists()) {
            exampleMod.mkdirs();
            
            File nf = new File(modulespath, "Example.js");
            if(!nf.exists()) {
                this.saveResource("Modules/Example.js", false);
            }
        }
        if(!modulesFile.exists()) {
            this.saveResource("config.yml", false);
        }
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }
    
    private void loadModules() throws IOException {
        List<String> allmodules = new ArrayList<>(listFilesName(modulespath));
        
        for(String module : allmodules) {
            if(!module.endsWith(".js")) module = module + ".js";
            
            File file = new File(modulespath, module);
            String code = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            
            Bukkit.getConsoleSender().sendMessage("§c[JsModuler] §aLoaded module §e["+module+"]");
            
            moduleslist.add(code);
            moduleslistfiles.add(module);
        }
    }
    
    public Set<String> listFilesName(String dir) {
        return Stream.of(new File(dir).listFiles())
          .filter(file -> file.isFile())
          .filter(file -> file.getName().endsWith(".js"))
          .map(File::getName)
          .collect(Collectors.toSet());
    }
}
