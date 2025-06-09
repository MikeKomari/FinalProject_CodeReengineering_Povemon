package menu;

import person.Enemy;
import person.Player;
import povemon.Povemon;

public class BattleData {
	private Player player;
    private Enemy enemy;
    private Povemon playerCurrentPovemon;
    private Povemon enemyCurrentPovemon;

    public BattleData(Player player, Enemy enemy, Povemon playerCurrentPovemon, Povemon enemyCurrentPovemon) {
        this.player = player;
        this.enemy = enemy;
        this.playerCurrentPovemon = playerCurrentPovemon;
        this.enemyCurrentPovemon = enemyCurrentPovemon;
    }

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Enemy getEnemy() {
		return enemy;
	}

	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}

	public Povemon getPlayerCurrentPovemon() {
		return playerCurrentPovemon;
	}

	public void setPlayerCurrentPovemon(Povemon playerCurrentPovemon) {
		this.playerCurrentPovemon = playerCurrentPovemon;
	}

	public Povemon getEnemyCurrentPovemon() {
		return enemyCurrentPovemon;
	}

	public void setEnemyCurrentPovemon(Povemon enemyCurrentPovemon) {
		this.enemyCurrentPovemon = enemyCurrentPovemon;
	}
    
}
