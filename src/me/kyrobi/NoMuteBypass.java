package me.kyrobi;

import litebans.api.Database;
import litebans.api.Entry;
import litebans.api.Events;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class NoMuteBypass implements Listener {

    private Main plugin;

    public static Set<Material> bannedBlocks = new HashSet<Material>();
    public static Set<String> mutedPlayersUUID = new HashSet<String>(); //Hashset has O(1) lookup time compared to arrayList

    public NoMuteBypass(Main plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        //Adds all the signs that can't be placed
        bannedBlocks.add(Material.OAK_SIGN);
        bannedBlocks.add(Material.OAK_WALL_SIGN);

        bannedBlocks.add(Material.SPRUCE_SIGN);
        bannedBlocks.add(Material.SPRUCE_WALL_SIGN);

        bannedBlocks.add(Material.BIRCH_SIGN);
        bannedBlocks.add(Material.BIRCH_WALL_SIGN);

        bannedBlocks.add(Material.JUNGLE_SIGN);
        bannedBlocks.add(Material.JUNGLE_WALL_SIGN);

        bannedBlocks.add(Material.ACACIA_SIGN);
        bannedBlocks.add(Material.ACACIA_WALL_SIGN);

        bannedBlocks.add(Material.DARK_OAK_SIGN);
        bannedBlocks.add(Material.DARK_OAK_WALL_SIGN);

        bannedBlocks.add(Material.MANGROVE_SIGN);
        bannedBlocks.add(Material.MANGROVE_WALL_SIGN);

        bannedBlocks.add(Material.CRIMSON_SIGN);
        bannedBlocks.add(Material.CRIMSON_WALL_SIGN);

        bannedBlocks.add(Material.WARPED_SIGN);
        bannedBlocks.add(Material.WARPED_WALL_SIGN);

        //Register the event that we will listen to when a player is muted
        Events.get().register(new Events.Listener() {
            public void entryAdded(Entry entry) {

                if (entry.getType().equals("mute")){
                    mutedPlayersUUID.add(entry.getUuid());
                }
            }

            public void entryRemoved(Entry entry) {

                if (entry.getType().equals("mute")){
                    mutedPlayersUUID.remove(entry.getUuid());
                }

            }
        });
    }


    //Add the user to the cache upon join if the user is muted
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        new BukkitRunnable() {
            @Override
            public void run() {
                boolean isMuted = Database.get().isPlayerMuted(uuid, null);
                if(isMuted){
                    mutedPlayersUUID.add(uuid.toString());
                }
            }

        }.runTaskAsynchronously(plugin);
    }

    //Remove the user to the cache upon join if the user has left the server
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        mutedPlayersUUID.remove(e.getPlayer().getUniqueId().toString());
    }


    //Block signs from being used
    @EventHandler
    public void onPlayerPlace(BlockPlaceEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();

        if(mutedPlayersUUID.contains(uuid.toString()) && plugin.allowSign()){
            if(bannedBlocks.contains(e.getBlockPlaced().getType())){
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.blockSignMessage()));
                e.setCancelled(true);
            }
        }
    }

    //Block items from being renamed
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e){
        if(e.getInventory().getType() == InventoryType.ANVIL){
            if(e.getSlotType() == InventoryType.SlotType.RESULT){
                if(mutedPlayersUUID.contains(e.getWhoClicked().getUniqueId().toString()) && plugin.allowRename()){
                    e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.blockRenameMessage()));
                    e.setCancelled(true);
                }
            }
        }
    }

    //Block books to be edited
    @EventHandler
    public void onBookEdit(PlayerEditBookEvent e){
        if(mutedPlayersUUID.contains(e.getPlayer().getUniqueId().toString()) && plugin.allowBook()){
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.blockBookMessage()));
            e.setCancelled(true);
        }
    }
}
