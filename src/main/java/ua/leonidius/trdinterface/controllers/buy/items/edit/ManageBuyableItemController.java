package ua.leonidius.trdinterface.controllers.buy.items.edit;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.BaseController;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.buy.items.ManageBuyableItemScreen;

import static ua.leonidius.trdinterface.Trading.settings;

public class ManageBuyableItemController extends BaseController {

    private final BuyableItem item;

    public ManageBuyableItemController(ScreenManager manager, BuyableItem item) {
        super(manager);
        this.item = item;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new ManageBuyableItemScreen(this));
    }

    public String buildItemDescription() {
        StringBuilder sb = new StringBuilder();

        Item gameItem = item.toGameItem();

        double price = item.price; // TODO: apply discount

        sb.append(Message.WDW_BUY_NAME.getText(gameItem.getName()));
        sb.append("\n");

        Item pureItem = Item.fromString(gameItem.getId() + ":" + gameItem.getDamage());
        sb.append(Message.WDW_BUY_ORIGINAL_NAME.getText(pureItem.getName(),
                gameItem.getId(), gameItem.getDamage()));

        sb.append("\n").append(Message.WDW_BUY_PRICE.getText(price, settings.currency));

        /*if (shopItem.discount != 0) {
            sb.append("\n")
                    .append(Message.WDW_BUY_DISCOUNT.getText(shopItem.discount, shopItem.calculatePrice(), settings.currency));
        }*/

        if (gameItem.getLore() != null && gameItem.getLore().length != 0) {
            StringBuilder loreBuilder = new StringBuilder();
            for (String line : gameItem.getLore()) {
                loreBuilder.append(line).append("\n");
            }
            sb.append("\n").append(Message.WDW_BUY_CUSTOM_LORE.getText(loreBuilder.toString()));
        }

        Enchantment[] enchantments = gameItem.getEnchantments();
        if (enchantments != null && enchantments.length != 0) {
            sb.append("\n");
            StringBuilder eListBuilder = new StringBuilder();
            for (int i = 0; i < enchantments.length; i++) {
                Enchantment enchantment = enchantments[i];
                eListBuilder.append(enchantment.getName()).append(" ").append(enchantment.getLevel());
                if (i != enchantments.length - 1) eListBuilder.append(", ");
            }
            sb.append(Message.WDW_BUY_ENCHANTMENTS.getText(eListBuilder.toString()));
        }

        return sb.toString();
    }

    public void buyItem() {
        /*try {
            Map<Item, Double> itemAndPrice = ShopHelper.getItemAndPrice(itemId);
            Item item = itemAndPrice.keySet().iterator().next();

            double priceWithoutDiscount = itemAndPrice.get(item);
            double price = priceWithoutDiscount; // TODO: apply discount

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
                getScreenManager().addAndShow(new BuySelectAmountScreen(getScreenManager(),
                        itemId, maxAmount), true);
            }
        } catch (SQLException | IOException e) {
            if (Trading.settings.debugMode) Trading.getPlugin().getLogger().error(e.getMessage());
            getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.ERROR.getText()));
        }*/
    }

    public void editItem() {
        // TODO: use the same form as creation form
    }

    public void editDiscount() {

    }

    public void addEnchantment() {

    }

    public void removeEnchantment() {

    }

    public void deleteItem() {

    }

}
