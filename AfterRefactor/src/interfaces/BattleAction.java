package interfaces;

import person.Enemy;
import person.Player;
import main.GameHandler;
public interface BattleAction {
    boolean execute(Player player, Enemy enemy, GameHandler handler);
}