package ua.leonidius.trdinterface;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Location;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;

import static ua.leonidius.trdinterface.Trading.settings;


public enum Message {

    //LNG_LOAD_FAIL("Failed to load languages from file. Default message used"),
    LNG_SAVE_FAIL("Failed to save lang file"),
    LNG_PRINT_FAIL("Failed to print message to 'null': \"%1%\""),

    // Commands
    CMD_CONSOLE ("You cannot use this command in console."),
    CMD_SHOP ("Opens the shop interface"),

    // Main menu
    WDW_MAIN_TITLE ("Main menu"),
    MENU_BUY ("Buy items"),
    MENU_SELL ("Sell items"),
    MENU_EDIT_BUY_LIST ("Edit buyable items"),
    MENU_EDIT_SELL_LIST ("Edit sellable items"),
    MENU_CUSTOM_NAMES ("Custom item names"),

    // Buttons
    BTN_BACK ("< Back"),

    // Modal windows
    BTN_YES ("Yes"),
    BTN_NO ("No"),

    // Custom names windows
    BTN_EDIT_CUSTOM_NAME ("* Edit custom name"),
    BTN_DELETE_CUSTOM_NAME ("* Delete custom name"),
    BTN_ADD_CUSTOM_NAME ("+ Add custom name"),

    // Success and fail
    WDW_SUCCESS_TITLE ("Success"),
    WDW_FAIL_TITLE ("Fail"),

    // Category selector window
    WDW_BUY_CAT_TITLE ("Choose a category"),
    WDW_BUY_NO_CATEGORIES ("There are no categories of goods in the shop. In order to use the shop, there must be at least one category."),

    // Buy item selector window
    WDW_BUY_EMPTY_CAT ("There are no items in this category."),
    BTN_ITEM_BUY ("%1% (%2%%3%)"),
    BTN_ITEM_BUY_SALE ("%1% (%2%%3%) &c-%4%%!"),

    // Buy window & BuyManageItemScreen
    WDW_BUY_TITLE ("Buying %1%"),
    WDW_BUY_NAME ("Name: %1%"),
    WDW_BUY_ORIGINAL_NAME ("Original name: %1% (%2%:%3%)"),
    WDW_BUY_PRICE ("Price: %1%%2% per item"),
    WDW_BUY_DISCOUNT ("Discount: %1%%\nPrice with discount: %2%%3%"),
    WDW_BUY_CUSTOM_LORE ("Lore: %1%"),
    WDW_BUY_ENCHANTMENTS ("Enchantments: %1%"),
    WDW_BUY_ENCHANTMENTS_ABSENT ("none"),
    WDW_BUY_AMOUNT ("Select amount"),

    // Sell item selector window
    WDW_SELL_SELECT_ITEM ("Select an item to sell"),
    WDW_SELL_NOTHING ("You have nothing to sell."),
    BTN_ITEM_SELL ("%1% (x%2%)"),

    // Sell window
    WDW_SELL_TITLE ("Selling %1%"),

    // Categories list window
    BTN_ADD_CATEGORY ("+ Add category"),

    // Category creation window
    WDW_NEW_CATEGORY ("Creating a new category"),
    WDW_NEW_CATEGORY_NAME ("Choose a name:"),
    //WDW_NEW_CATEGORY_ID ("Choose a unique ID (string):"),
    WDW_NEW_CATEGORY_FAIL ("A category with such a name already exists."),
    WDW_NEW_CATEGORY_SUCCESS ("A new category has been created successfully."),

    // Category renaming window
    WDW_RENAME_CATEGORY_TITLE ("Renaming category"),
    WDW_RENAME_CATEGORY_NAME ("Choose a new name:"),

    // Category editing window
    BTN_RENAME_CATEGORY ("* Rename category"),
    BTN_DELETE_CATEGORY ("* Delete category"),
    BTN_ADD_ITEM ("+ Add item"),

    // Category deletion window
    WDW_DELETE_CATEGORY_TITLE ("Deleting category"),
    WDW_DELETE_CATEGORY_CONF ("Are you sure you want to delete %1% category? This action can't be undone."),

    // Adding item windows
    WDW_ADD_ITEM_TITLE ("Adding item"),
    WDW_ADD_ITEM_ID ("Item's ID (string or number)"),
    WDW_ADD_ITEM_PRICE ("Price per item"),
    WDW_ADD_ITEM_CUSTOM_NAME ("Custom name (optional)"),
    WDW_ADD_ITEM_CUSTOM_LORE ("Custom lore (optional)"),
    WDW_ADD_ITEM_ENCHANTMENTS ("You will be able to add enchantments to the item later."),
    WDW_ADD_BUY_ITEM_SUCCESS ("You have successfully added %1% to the list of buyable items."),
    WDW_ADD_SELL_ITEM_SUCCESS ("You have successfully added %1% to the list of sellable items."),
    WDW_INVALID_PARAMS ("Invalid parameters."),
    WDW_ITEM_ALREADY_EXISTS ("This item is already on the list."),

