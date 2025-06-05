package menu;
import main.GameHandler;
import person.Player;
import interfaces.Utility;
import interfaces.MenuAction;
public class ModifyTeamAction implements MenuAction {
    private GameHandler gameHandler;
    private Player player;

    public ModifyTeamAction(GameHandler gameHandler, Player player) {
        this.gameHandler = gameHandler;
        this.player = player;
    }

    @Override
    public void execute() {
        gameHandler.modifyTeam(player);
    }
}
