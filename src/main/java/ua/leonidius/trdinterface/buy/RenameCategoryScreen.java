package ua.leonidius.trdinterface.buy;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.ShopEditor;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.screens.Screen;

import java.sql.SQLException;

import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class RenameCategoryScreen extends FormWindowCustom implements Screen {

    private int shopId, categoryId;
    private String oldName;

    public RenameCategoryScreen(int shopId, int categoryId, String oldName) {
        super(Message.WDW_RENAME_CATEGORY_TITLE.getText());

        this.oldName = oldName;

        addElement(new ElementInput(Message.WDW_RENAME_CATEGORY_NAME.getText(), "", oldName));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        String newName = getResponse().getInputResponse(0);

        Player player = event.getPlayer();

        try {
            ShopEditor.renameCategory(categoryId, newName);

            if (settings.editLogging) {
                Message.LOG_CATEGORY_RENAMED.log(event.getPlayer().getName(), oldName, newName);
            }

            player.showFormWindow(new BuyItemSelectorScreen(shopId, categoryId, player.hasPermission("shop.edit")));
        } catch (SQLException e) {
            Trading.getPlugin().getLogger().error(e.getMessage()); // TODO: remove
            // TODO: fail screen
            player.showFormWindow(new BuyItemSelectorScreen(shopId, categoryId, player.hasPermission("shop.edit")));
        }
    }

}