package ua.leonidius.trdinterface.controllers;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.models.ShopItem;
import ua.leonidius.trdinterface.views.ScreenManager;

public abstract class ItemDetailsViewController extends BaseController {

    public ItemDetailsViewController(ScreenManager manager) {
        super(manager);
    }

    public abstract boolean isBuy();

    protected abstract ShopItem getItem();

    public String buildItemDescription() {
        StringBuilder sb = new StringBuilder();

        getItem().resetGameItem();
        Item gameItem = getItem().toGameItem();

        if (isBuy()) {
            // TODO: apply discount to price
        }

        sb.append(Message.WDW_BUY_NAME.getText(gameItem.getName()));
        sb.append("\n");

        Item pureItem = Item.fromString(gameItem.getId() + ":" + gameItem.getDamage());
        sb.append(Message.WDW_BUY_ORIGINAL_NAME.getText(pureItem.getName(),
                gameItem.getId(), gameItem.getDamage()));

        sb.append("\n").append(Message.WDW_BUY_PRICE.getText(getItem().getPrice(),
                Trading.settings.currency));

        if (isBuy()) {
            /*if (shopItem.discount != 0) {
            sb.append("\n")
                    .append(Message.WDW_BUY_DISCOUNT.getText(shopItem.discount, shopItem.calculatePrice(), settings.currency));
        }*/
        }


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

}
