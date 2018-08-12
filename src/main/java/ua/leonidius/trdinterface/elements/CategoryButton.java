/*
 * A button, which represents a category of items and stores a string id of the category.
 * Created by Leonidius20 on 26.06.18.
 * This class is a part of "Trading Interface".
 */
package ua.leonidius.trdinterface.elements;

import cn.nukkit.form.element.ElementButton;

public class CategoryButton extends ElementButton {

    private String categoryId;

    public CategoryButton(String text, String categoryId) {
        super(text);
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }
}

