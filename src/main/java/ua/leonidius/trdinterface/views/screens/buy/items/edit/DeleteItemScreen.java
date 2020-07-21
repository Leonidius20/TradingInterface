package ua.leonidius.trdinterface.views.screens.buy.items.edit;

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
 * Created by Leonidius20 on 10.08.18.
 */
/*public class DeleteItemScreen extends ModalScreen {

    private transient int recordId;
    private String itemName, itemId;

    public DeleteItemScreen(ScreenManager manager, int recordId, String name, String id) {
        super(manager, Message.WDW_DELETE_ITEM_TITLE.getText(),
                Message.WDW_DELETE_BUY_ITEM_CONF.getText(name),
                Message.BTN_YES.getText(), Message.BTN_NO.getText());

        this.recordId = recordId;
        this.itemName = name;
        this.itemId = id;
    }

    @Override
    public void update() {}

    public void onResponse(PlayerFormRespondedEvent event) {
        if (getResponse().getClickedButtonId() == 0) { // Yes
            try {
                ShopHelper.deleteBuyItem(recordId);

                if (settings.editLogging) {
                    Message.LOG_BUY_ITEM_DELETED.log(event.getPlayer().getName(), itemName, itemId);
                }

                getScreenManager().backTwoScreens();
            } catch (SQLException e) {
                if (settings.debugMode) Trading.getPlugin().getLogger().error(e.getMessage());
                getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.ERROR.getText()));
            }
        } else { // No
            getScreenManager().back();
        }
    }

}*/