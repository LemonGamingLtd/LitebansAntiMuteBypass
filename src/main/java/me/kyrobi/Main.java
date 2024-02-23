package me.kyrobi;

import me.nahu.scheduler.wrapper.FoliaWrappedJavaPlugin;

public class Main extends FoliaWrappedJavaPlugin {

    private Boolean disableSign = true;
    private Boolean disableRename = true;
    private Boolean disableBook = true;
    private Boolean disableCommands = true;

    private String disableSignMessage = "";
    private String disableRenameMessage = "";
    private String disableBookMessage = "";
    private String disableCommandsMessage = "";


    public void onEnable(){
        this.saveDefaultConfig();

        disableSign = this.getConfig().getBoolean("block-signs");
        disableRename = this.getConfig().getBoolean("block-anvil-rename");
        disableBook = this.getConfig().getBoolean("block-book-editing");
        disableCommands = this.getConfig().getBoolean("block-commands", true);

        disableSignMessage = this.getConfig().getString("block-sign-message");
        disableRenameMessage = this.getConfig().getString("block-renaming-message");
        disableBookMessage = this.getConfig().getString("block-book-editing-message");
        disableCommandsMessage = this.getConfig().getString("block-commands-message", "&cYou can't execute this command when muted!");

        new NoMuteBypass(this);
    }

    @Override
    public void onDisable(){
        NoMuteBypass.bannedBlocks.clear();
        NoMuteBypass.mutedPlayersUUID.clear();
    }

    public Boolean disableSign(){
        return disableSign;
    }

    public Boolean disableRename(){
        return disableRename;
    }

    public Boolean disableBook(){
        return disableBook;
    }

    public Boolean disableCommands() {
        return disableCommands;
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

    public String blockCommandsMessage() {
        return disableCommandsMessage;
    }
}
