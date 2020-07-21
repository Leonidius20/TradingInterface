package ua.leonidius.trdinterface.views.screens.buy.categories.edit;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.ShopHelper;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.views.screens.InfoScreen;
import ua.leonidius.trdinterface.views.screens.ModalScreen;

import java.sql.SQLException;

import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 08.08.18.
 */

/*public class DeleteCategoryScreen extends ModalScreen {

    private transient int categoryId;
    private transient String categoryName;

    public DeleteCategoryScreen(ScreenManager manager, String categoryName, int categoryId) {
        super(manager, Message.WDW_DELETE_CATEGORY_TITLE.getText(),
              Message.WDW_DELETE_CATEGORY_CONF.getText(categoryName),
              Message.BTN_YES.getText(), Message.BTN_NO.getText());

        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    @Override
    public void update() {}

    public void onResponse(PlayerFormRespondedEvent event) {
        /*if (getResponse().getClickedButtonId() != 0) { // Negative button clicked
            getScreenManager().back();
            return;
        }

        try {
            ShopHelper.deleteCategory(categoryId);

            if (settings.editLogging) {
                Message.LOG_CATEGORY_DELETED.log(event.getPlayer().getName(), categoryName);
            }

            getScreenManager().back();
        } catch (SQLException e) {
            if (settings.debugMode) Trading.getPlugin().getLogger().error(e.getMessage());
            getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.ERROR.getText()));
        }
    }

}*/