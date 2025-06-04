package other;

import person.Player;

public class BuyPovemonOption implements ShopOption {
    @Override
    public boolean execute(Player player, Shop shop) {
        shop.buyPovemon(player);
        return false;
    }
}