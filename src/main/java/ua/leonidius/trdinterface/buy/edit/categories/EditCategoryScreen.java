package ua.leonidius.trdinterface.buy.edit.categories;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.buy.edit.items.AddBuyItemScreen;
import ua.leonidius.trdinterface.buy.edit.items.BuyItemMenuScreen;
import ua.leonidius.trdinterface.elements.ItemButton;
import ua.leonidius.trdinterface.screens.Screen;

import static ua.leonidius.trdinterface.Trading.buyCfg;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class EditCategoryScreen extends FormWindowSimple implements Screen {

    String categoryId;

    public EditCategoryScreen(String categoryId) {
        super(buyCfg.getSection(categoryId).getString("name"), "");

        this.categoryId = categoryId;

        addButton(new ElementButton(Message.BTN_BACK.getText()));
        addButton(new ElementButton(Message.BTN_RENAME_CATEGORY.getText()));
        addButton(new ElementButton(Message.BTN_DELETE_CATEGORY.getText()));
        addButton(new ElementButton(Message.BTN_ADD_ITEM.getText()));

        for (String key : buyCfg.getSection(categoryId).getSection("items").getKeys(false)) {
            String id = key.replace("-", ":");
            addButton(new ItemButton(id));
        }
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        if (getResponse().getClickedButtonId() == 0) { // Back
            player.showFormWindow(new EditCategoriesScreen());
        } else if (getResponse().getClickedButtonId() == 1) { // Rename
            player.showFormWindow(new RenameCategoryScreen(categoryId));
        } else if (getResponse().getClickedButtonId() == 2) { // Delete
            player.showFormWindow(new DeleteCategoryScreen(categoryId));
        } else if (getResponse().getClickedButtonId() == 3) { // Add item
            player.showFormWindow(new AddBuyItemScreen(categoryId));
        } else { // Edit item
            ItemButton button = (ItemButton)getResponse().getClickedButton();
            player.showFormWindow(new BuyItemMenuScreen(categoryId, button.getStringId().replace(":", "-")));
        }
    }
}
