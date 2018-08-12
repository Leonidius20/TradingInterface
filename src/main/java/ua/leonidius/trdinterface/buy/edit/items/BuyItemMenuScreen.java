package ua.leonidius.trdinterface.buy.edit.items;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.ConfigSection;
import ua.leonidius.trdinterface.ItemName;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.buy.Buy;
import ua.leonidius.trdinterface.buy.edit.categories.EditCategoryScreen;
import ua.leonidius.trdinterface.screens.Screen;

import static ua.leonidius.trdinterface.Trading.buyCfg;

/**
 * Created by Leonidius20 on 10.08.18.
 */
public class BuyItemMenuScreen extends FormWindowSimple implements Screen {

    String categoryId, key;

    public BuyItemMenuScreen(String categoryId, String key) {
        super(Message.WDW_EDIT_ITEM_TITLE.getText(), "");
        this.categoryId = categoryId;
        this.key = key;

        addButton(new ElementButton(Message.BTN_BACK.getText()));
        addButton(new ElementButton(Message.BTN_EDIT_ITEM.getText()));
        addButton(new ElementButton(Message.BTN_DELETE_ITEM.getText()));

        String itemName = ItemName.get(key.replace("-", ":"));

        ConfigSection itemSection = buyCfg.getSection(categoryId).getSection("items").getSection(key);
        double price = itemSection.getDouble("price");

        if (itemSection.exists("discount")) {
            addButton(new ElementButton(Message.BTN_EDIT_DISCOUNT.getText()));
            int discount = itemSection.getInt("discount");
            double priceWithDiscount = Buy.getPrice(categoryId, key.replace("-", ":"));
            setContent(Message.BUY_ITEM_INFO_DISC.getText(itemName, price, Trading.settings.currency, discount, priceWithDiscount, Trading.settings.currency));
        } else {
            addButton(new ElementButton(Message.BTN_ADD_DISCOUNT.getText()));
            setContent(Message.BUY_ITEM_INFO.getText(itemName, price, Trading.settings.currency, Message.BTN_NO.getText()));
        }
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        int buttonId = getResponse().getClickedButtonId();
        Player player = event.getPlayer();
        if (buttonId == 0) { // Back
            player.showFormWindow(new EditCategoryScreen(categoryId));
        } else if (buttonId == 1) { // Edit item
            player.showFormWindow(new EditBuyItemScreen(categoryId, key));
        } else if (buttonId == 2) { // Delete item
            player.showFormWindow(new DeleteItemScreen(categoryId, key));
        } else if (buttonId == 3) { // Edit discount
            player.showFormWindow(new EditDiscountScreen(categoryId, key));
        }
    }
}
