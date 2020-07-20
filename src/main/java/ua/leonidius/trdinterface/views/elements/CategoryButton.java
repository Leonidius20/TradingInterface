package ua.leonidius.trdinterface.views.elements;

/**
 * A button, which represents a category of items and holds an id of the category.
 */
public class CategoryButton extends CallbackButton {

    private final int categoryId;

    public CategoryButton(String categoryName, int categoryId, Callback callback) {
        super(categoryName, callback);
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

}