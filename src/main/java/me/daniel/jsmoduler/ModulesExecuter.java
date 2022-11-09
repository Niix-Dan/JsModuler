/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.daniel.jsmoduler;

import com.oracle.truffle.js.scriptengine.GraalJSScriptEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import static me.daniel.jsmoduler.JsModuler.plugin;
import org.bukkit.Bukkit;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.graalvm.polyglot.Context;

/**
 *
 * @author battl
 */
public class ModulesExecuter {
    public static Utils utils = new Utils();
    public static List<String> getModules(String event) {
        List<String> mods = JsModuler.moduleslist;
        List<String> validModules = new ArrayList<String>();
        
        
        for(String module : mods) {
            for(String handler : module.split("@Handler")[1].split("\"")[1].split(";")) {
                if(handler.split("@")[0].equals(event)) {
                    validModules.add(module);
                }
            }
        }
        
        
        return validModules;
    }
    
    public static void run(String EventName, Object Event) {
        try {
            EventRun(EventName, Event);
        } catch(Exception ex) {
            Bukkit.getConsoleSender().sendMessage("§c[JsModuler] §eError on trying to run event §a" + EventName + "§e.");
            System.out.println("§c"+ex.getMessage());
        }
    }
    
    public static void EventRun(String EventName, Object Event) throws Exception {
        List<String> modules = getModules(EventName);
        
        for(String module : modules) {
            String Run = "";
            for(String handler : module.split("Handler")[1].split("\"")[1].split(";")) {
                if(handler.split("@")[0].equals(EventName) && Run.isBlank()) {
                    Run = handler.split("@")[1];
                }
            }
            
            ScriptEngine engine = GraalJSScriptEngine.create(null,
                Context.newBuilder("js")
                    .allowAllAccess(true));
            
            
            engine.put("UTILS", utils);
            engine.put("SYSOUT", System.out);
            engine.put("PLUGIN", plugin);
            engine.put("SERVER", plugin.getServer());
            
            
            
            engine.eval(module);

            Invocable inv = (Invocable) engine;
            inv.invokeFunction(Run, Event);
        }
    }
}

