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
import ua.leonidius.trdinterface.buy.edit.categories.EditCategoriesScreen;
import ua.leonidius.trdinterface.sell.SellItemSelectorScreen;
import ua.leonidius.trdinterface.sell.edit.EditSellListScreen;

public class MainScreen extends FormWindowSimple implements Screen {

    public MainScreen(boolean hasPermission) {
        super(Message.WDW_MAIN_TITLE.getText(), "");
        addButton(new ElementButton(Message.MENU_BUY.getText()));
        addButton(new ElementButton(Message.MENU_SELL.getText()));
        if (hasPermission) {
            addButton(new ElementButton(Message.MENU_EDIT_BUY_LIST.getText()));
            addButton(new ElementButton(Message.MENU_EDIT_SELL_LIST.getText()));
            addButton(new ElementButton(Message.MENU_CUSTOM_NAMES.getText()));
        }
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        int clickedButtonId = getResponse().getClickedButtonId();
        if (clickedButtonId == 0) { // Buy button
            event.getPlayer().showFormWindow(new BuyCategoriesScreen());
        } else if (clickedButtonId == 1) { // Sell button
            event.getPlayer().showFormWindow(new SellItemSelectorScreen(event.getPlayer().getInventory()));
        } else if (clickedButtonId == 2) { // Edit buyable items button
            event.getPlayer().showFormWindow(new EditCategoriesScreen());
        } else if (clickedButtonId == 3) { // Edit sellable items button
            event.getPlayer().showFormWindow(new EditSellListScreen());
        } else if (clickedButtonId == 4) { // Custom item names
            event.getPlayer().showFormWindow(new CustomNamesScreen());
        }
    }

}
