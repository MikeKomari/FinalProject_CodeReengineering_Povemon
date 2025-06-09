package actions;

import interfaces.BattleAction;
import main.GameHandler;
import person.Enemy;
import person.Player;

public class SwapAction implements BattleAction {
    @Override
    public boolean execute(Player player, Enemy enemy, GameHandler handler) {
        int index = player.swapPovemon(handler.getPlayerCurrentPovemon());
        if (index != -1) {
            handler.setPlayerCurrentPovemon(player.getTeam().get(index));
        }
        return false;
    }
}