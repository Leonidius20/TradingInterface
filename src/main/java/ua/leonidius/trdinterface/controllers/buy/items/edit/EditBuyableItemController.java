package ua.leonidius.trdinterface.controllers.buy.items.edit;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.ItemDetailsController;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ItemDetailsEditScreen;

import java.io.IOException;
import java.sql.SQLException;

public class EditBuyableItemController extends ItemDetailsController {

    private final BuyableItem item;

    public EditBuyableItemController(ScreenManager manager, BuyableItem item) {
        super(manager);
        this.item = item;
    }

    @Override
    public void showScreen() {
        StringBuilder customLoreBuilder = new StringBuilder();
        String[] lore = item.toGameItem().getLore();

        for (int i = 0; i < lore.length; i++) {
            customLoreBuilder.append(lore[i]);
            if (i != lore.length - 1) customLoreBuilder.append("\n");
        }

        manager.addAndShow(new ItemDetailsEditScreen(this,
                Message.WDW_EDIT_ITEM_TITLE.getText(),
                item.getItemId(), String.valueOf(item.getPrice()),
                item.toGameItem().getCustomName(),
                customLoreBuilder.toString()));
    }

    // TODO: fix code redundancy with Add...Controller
    @Override
    public void submitDetails(String itemId, String priceS, String customName, String customLore) {
        Item gameItem;

        if (itemId.isEmpty() || (gameItem = Item.fromString(itemId)).getId() == 0) {
            showErrorScreen();
            return;
        }
        item.setItemId(itemId);

        double oldPrice = item.getPrice();
        try {
            item.setPrice(Double.parseDouble(priceS.replace(",", ".")));
        } catch (NumberFormatException e) {
            showErrorScreen();
            return;
        }

        if (customName != null && !customName.isEmpty())
            gameItem.setCustomName(customName);

        if (customLore != null && !customLore.isEmpty())
            gameItem.setLore(customLore.split("\n"));

        try {
            item.setNbt(gameItem.getNamedTag() == null ? null : NBTIO.write(gameItem.getNamedTag()));
        } catch (IOException e) {
            if (Trading.settings.debugMode) {
                Trading.getPlugin().getLogger().error(e.getMessage());
            }
        }

        try {
            Dao<BuyableItem, Integer> itemDao =
                    DaoManager.createDao(Trading.getSource(), BuyableItem.class);
            itemDao.update(item);
        } catch (SQLException e) {
            if (Trading.settings.debugMode)
                Trading.getPlugin().getLogger().error(e.getMessage());
            showErrorScreen();
            return;
        }

        if (Trading.settings.editLogging) {
            Message.LOG_BUY_ITEM_EDITED.log(manager.getPlayer(),
                    gameItem.getName(),
                    gameItem.getId() + ":" + gameItem.getDamage(),
                    item.getPrice(), Trading.settings.currency,
                    oldPrice, Trading.settings.currency);
            // TODO: check if there's a way to optimize double currency
        }

        manager.back();
    }

}
