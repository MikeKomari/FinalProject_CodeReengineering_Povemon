package actions;

import interfaces.BattleAction;
import main.GameHandler;
import person.Enemy;
import person.Player;
import povemon.Povemon;

public class FightAction implements BattleAction {
    @Override
    public boolean execute(Player player, Enemy enemy, GameHandler handler) {
        handler.fight();

        Povemon playerPovemon = handler.getPlayerCurrentPovemon();
        Povemon enemyPovemon = handler.getEnemyCurrentPovemon();

        if (!playerPovemon.getIsAlive() && player.leftAlive()) {
            return new SwapAction().execute(player, enemy, handler);
        }

        if (!enemyPovemon.getIsAlive()) {
            Povemon nextPovemon = enemy.switchNextPovemon();
            if (nextPovemon != null) {
                handler.setEnemyCurrentPovemon(nextPovemon);
            }
        }

        return false;
    }
}