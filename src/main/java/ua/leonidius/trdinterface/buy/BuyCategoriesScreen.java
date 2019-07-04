package ua.leonidius.trdinterface.buy;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.elements.CategoryButton;
import ua.leonidius.trdinterface.screens.MainScreen;
import ua.leonidius.trdinterface.screens.Screen;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Leonidius20 on 26.06.18.
 */
public class BuyCategoriesScreen extends FormWindowSimple implements Screen {

    private int shopId;
    private boolean hasPermission;

    public BuyCategoriesScreen(int shopId, boolean hasPermission) {
        super(Message.WDW_BUY_CAT_TITLE.getText(), "");

        this.shopId = shopId;
        this.hasPermission = hasPermission;

        // Back button
        addButton(new ElementButton(Message.BTN_BACK.getText()));

        // Add category button
        if (hasPermission) addButton(new ElementButton(Message.BTN_ADD_CATEGORY.getText()));

        /*List<Map> categories = (List<Map>)buyCfg.getMapList("shops").get(0).get("categories");

        for (Map category : categories) {
            addButton(new CategoryButton((String)category.get("name")));
        }*/

        try {
            String query = "SELECT * FROM categories WHERE shop_id = ?";
            PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
            statement.setInt(1, shopId);
            ResultSet categories = statement.executeQuery();
            while (categories.next()) {
                String name = categories.getString("name");
                int categoryId = categories.getInt("record_id");
                addButton(new CategoryButton(name, categoryId));
            }
        } catch (SQLException e) {
            Trading.getPlugin().getLogger().error(e.getMessage());
        }

        if ((getButtons().size() == 1 && !hasPermission) || (getButtons().size() == 2 && hasPermission)) {
            setContent(Message.WDW_BUY_NO_CATEGORIES.getText());
        }
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        if (getResponse().getClickedButtonId() == 0) { // Back
            event.getPlayer().showFormWindow(new MainScreen(shopId));
            return;
        }

        if (hasPermission && getResponse().getClickedButtonId() == 1) { // Add category button
            event.getPlayer().showFormWindow(new AddCategoryScreen(shopId));
            return;
        }

        CategoryButton button = (CategoryButton)getResponse().getClickedButton();

        event.getPlayer().showFormWindow(new BuyItemSelectorScreen(shopId, button.getCategoryId(), hasPermission));
    }

}