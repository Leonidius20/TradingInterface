package ua.leonidius.trdinterface.elements;

import cn.nukkit.form.element.ElementButton;

/**
 * A button, which represents a category of items and holds an id of the category.
 */
public class CategoryButton extends ElementButton {

    private int categoryId;

    public CategoryButton(String categoryName, int categoryId) {
        super(categoryName);
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

}