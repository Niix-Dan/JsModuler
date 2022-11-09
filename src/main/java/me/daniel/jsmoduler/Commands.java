/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.daniel.jsmoduler;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author battl
 */
public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if(args.length >= 1 && args[0].equalsIgnoreCase("reload") && (sender.hasPermission("jsmoduler.reload") || sender.isOp())) {
            
            if(args.length >= 2) {
                String module = args.length > 2 ? getSliceOfArray(args, 1, args.length - 1).toString() : args[1];
                if(module.equalsIgnoreCase("all")) {
                    try {
                        sender.sendMessage("§c[JsModuler] §aReloading modules...");
                        reloadModules(sender);
                        sender.sendMessage("§c[JsModuler] §aModules reloaded successfully!");
                    } catch (IOException ex) {
                        sender.sendMessage("§c[JsModuler] §cError on reload modules!");
                    }
                } else {
                    try {
                        sender.sendMessage("§c[JsModuler] §aReloading module §c["+module+"]§a!");
                        reloadModule(sender, module);
                    } catch (IOException ex) {
                        sender.sendMessage("§c[JsModuler] §cError on reload module §a["+module+"]§c!");
                    }
                }
            } else {
                sender.sendMessage("/jsm <reload> [<all>:<module-name>]");
            }
        } else {
            sender.sendMessage("/jsm <reload>");
        }
        
        return false;
    }
    public static String[] getSliceOfArray(String[] arr, int start, int end) {
        String [] slice = new String[end - start];
  
        for (int i = 0; i < slice.length; i++) {
            slice[i] = arr[start + i];
        }
        return slice;
    }
    
    private void reloadModule(CommandSender sender, String modulename) throws IOException {
        List<String> allmodules = new ArrayList<>(listFilesName(JsModuler.plugin.modulespath));
        modulename = modulename.endsWith(".js") ? modulename : modulename + ".js";
        
        int index = 0;
        boolean run = false;
        for(String module : JsModuler.moduleslistfiles) {
            if(module.equalsIgnoreCase(modulename)) {
                File file = new File(JsModuler.plugin.modulespath, module);
                String code = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                
                JsModuler.moduleslist.set(index, code);
                Bukkit.getConsoleSender().sendMessage("§c[JsModuler] §aReloaded module §c["+module+"]§a!");
                if (sender instanceof Player) { sender.sendMessage("§c[JsModuler] §aReloaded module §c["+module+"]."); }
                run = true;
            }
            index++;
        }
        
        if(!run) {
            sender.sendMessage("§c[JsModuler] §aNo module named §c["+modulename+"] §awas found!");
        }
    }
    
    private void reloadModules(CommandSender sender) throws IOException {
        List<String> allmodules = new ArrayList<>(listFilesName(JsModuler.plugin.modulespath));
        
        JsModuler.moduleslist = new ArrayList<String>();
        JsModuler.moduleslistfiles = new ArrayList<String>();
        
        for(String module : allmodules) {
            if(!module.endsWith(".js")) module = module + ".js";
            
            File file = new File(JsModuler.plugin.modulespath, module);
            String code = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            
            Bukkit.getConsoleSender().sendMessage("§c[JsModuler] §aReloaded module §c["+module+"]§a!");
            if (sender instanceof Player) { sender.sendMessage("§aReloaded module §c["+module+"]."); }
            
            JsModuler.moduleslist.add(code);
            JsModuler.moduleslistfiles.add(module);
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
