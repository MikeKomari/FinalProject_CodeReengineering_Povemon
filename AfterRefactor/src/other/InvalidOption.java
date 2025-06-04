package other;

import person.Player;

public class InvalidOption implements ShopOption {
    @Override
    public boolean execute(Player player, Shop shop) {
        System.out.println(" Invalid Choice!");
        return false;
    }
}