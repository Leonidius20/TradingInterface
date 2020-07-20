package ua.leonidius.trdinterface.buy.categories;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.ScreenManager;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.buy.BuyItemSelectorScreen;
import ua.leonidius.trdinterface.buy.edit.categories.AddCategoryScreen;
import ua.leonidius.trdinterface.elements.CategoryButton;
import ua.leonidius.trdinterface.screens.InfoScreen;
import ua.leonidius.trdinterface.screens.SimpleScreen;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Leonidius20 on 26.06.18.
 */
public class BuyCategoriesScreen extends SimpleScreen {

    private transient int shopId;

    public BuyCategoriesScreen(ScreenManager manager, int shopId) throws SQLException {
        super(manager, Message.WDW_BUY_CAT_TITLE.getText());

        this.shopId = shopId;

        update();
    }

    @Override
    public void update() throws SQLException {
        getButtons().clear();
        setContent("");

        // Back button
        addButton(new ElementButton(Message.BTN_BACK.getText()));

        // Add category button
        boolean hasPermission = getScreenManager().getPlayer().hasPermission("shop.edit");
        if (hasPermission) addButton(new ElementButton(Message.BTN_ADD_CATEGORY.getText()));

        String query = "SELECT * FROM categories WHERE shop_id = ?";
        PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
        statement.setInt(1, shopId);
        ResultSet categories = statement.executeQuery();
        while (categories.next()) {
            String name = categories.getString("name");
            int categoryId = categories.getInt("record_id");
            addButton(new CategoryButton(name, categoryId));
        }

        if ((getButtons().size() == 1 && !hasPermission) || (getButtons().size() == 2 && hasPermission)) {
            setContent(Message.WDW_BUY_NO_CATEGORIES.getText());
        }
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        if (getResponse().getClickedButtonId() == 0) { // Back
            getScreenManager().back();
            return;
        }

        boolean hasPermission = getScreenManager().getPlayer().hasPermission("shop.edit");
        if (hasPermission && getResponse().getClickedButtonId() == 1) { // Add category button
            getScreenManager().addAndShow(new AddCategoryScreen(getScreenManager(), shopId), true);
            return;
        }

        CategoryButton button = (CategoryButton)getResponse().getClickedButton();

        try {
            BuyItemSelectorScreen screen = new BuyItemSelectorScreen(getScreenManager(), shopId, button.getCategoryId());
            getScreenManager().addAndShow(screen);
        } catch (SQLException | IOException e) {
            if (Trading.settings.debugMode) Trading.getPlugin().getLogger().error(e.getMessage());
            getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.ERROR.getText()));
        }
    }

}