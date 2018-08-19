package ua.leonidius.trdinterface.buy;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.utils.ConfigSection;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.elements.ItemButton;
import ua.leonidius.trdinterface.screens.Screen;

import static ua.leonidius.trdinterface.Trading.buyCfg;

/**
 * Created by Leonidius20 on 26.06.18.
 */
public class BuyItemSelectorScreen extends FormWindowSimple implements Screen {

    private String categoryId;

    public BuyItemSelectorScreen(String categoryId) {
        super(buyCfg.getSection(categoryId).getString("name"), "");
        this.categoryId = categoryId;

        // Back button
        addButton(new ElementButton(Message.BTN_BACK.getText()));

        for (String key : buyCfg.getSection(categoryId).getSection("items").getKeys(false)) {
            String id = key.replace("-", ":");
            double price = Buy.getPrice(categoryId, id);

            ConfigSection itemSection = buyCfg.getSection(categoryId).getSection("items").getSection(key);

            if (itemSection.exists("discount")) {
                addButton(new ItemButton(id, price, itemSection.getInt("discount")));
            } else {
                addButton(new ItemButton(id, price));
            }
        }

        // If there is only back button
        if (getButtons().size() == 1) {
            setContent(Message.WDW_BUY_EMPTY_CAT.getText());
        }
    }

    public void onResponse(PlayerFormRespondedEvent event) {

        // If back button pressed
        if (!(getResponse().getClickedButton() instanceof ItemButton)) {
            event.getPlayer().showFormWindow(new BuyCategoriesScreen());
            return;
        }

        ItemButton button = (ItemButton)getResponse().getClickedButton();
        // Checking if there is enough place in inventory and money
        Item item = Item.fromString(button.getStringId());

        Player player = event.getPlayer();

        double price = Buy.getPrice(categoryId, button.getStringId());
        int maxByMoney = Buy.getMaxByMoney(player, price);
        int maxByInventory = Buy.getMaxByInventory(player, item);

        if (maxByMoney == 0 && maxByInventory == 0) {
            player.showFormWindow(new BuyFailScreen(0, categoryId));
        } else if (maxByMoney == 0){
            player.showFormWindow(new BuyFailScreen(1, categoryId));
        } else if (maxByInventory == 0) {
            player.showFormWindow(new BuyFailScreen(2, categoryId));
        } else {
            int maxAmount = Math.min(maxByInventory, maxByMoney);
            player.showFormWindow(new BuyScreen(categoryId, button.getStringId(), price, maxAmount));
        }
    }

}
