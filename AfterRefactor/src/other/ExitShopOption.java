package other;

import person.Player;

public class ExitShopOption implements ShopOption {
    @Override
    public boolean execute(Player player, Shop shop) {
        System.out.println(" Thank you for visiting the shop!");
        return true;
    }
}
