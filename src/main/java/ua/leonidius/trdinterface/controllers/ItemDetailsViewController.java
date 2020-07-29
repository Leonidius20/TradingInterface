package ua.leonidius.trdinterface.controllers;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.models.Discount;
import ua.leonidius.trdinterface.models.ShopItem;
import ua.leonidius.trdinterface.utils.TimeUnit;
import ua.leonidius.trdinterface.views.ScreenManager;

public abstract class ItemDetailsViewController extends BaseController {

    public ItemDetailsViewController(ScreenManager manager) {
        super(manager);
    }

    protected abstract ShopItem getItem();

    public String buildItemDescription() {
        StringBuilder sb = new StringBuilder();

        getItem().resetGameItem();
        Item gameItem = getItem().toGameItem();

        sb.append(Message.WDW_BUY_NAME.getText(gameItem.getName()));
        sb.append("\n");

        Item pureItem = Item.fromString(getItem().getItemId());
        sb.append(Message.WDW_BUY_ORIGINAL_NAME.getText(pureItem.getName(),
                gameItem.getId(), gameItem.getDamage()));

        sb.append("\n").append(Message.WDW_BUY_PRICE.getText(getItem().getPrice(),
                Trading.getSettings().getCurrency()));

        // Adding discount info
        if (getItem() instanceof BuyableItem) {
            Discount discount = ((BuyableItem) getItem()).getDiscount();
            if (discount != null) {
                sb.append("\n")
                        .append(Message.WDW_BUY_DISCOUNT.getText(
                                discount.getPercent(), formatTimeLeft(discount.getEndTime())));
                sb.append("\n")
                        .append(Message.WDW_BUY_ORIGINAL_PRICE.getText(
                                ((BuyableItem) getItem()).getOriginalPrice(),
                                Trading.getSettings().getCurrency()));
            }
        }

        if (gameItem.getLore() != null && gameItem.getLore().length != 0) {
            StringBuilder loreBuilder = new StringBuilder();
            for (String line : gameItem.getLore()) {
                loreBuilder.append(line).append("\n");
            }
            sb.append("\n")
                    .append(Message.WDW_BUY_CUSTOM_LORE.getText(
                            loreBuilder.toString()));
        }

        Enchantment[] enchantments = gameItem.getEnchantments();
        if (enchantments != null && enchantments.length != 0) {
            sb.append("\n");
            StringBuilder eListBuilder = new StringBuilder();
            for (int i = 0; i < enchantments.length; i++) {
                Enchantment enchantment = enchantments[i];
                eListBuilder.append(enchantment.getName())
                        .append(" ").append(enchantment.getLevel());
                if (i != enchantments.length - 1) eListBuilder.append(", ");
            }
            sb.append(Message.WDW_BUY_ENCHANTMENTS.getText(eListBuilder.toString()));
        }

        return sb.toString();
    }

    /**
     * Formats time left to a certain number of seconds since epoch
     *
     * @param timestamp seconds after epoch, must be bigger than the
     *                  current time, otherwise the result is 'infinity'
     * @return formatted string
     */
    private static String formatTimeLeft(long timestamp) {
        long secondsLeft = timestamp - System.currentTimeMillis() / 1000;
        if (secondsLeft < 0) return Message.INFINITY.getText();
        if (secondsLeft == 0) return "0";

        StringBuilder sb = new StringBuilder();
        long months = secondsLeft / TimeUnit.MONTHS.getMultiplier(); // integer division
        if (months > 0) {
            sb.append(months).append(" ").append(TimeUnit.MONTHS.getName()).append(" ");
            secondsLeft -= (months * TimeUnit.MONTHS.getMultiplier());
        }

        long days = secondsLeft / TimeUnit.DAYS.getMultiplier();
        if (days > 0) {
            sb.append(days).append(" ").append(TimeUnit.DAYS.getName()).append(" ");
            secondsLeft -= (days * TimeUnit.DAYS.getMultiplier());
        }

        long hours = secondsLeft / TimeUnit.HOURS.getMultiplier();
        if (hours > 0) {
            sb.append(hours).append(" ").append(TimeUnit.HOURS.getName()).append(" ");
            secondsLeft -= (hours * TimeUnit.HOURS.getMultiplier());
        }

        long minutes = secondsLeft / TimeUnit.MINUTES.getMultiplier();
        if (minutes > 0) {
            sb.append(minutes).append(" ").append(TimeUnit.MINUTES.getName()).append(" ");
            secondsLeft -= (minutes * TimeUnit.MINUTES.getMultiplier());
        }

        if (secondsLeft > 0) {
            sb.append(secondsLeft).append(" ").append(Message.SECONDS.getText());
        }

        return sb.toString();
    }

}
