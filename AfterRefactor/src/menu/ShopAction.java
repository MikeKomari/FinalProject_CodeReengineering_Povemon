package menu;
import interfaces.MenuAction;
import other.Shop;
import person.Player;
import interfaces.Utility;
public class ShopAction implements MenuAction {
    private Shop shop;
    private Player player;

    public ShopAction(Shop shop, Player player) {
        this.shop = shop;
        this.player = player;
    }

    @Override
    public void execute() {
        Utility.clearScreen();
        shop.shopMenu(player);
    }
}