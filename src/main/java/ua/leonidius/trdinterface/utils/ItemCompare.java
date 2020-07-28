package ua.leonidius.trdinterface.utils;

import cn.nukkit.item.Item;

public abstract class ItemCompare {

    /**
     * Comparing two items for equality taking custom names, lores
     * and enchantments into account, but not their count
     *
     * @param item1 first item
     * @param item2 second item
     * @return whether the two items are equal or not
     */
    public static boolean equals(Item item1, Item item2) {
        boolean initialTest = item1.getId() == item2.getId()
                && item1.getDamage() == item2.getDamage()
                && item1.getCustomName().equals(item2.getCustomName());
        if (!initialTest) return false;

        EnchantmentWrapper[] wrappers1 = EnchantmentWrapper.getWrappers(item1);
        EnchantmentWrapper[] wrappers2 = EnchantmentWrapper.getWrappers(item2);

        if (wrappers1 == wrappers2) return true;

        if (wrappers1.length != wrappers2.length) return false;

        for (EnchantmentWrapper w1 : wrappers1) {
            boolean secondArrayContains = false;
            for (EnchantmentWrapper w2 : wrappers2) {
                if (w1.equals(w2)) {
                    secondArrayContains = true;
                    break;
                }
            }
            if (!secondArrayContains) {
                return false;
            }
        }

        return true;
    }

}
