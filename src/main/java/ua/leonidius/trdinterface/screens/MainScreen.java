/*
 * A main screen of the plugin, contains two buttons
 * for opening buying and selling menus.
 * Created by Leonidius20 on 26.06.18.
 * This class is a part of "Trading Interface".
 */
package ua.leonidius.trdinterface.screens;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.buy.BuyCategoriesScreen;
import ua.leonidius.trdinterface.sell.SellItemSelectorScreen;

public class MainScreen extends FormWindowSimple implements Screen {

    private int shopId;

    public MainScreen(int shopId) {
        super(Message.WDW_MAIN_TITLE.getText(), "");

        this.shopId = shopId;

        addButton(new ElementButton(Message.MENU_BUY.getText()));
        addButton(new ElementButton(Message.MENU_SELL.getText()));

        /*if (hasPermission) {
            //addButton(new ElementButton(Message.MENU_EDIT_BUY_LIST.getText()));
            //addButton(new ElementButton(Message.MENU_EDIT_SELL_LIST.getText()));
            addButton(new ElementButton(Message.MENU_CUSTOM_NAMES.getText()));
        }*/
        // TODO: if we remove the custom translation of names, we can remove the hasPermission argument from the constructor
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        int clickedButtonId = getResponse().getClickedButtonId();
        boolean hasPermission = event.getPlayer().hasPermission("shop.edit");
        switch (clickedButtonId) {
            case 0:  // Buy button
                event.getPlayer().showFormWindow(new BuyCategoriesScreen(shopId, hasPermission));
                // TODO: pass the shop name as argument
                break;
            case 1:  // Sell button
                //event.getPlayer().showFormWindow(new SellItemSelectorScreen(event.getPlayer().getInventory()));
                // TODO: uncomment and fix
                break;
            case 2:
                event.getPlayer().showFormWindow(new CustomNamesScreen());
                break;
            /*case 2:  // Edit buyable items button
                event.getPlayer().showFormWindow(new EditCategoriesScreen(shopId));
                break;
            case 3:  // Edit sellable items button
                event.getPlayer().showFormWindow(new EditSellListScreen(shopId));
                break;
            case 4:  // Custom item names
                event.getPlayer().showFormWindow(new CustomNamesScreen(shopId));
                // TODO: remove that feature
                break;
                */
        }
    }

}