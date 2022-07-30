package me.kyrobi;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Boolean disableSign = true;
    private Boolean disableRename = true;
    private Boolean disableBook = true;

    private String disableSignMessage = "";
    private String disableRenameMessage = "";
    private String disableBookMessage = "";


    public void onEnable(){
        this.saveDefaultConfig();

        disableSign = this.getConfig().getBoolean("block-signs");
        disableRename = this.getConfig().getBoolean("block-anvil-rename");
        disableBook = this.getConfig().getBoolean("block-book-editing");

        disableSignMessage = this.getConfig().getString("block-sign-message");
        disableRenameMessage = this.getConfig().getString("block-renaming-message");
        disableBookMessage = this.getConfig().getString("block-book-editing-message");

        new NoMuteBypass(this);
    }

    @Override
    public void onDisable(){
        NoMuteBypass.bannedBlocks.clear();
        NoMuteBypass.mutedPlayersUUID.clear();
    }

    public Boolean allowSign(){
        return disableSign;
    }

    public Boolean allowRename(){
        return disableRename;
    }

    public Boolean allowBook(){
        return disableBook;
    }

    public String blockSignMessage(){
        return disableSignMessage;
    }

    public String blockRenameMessage(){
        return disableRenameMessage;
    }

    public String blockBookMessage(){
        return disableBookMessage;
    }
}
