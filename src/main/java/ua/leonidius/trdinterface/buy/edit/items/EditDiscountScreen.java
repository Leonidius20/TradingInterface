/*package ua.leonidius.trdinterface.buy.edit.items;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.utils.ConfigSection;
import ua.leonidius.trdinterface.ItemName;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.screens.Screen;

import static ua.leonidius.trdinterface.Trading.buyCfg;
import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 10.08.18.
 */
/*public class EditDiscountScreen extends FormWindowCustom implements Screen {

    String categoryId, key;
    boolean discountExisted;

    public EditDiscountScreen(String categoryId, String key) {
        super(Message.WDW_EDIT_DISCOUNT_TITLE.getText());

        this.categoryId = categoryId;
        this.key = key;

        ConfigSection itemSection = buyCfg.getSection(categoryId).getSection("items").getSection(key);

        if (itemSection.exists("discount")) {
            discountExisted = true;
            String discount = itemSection.getString("discount");
            addElement(new ElementInput(Message.WDW_EDIT_DISCOUNT_HINT.getText(), "", discount));
        } else {
            discountExisted = false;
            addElement(new ElementInput(Message.WDW_EDIT_DISCOUNT_HINT.getText()));
        }
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        try {
            // For logging
            int oldDiscount = 0;
            if (discountExisted) {
                oldDiscount = buyCfg.getSection(categoryId).getSection("items").getSection(key).getInt("discount");
            }
            String playerName = event.getPlayer().getName();
            String id = key.replace("-", ":");

            int discount = Integer.parseInt(getResponse().getInputResponse(0));

            if (discount > 100) {
                event.getPlayer().showFormWindow(new EditDiscountFailScreen(categoryId, key, EditDiscountFailScreen.moreThan100));
                return;
            }

            if (discount == 0) {
                buyCfg.getSection(categoryId).getSection("items").getSection(key).remove("discount");

                // Logging
                if (settings.editLogging && discountExisted) {
                    Message.LOG_DISCOUNT_DELETED.log(playerName, ItemName.get(id), id);
                }

            } else {
                buyCfg.getSection(categoryId).getSection("items").getSection(key).set("discount", discount);

                // logging
                if (settings.editLogging) {
                    if (discountExisted) {
                        Message.LOG_DISCOUNT_EDITED.log(playerName, ItemName.get(id), id, discount, oldDiscount);
                    } else {
                        Message.LOG_DISCOUNT_ADDED.log(playerName, discount, ItemName.get(id), id);
                    }
                }
            }

            buyCfg.save();

            event.getPlayer().showFormWindow(new BuyManageItemScreen(categoryId, key));
        } catch (Exception e) {
            event.getPlayer().showFormWindow(new EditDiscountFailScreen(categoryId, key, EditDiscountFailScreen.invalidParams));
        }
    }

}*/