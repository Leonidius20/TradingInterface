package ua.leonidius.trdinterface.utils;

import cn.nukkit.utils.Config;
import ua.leonidius.trdinterface.Trading;

/**
 * Contains methods to add items from the old
 * YAML item lists to the new database, as
 * well as import custom item name translations
 */
public abstract class YamlToDbConverter {

    public static void convertBuyableItems(Config buyableItems) {
        // TODO

        Trading.getPlugin().getLogger().info("");
    }

    public static void convertSellableItems(Config sellableItems) {
        // TODO

        Trading.getPlugin().getLogger().info("");
    }

    public static void convertTranslations(Config translations) {

    }

}
