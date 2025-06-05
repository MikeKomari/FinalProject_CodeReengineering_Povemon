package actions;

import interfaces.BattleAction;
import main.GameHandler;
import person.Enemy;
import person.Player;

public class RunAction implements BattleAction {
    @Override
    public boolean execute(Player player, Enemy enemy, GameHandler handler) {
        System.out.println(" You ran away...");
        return true;
    }
}