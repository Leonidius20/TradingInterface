package ua.leonidius.trdinterface.utils;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;

public class EnchantmentWrapper {

    private final Enchantment enchantment;

    public EnchantmentWrapper(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public String getName() {
        return enchantment.getName() + " " + enchantment.getLevel();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnchantmentWrapper)) return false;
        EnchantmentWrapper that = (EnchantmentWrapper) o;
        return enchantment.getId() == that.enchantment.getId() &&
                enchantment.getLevel() == that.enchantment.getLevel();
    }

    public static EnchantmentWrapper[] getWrappers(Item item) {
        Enchantment[] enchantments = item.getEnchantments();
        EnchantmentWrapper[] wrappers = new EnchantmentWrapper[enchantments.length];
        for (int i = 0; i < enchantments.length; i++) {
            wrappers[i] = new EnchantmentWrapper(enchantments[i]);
        }
        return wrappers;
    }

}
