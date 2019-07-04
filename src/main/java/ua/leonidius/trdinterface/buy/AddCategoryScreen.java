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

import static ua.leonidius.trdinterface.Trading.getPlugin;
import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class AddCategoryScreen extends FormWindowCustom implements Screen {

    private int shopId;

    public AddCategoryScreen(int shopId) {
        super(Message.WDW_NEW_CATEGORY.getText());

        this.shopId = shopId;

        addElement(new ElementInput(Message.WDW_NEW_CATEGORY_NAME.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        String categoryName = getResponse().getInputResponse(0);

        try {
            ShopEditor.addCategory(shopId, categoryName);

            if (settings.editLogging) {
                Message.LOG_CATEGORY_ADDED.log(player.getName(), categoryName);
            }

            player.showFormWindow(new AddCategoryOutcomeScreen(shopId, true));
        } catch (SQLException e) {
            Trading.getPlugin().getLogger().error(e.getMessage()); // TODO: remove
            player.showFormWindow(new AddCategoryOutcomeScreen(shopId, false));
        }
    }

}