    // Item editing windows
    WDW_EDIT_ITEM_TITLE ("Editing item"),
    //BUY_ITEM_INFO ("Item: %1%\nPrice: %2%%3%\nDiscount: %4%%"),
    //BUY_ITEM_INFO_DISC ("Item: %1%\nPrice: %2%%3%\nDiscount: %4%%\nPrice with discount: %5%%6%"),
    SELL_ITEM_INFO ("Item: %1%\nPrice: %2%%3%"),
    BTN_EDIT_ITEM ("* Edit item"),
    BTN_DELETE_ITEM ("* Delete item"),
    BTN_EDIT_DISCOUNT ("* Edit discount"),
    BTN_ADD_DISCOUNT ("+ Add discount"),

    // AddEnchantmentFailScreen
    WDW_ADD_ENCHANTMENT_FAIL("Failed to add enchantment."),

    // BuyManageItemScreen
    BTN_BUY_ITEM ("Buy this item"),
    BTN_ADD_ENCHANTMENT ("Add enchantment"),
    BTN_REMOVE_ENCHANTMENT ("Remove enchantment"),

    // Item deletion windows
    WDW_DELETE_ITEM_TITLE ("Deleting item"),
    WDW_DELETE_BUY_ITEM_CONF ("Are you sure you want to delete %1% from the list of buyable items? This action can't be undone."),
    WDW_DELETE_SELL_ITEM_CONF ("Are you sure you want to delete %1% from the list of sellable items? This action can't be undone."),

    // Discount editing window
    WDW_EDIT_DISCOUNT_TITLE ("Editing discount"),
    WDW_EDIT_DISCOUNT_HINT ("Enter discount (in percents). Type 0 to remove discount."),
    WDW_EDIT_DISCOUNT_MORE_THAN_100 ("Discount can't be bigger than 100%."),

    // Custom names list window
    WDW_CUSTOM_NAMES_HINT ("You can add a custom name to an item. It will appear instead of the original one."),
    WDW_CUSTOM_NAME_TITLE ("Custom item name"),

    // Custom name editing menu
    WDW_CUSTOM_NAME_INFO ("Item's ID: %1%\nOriginal name: %2%\nCustom name: %3%"),

    // Custom name adding window
    WDW_ADD_CUSTOM_NAME_TITLE ("Adding custom name"),
    WDW_ADD_CUSTOM_NAME_FAIL ("There is already a custom name for this item."),

    // Custom name editing window
    WDW_EDIT_CUSTOM_NAME_TITLE ("Editing custom name"),
    WDW_CUSTOM_NAME_HINT ("Item's custom name:"),

    // Custom name deletion window
    WDW_DELETE_CUSTOM_NAME_TITLE ("Deleting custom name"),
    WDW_DELETE_CUSTOM_NAME_CONF ("Are you sure you want to delete a custom name for an item? This action can't be undone."),

    // Buy success and fail strings
    BUY_NO_MONEY ("Not enough money to buy this item."),
    BUY_NO_SPACE ("There is no free space in your inventory."),
    BUY_NO_SPACE_AND_MONEY ("There is no free space in your inventory and there is not enough money to buy this item."),
    ERROR ("An error occurred."),
    BUY_SUCCESS ("You have successfully bought %1%x %2% for %3%%4%."),

    // Sell success string
    SELL_SUCCESS ("You have successfully sold %1%x %2% for %3%%4%."),

    // List edit logs
    LOG_CATEGORY_ADDED ("User %1% has added a new category of buyable items called %2%."),
    LOG_CATEGORY_DELETED ("User %1% has deleted a category of buyable items called %2%."),
    LOG_CATEGORY_RENAMED ("User %1% has renamed a category of buyable items called %2% into %3%."),

    LOG_BUY_ITEM_ADDED ("User %1% has added %2% (ID: %3%) to the list of buyable items, price: %4%%5%."),
    LOG_BUY_ITEM_ADDED_WITH_CUSTOM_NAME ("User %1% has added %2% (original name: %3%, ID: %4%) to the list of buyable items, price: %5%%6%."),
    LOG_BUY_ITEM_DELETED ("User %1% has deleted %2% (ID: %3%) from the list of buyable items."),
    LOG_BUY_ITEM_EDITED ("User %1% has changed the price of %2% (ID: %3%) for buying, new price: %4%%5%, old price: %6%%7%."),

    LOG_DISCOUNT_ADDED ("User %1% has added %2%% discount for %3% (ID: %4%)."),
    LOG_DISCOUNT_EDITED ("User %1% has changed the discount for %2% (ID: %3%), new discount: %4%%, old discount: %5%%."),
    LOG_DISCOUNT_DELETED ("User %1% has deleted the discount for %2% (ID: %3%)."),

    LOG_SELL_ITEM_ADDED ("User %1% has added %2% (ID: %3%) to the list of sellable items, price: %4%%5%."),
    LOG_SELL_ITEM_DELETED ("User %1% has deleted %2% (ID: %3%) from the list of sellable items."),
    LOG_SELL_ITEM_EDITED ("User %1% has changed the price of %2% (ID: %3%) for selling, new price: %4%%5%, old price: %6%%7%."),

