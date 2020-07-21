package ua.leonidius.trdinterface.controllers.buy.items;

import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.BaseController;
import ua.leonidius.trdinterface.controllers.buy.categories.edit.RenameCategoryController;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.models.Category;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.buy.items.BuyableItemsScreen;

public class BuyableItemsController extends BaseController {

    private final Category category;

    public BuyableItemsController(ScreenManager manager, Category category) {
        super(manager);
        this.category = category;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new BuyableItemsScreen(this));
    }

    public String getCategoryName() {
        return category.name;
    }

    public boolean showEditingButtons() {
        // TODO: check divided permissions
        return manager.getPlayer().hasPermission("shop.edit");
    }

    public BuyableItem[] fetchItems() {
        BuyableItem[] items = new BuyableItem[0];
        return category.items.toArray(items);
    }

    public void renameCategory() {
        new RenameCategoryController(manager, category).showScreen();
    }

    public void deleteCategory() {
        // TODO: also make sure that all the items from it are deleted
       /* setIgnoreStack(true); // When the category is deleted, we want to return to BuyCategoriesScreen
        getScreenManager().addAndShow(new DeleteCategoryScreen(getScreenManager(),
                categoryName, categoryId), true);*/
    }

    public void addItem() {
        // TODO
        /*getScreenManager().addAndShow(new AddBuyItemScreen(getScreenManager(),
                shopId, categoryId), true);*/
    }

    public void buyItem(BuyableItem item) {
        // TODO
        /*if (hasPermission) {
            try {
                getScreenManager().addAndShow(new BuyManageItemScreen(getScreenManager(), itemRecordId));
            } catch (SQLException | IOException e) {
                if (Trading.settings.debugMode) Trading.getPlugin().getLogger().error(e.getMessage());
                getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.ERROR.getText()));
            }
            return;
        }

        try {
            Map<Item, Double> itemAndPrice = ShopHelper.getItemAndPrice(itemRecordId);
            Item item = itemAndPrice.keySet().iterator().next();
            double priceWithoutDiscount = itemAndPrice.get(item);

            // Checking if there is enough place in inventory and money
            double price = priceWithoutDiscount; // TODO: account for discount
            int maxByMoney = Buy.getMaxByMoney(player, price);
            int maxByInventory = Buy.getMaxByInventory(player, item);

            if (maxByMoney == 0 && maxByInventory == 0) {
                String message = Message.BUY_NO_SPACE_AND_MONEY.getText();
                getScreenManager().addAndShow(new InfoScreen(getScreenManager(), message));
            } else if (maxByMoney == 0) {
                String message = Message.BUY_NO_MONEY.getText();
                getScreenManager().addAndShow(new InfoScreen(getScreenManager(), message));
            } else if (maxByInventory == 0) {
                String message = Message.BUY_NO_SPACE.getText();
                getScreenManager().addAndShow(new InfoScreen(getScreenManager(), message));
            } else {
                int maxAmount = Math.min(maxByInventory, maxByMoney);
                BuySelectAmountScreen screen = new BuySelectAmountScreen(getScreenManager(), itemRecordId, maxAmount);
                getScreenManager().addAndShow(screen, true);
            }
        } catch (SQLException | IOException e) {
            if (Trading.settings.debugMode) Trading.getPlugin().getLogger().error(e.getMessage());
            getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.ERROR.getText()));
        }*/
    }

    public String buildItemButtonText(BuyableItem item) {
        Item gameItem = item.toGameItem();

        StringBuilder sb = new StringBuilder();
        sb.append(gameItem.getName());
        sb.append(" (").append(item.price)
                .append(Trading.settings.currency).append(")");

        if (gameItem.hasEnchantments()) {
            return TextFormat.colorize(TextFormat.DARK_PURPLE.getChar(), sb.toString());
        } else return sb.toString();
    }

}
