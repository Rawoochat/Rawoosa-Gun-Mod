package rawoochat.yetanothergunmod.items;

public enum ItemNames {
    PISTOL_AMMO_FMJ("pistol_ammo_fmj"), PISTOL_AMMO_AP("pistol_ammo_ap"), PISTOL_AMMO_HP("pistol_ammo_hp");


    private String path;

    ItemNames(String itemPath) {
        this.path = itemPath;
    }

    public String getPath() {
        return path;
    }
}
