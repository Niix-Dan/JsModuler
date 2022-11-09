/*
 * @name = "Example"
 * @description = "A example module"
 * 
 * @Handler = "PlayerJoinEvent@onJoin"
 */

function onJoin(event) {
    event.getPlayer().sendMessage("§a§lBem vindo(a) ao servidor!");
}

