package ua.leonidius.trdinterface.buy.edit.categories;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.ScreenManager;
import ua.leonidius.trdinterface.ShopHelper;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.screens.CustomScreen;
import ua.leonidius.trdinterface.screens.InfoScreen;

import java.sql.SQLException;

import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class RenameCategoryScreen extends CustomScreen {

    private transient int categoryId;
    private transient String oldName;

    public RenameCategoryScreen(ScreenManager manager, int categoryId, String oldName) {
        super(manager, Message.WDW_RENAME_CATEGORY_TITLE.getText());

        this.categoryId = categoryId;
        this.oldName = oldName;

        addElement(new ElementInput(Message.WDW_RENAME_CATEGORY_NAME.getText(), "", oldName));
    }

    @Override
    public void update() {}

    public void onResponse(PlayerFormRespondedEvent event) {
        String newName = getResponse().getInputResponse(0);

        try {
            ShopHelper.renameCategory(categoryId, newName);

            if (settings.editLogging) {
                Message.LOG_CATEGORY_RENAMED.log(event.getPlayer().getName(), oldName, newName);
            }

            getScreenManager().back();
        } catch (SQLException e) {
            if (settings.debugMode) Trading.getPlugin().getLogger().error(e.getMessage());
            getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.ERROR.getText()));
        }
    }

}