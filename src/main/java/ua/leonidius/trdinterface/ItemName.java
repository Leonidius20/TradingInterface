package ua.leonidius.trdinterface;

import cn.nukkit.item.Item;

/**
 * Created by Leonidius20 on 11.03.17.
 */
public class ItemName {

    public static String get (String stringId) {

        if (Trading.customNames.exists(stringId)) {
            return Trading.customNames.getString(stringId);
        }

        Item item = Item.fromString(stringId);
        if (Trading.customNames.exists(item.getId()+"-"+item.getDamage())) {
            return Trading.customNames.getString(item.getId() + "-" + item.getDamage());
        }

        int id = item.getId();

        if (id == 356){
            return "Repeater";
        } else if (id == 358){
            return "Map";
        } else if (id == 370){
            return "Ghast Tear";
        } else if (id == 376){
            return "Fermented Spider Eye";
        } else if (id == 23){
            return "Dispencer";
        } else if (id == 29){
            return "Sticky Piston";
        } else if (id == 125){
            return "Dropper";
        } else if (id == 154){
            return "Hopper";
        } else if (id == 160){
            return "Stained Glass Pane";
        } else if (id == 200){
            return "Chorus Flower";
        } else if (id == 208){
            return "End Rod";
        } else if (id == 209){
            return "End Gateway";
        } else if (id == 240){
            return "Chorus Plant";
        } else if (id == 247){
            return "Nether Reactor";
        } else if (id == 251){
            return "Observer";
        } else if (id == 377){
            return "Blaze Powder";
        } else if (id == 378){
            return "Magma Cream";
        } else if (id == 385){
            return "Fire Charge";
        } else if (id == 395){
            return "Empty map";
        } else if (id == 396){
            return "Golden Carrot";
        } else if (id == 404){
            return "Comparator";
        } else if (id == 410){
            return "Hopper";
        } else if (id == 420){
            return "Lead";
        } else if (id == 421){
            return "Name Tag";
        } else if (id == 432){
            return "Chorus Fruit";
        } else if (id == 433){
            return "Popped Chorus Fruit";
        } else if (id == 441){
            return "Lingering Potion";
        } else if (id == 445){
            return "Shulker Shell";
        } else if (id == 130){
            return "Ender Chest";
        } else if (id == 392){
            return "Potato";
        } else {
            return item.getName();
        }

    }
}