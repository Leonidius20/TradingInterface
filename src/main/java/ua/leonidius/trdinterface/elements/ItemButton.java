package ua.leonidius.trdinterface.elements;

import cn.nukkit.form.element.ElementButton;

/**
 * A button which represents an item available for buying and holds item's record id.
 */
public class ItemButton extends ElementButton {

    private int itemId;

    public ItemButton(String text, int itemId) {
        super(text);
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

}