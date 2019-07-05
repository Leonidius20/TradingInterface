package ua.leonidius.trdinterface.buy;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowModal;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.ShopHelper;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.screens.Screen;

import java.sql.SQLException;

import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class DeleteCategoryScreen extends FormWindowModal implements Screen {

    private int shopId, categoryId;
    private String categoryName;

    public DeleteCategoryScreen(String categoryName, int shopId, int categoryId) {
        super(Message.WDW_DELETE_CATEGORY_TITLE.getText(),
              Message.WDW_DELETE_CATEGORY_CONF.getText(categoryName),
              Message.BTN_YES.getText(), Message.BTN_NO.getText());

        this.shopId = shopId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();

        if (getResponse().getClickedButtonId() != 0) { // Negative button clicked
            player.showFormWindow(new BuyItemSelectorScreen(shopId, categoryId, player.hasPermission("shop.edit")));
            return;
        }

        try {
            ShopHelper.deleteCategory(categoryId);

            if (settings.editLogging) {
                Message.LOG_CATEGORY_DELETED.log(event.getPlayer().getName(), categoryName);
            }

            player.showFormWindow(new BuyCategoriesScreen(shopId, player.hasPermission("shop.edit")));
        } catch (SQLException e) {
            Trading.getPlugin().getLogger().error(e.getMessage()); // TODO: remove?
            // TODO: fail screen
            player.showFormWindow(new BuyItemSelectorScreen(shopId, categoryId, player.hasPermission("shop.edit")));
        }
    }

}