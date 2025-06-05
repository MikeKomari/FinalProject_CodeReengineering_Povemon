package menu;
import interfaces.MenuAction;
import main.GameHandler;
import person.Player;
import interfaces.Utility;
public class BattleAction implements MenuAction {
    private GameHandler gameHandler;
    private Player player;

    public BattleAction(GameHandler gameHandler, Player player) {
        this.gameHandler = gameHandler;
        this.player = player;
    }

    @Override
    public void execute() {
        Utility.clearScreen();
        gameHandler.enterBattle(player);
    }
}