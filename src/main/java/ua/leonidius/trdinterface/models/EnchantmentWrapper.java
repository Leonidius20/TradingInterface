package ua.leonidius.trdinterface.models;

import cn.nukkit.item.enchantment.Enchantment;

import java.util.HashSet;

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

    public static HashSet<EnchantmentWrapper> getWrappers(Enchantment[] enchantments) {
        HashSet<EnchantmentWrapper> set = new HashSet<>();
        for (Enchantment e : enchantments) {
            set.add(new EnchantmentWrapper(e));
        }
        return set;
    }

}
