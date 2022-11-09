package me.daniel.jsmoduler;

import static me.daniel.jsmoduler.JsModuler.plugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredListener;

public class Events implements Listener {
    
//    public void invTeste(PlayerJoinEvent e) {
//        e.getPlayer().getInventory().getItem(0)
//    }
    
    public void register() {
        RegisteredListener registeredListener = new RegisteredListener(this, (listener, event) -> ModulesExecuter.run(event.getEventName(), event), EventPriority.LOWEST, plugin, false);
        for (HandlerList handler : HandlerList.getHandlerLists())
            handler.register(registeredListener);
    }
    
}