package characters;

import java.util.ArrayList;
import java.util.List;

import game.GamePanel;
import game.KeyHandler;
import objects.Item;

public class Player extends Character {

	private int tileSize;
	private int experiencePoints;
	private int nextLevelXP;
	
	List<Item> inventory = new ArrayList<>();
	KeyHandler keyHandler;

	public Player(GamePanel gamePanel, KeyHandler keyHandler) {
		
		super(gamePanel);
		this.keyHandler = keyHandler;
		this.tileSize = gamePanel.getTileSize();
	
		setDefaultValues();
		loadPlayerImages();
	}
	
	private void setDefaultValues() {
		
		maxHealth = 6;
		currentHealth = maxHealth;
		level = 1;
		strength = 1;
		dexterity = 1;
		experiencePoints = 0;
		nextLevelXP = 5;
		
		attack = computeAttack();
		defense = computeDefense();
	}
	
	private void loadPlayerImages() {
		
		up1 = setupEntityImage("/characters/player_map_up_1.png", tileSize, tileSize);
		up2 = up1;
		down1 = setupEntityImage("/characters/player_map_down_1.png", tileSize, tileSize);
		down2 = down1;
		right1 = setupEntityImage("/characters/player_map_right_1.png", tileSize, tileSize);
		right2 = right1;
		left1 = setupEntityImage("/characters/player_map_left_1.png", tileSize, tileSize);
		left2 = left1;
	}
	
	public void update() {
		
		if (keyHandler.isUpPressed() || keyHandler.isDownPressed()
				|| keyHandler.isRightPressed() || keyHandler.isLeftPressed()) {
			
			if (keyHandler.isUpPressed()) {
				direction = "up";
			} else if (keyHandler.isDownPressed()) {
				direction = "down";
			} else if (keyHandler.isLeftPressed()) {
				direction = "left";
			} else if (keyHandler.isRightPressed()) {
				direction = "right";
			}
			
			switch (direction) {
			case "up":
				mapY -= tileSize;
				break;
			case "down":
				mapY += tileSize;
				break;
			case "left":
				mapX -= tileSize;
				break;
			case "right":
				mapX += tileSize;
				break;						
			}
		}
	}
}