    LOG_BUY_ENCHANTMENT_ADDED ("User %1% has added %2% %3% enchantment to a buyable item %4% (%5%:%6%)"),
    LOG_SELL_ENCHANTMENT_ADDED ("User %1% has added %2% %3% enchantment to a sellable item %4% (%5%:%6%)"),
    LOG_BUY_ENCHANTMENT_REMOVED ("User %1% has removed %2% %3% enchantment from a buyable item %4% (%5%:%6%)"),

    // Transaction logs
    LOG_BOUGHT ("Player %1% have bought %2%x %3% (ID: %4%) for %5%%6%."),
    LOG_SOLD ("Player %1% have sold %2%x %3% (ID: %4%) for %5%%6%.");

    private static String language = "english";
    private static char c1; //= 'a';
    private static char c2; //= '2';
    private static boolean saveLanguage = false;

    private static PluginBase plugin = null;

    public boolean log(Object... s) {
        plugin.getLogger().info(getText(s));
        return true;
    }

    public boolean print(CommandSender sender, Object... s) {
        if (sender == null) return Message.LNG_PRINT_FAIL.log(this.name());
        sender.sendMessage(getText(s));
        return true;
    }

    public void printError(CommandSender sender, Object... s) {
        print (sender, s, 'c', 'c');
    }

    public void tip (Player player, Object... s) {
        if (player!=null) player.sendTip(getText(s));
    }

    public void popup (Player player, Object... s) {
        if (player!=null) player.sendPopup(getText(s));
    }

    public boolean broadcast(String permission, Object... s) {
        for (Player player : plugin.getServer().getOnlinePlayers().values()) {
            if (permission == null || player.hasPermission(permission)) print(player, s);
        }
        return true;
    }

    public String getText(Object... keys) {
        if (keys.length == 0) return TextFormat.colorize(this.message);
        String str = this.message;
        boolean fullFloat = false;
        int count = 1;
        int c = 0;
        DecimalFormat fmt = new DecimalFormat("####0.##");
        for (int i = 0; i < keys.length; i++) {
            String s = keys[i].toString();
            if (s.equals("FULLFLOAT")) {
                fullFloat = true;
                continue;
            } else if (keys[i] instanceof Location) {
                Location loc = (Location) keys[i];
                if (fullFloat)
                    s = loc.getLevel().getName() + "[" + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + "]";
                else
                    s = loc.getLevel().getName() + "[" + fmt.format(loc.getX()) + ", " + fmt.format(loc.getY()) + ", " + fmt.format(loc.getZ()) + "]";
            } else if (keys[i] instanceof Double || keys[i] instanceof Float) {
                if (!fullFloat) s = fmt.format(keys[i]);
            }

            String from = (new StringBuilder("%").append(count).append("%")).toString();
            String to = s;
            str = str.replace(from, to);
            count++;
        }

        return TextFormat.colorize(str);
    }

    private void initMessage(String message) {
        this.message = message;
    }

    private String message;
    private Character color1;
    private Character color2;

    Message(String msg) {
        message = msg;
        this.color1 = null;
        this.color2 = null;
    }

    Message(String msg, char color1, char color2) {
        this.message = msg;
        this.color1 = color1;
        this.color2 = color2;
    }

    Message(String msg, char color) {
        this(msg, color, color);
    }

    @Override
    public String toString() {
        return this.getText("NOCOLOR");
    }

    public static void init(PluginBase plg) {
        plugin = plg;
        language = settings.language;
        if (language.equalsIgnoreCase("default")) language = Server.getInstance().getLanguage().getLang();
        else if (language.length() > 3) language = language.substring(0, 3);
        saveLanguage = settings.save_translation;

        initMessages();
        if (saveLanguage) saveMessages();
        c1 = 'a';
        c2 = '2';
    }

    private static void initMessages() {
        File f = new File(plugin.getDataFolder() + File.separator + language + ".lng");
        Config lng;
        if (!f.exists()) {
            lng = new Config(f, Config.YAML);
            InputStream is = plugin.getClass().getResourceAsStream("/lang/" + language + ".lng");
            lng.load(is);
            if (!f.delete()) {
                System.gc();
                f.delete();
            }
        } else lng = new Config(f, Config.YAML);
        for (Message key : Message.values())
            key.initMessage(lng.getString(key.name().toLowerCase(), key.message));
    }

    private static void saveMessages() {
        File f = new File(plugin.getDataFolder() + File.separator + language + ".lng");
        Config lng = new Config(f, Config.YAML);
        for (Message key : Message.values())
            lng.set(key.name().toLowerCase(), key.message);
        try {
            lng.save();
        } catch (Exception e) {
            LNG_SAVE_FAIL.log();
        }
    }

    /*public static String join(Object... s) {
        StringBuilder sb = new StringBuilder();
        for (Object o : s) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(o.toString());
        }
        return sb.toString();
    }*/

}