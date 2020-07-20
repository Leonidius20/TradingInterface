package ua.leonidius.trdinterface.controllers.buy.edit.categories;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import org.sqlite.SQLiteErrorCode;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.ShopHelper;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.views.screens.CustomScreen;
import ua.leonidius.trdinterface.views.screens.InfoScreen;

import java.sql.SQLException;

import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class AddCategoryScreen extends CustomScreen {

    private int shopId;

    public AddCategoryScreen(ScreenManager manager, int shopId) {
        super(manager, Message.WDW_NEW_CATEGORY.getText());

        this.shopId = shopId;

        addElement(new ElementInput(Message.WDW_NEW_CATEGORY_NAME.getText()));
    }

    @Override
    public void update() {}

    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        String categoryName = getResponse().getInputResponse(0);

        try {
            ShopHelper.addCategory(shopId, categoryName);

            if (settings.editLogging) {
                Message.LOG_CATEGORY_ADDED.log(player.getName(), categoryName);
            }

            getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.WDW_NEW_CATEGORY_SUCCESS.getText()));
        } catch (SQLException e) {
            if (settings.debugMode) Trading.getPlugin().getLogger().error(e.getMessage());
            InfoScreen screen;
            if (e.getErrorCode() == SQLiteErrorCode.SQLITE_CONSTRAINT.code) {
                screen = new InfoScreen(getScreenManager(), Message.WDW_NEW_CATEGORY_FAIL.getText());
            } else {
                screen = new InfoScreen(getScreenManager(), Message.ERROR.getText());
            }
            getScreenManager().addAndShow(screen);
        }
    }

}