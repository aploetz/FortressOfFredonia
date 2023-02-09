package characters;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.GraphicsTools;
import objects.Item;

public class Character {

	protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	protected BufferedImage upAttack1, upAttack2, downAttack1, downAttack2, leftAttack1, leftAttack2, rightAttack1, rightAttack2;
	
	protected int maxHealth;
	protected int currentHealth;
	protected int level;
	protected int strength;
	protected int dexterity;
	protected int attack;
	protected int defense;
	protected int mapX;
	protected int mapY;
	protected String direction;

	private GamePanel gamePanel;

	private Item currentWeapon;
	
	private int animationCounter = 0;
	private int spriteNum = 1;
	private int tileSize;
	
	
	public Character (GamePanel gamePanel) {
		
		this.gamePanel = gamePanel;
		tileSize = this.gamePanel.getTileSize();
	}
	
	private void setAction() {
		
	}
	
	public void update() {
		setAction();
		
		// how to influence the animation
		animationCounter++;
		if (animationCounter > 12) {
			if (spriteNum == 1) {
				spriteNum = 2;
			// } else if ( spriteNum == 2) {
			} else {
				spriteNum = 1;
			}
			animationCounter = 0;
		}

	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if (spriteNum == 1) {
				image = up1;
			} else {
				image = up2;
			}
			break;
			
		case "down":
			if (spriteNum == 1) {
				image = down1;
			} else {
				image = down2;
			}
			break;
			
		case "left":
			if (spriteNum == 1) {
				image = left1;
			} else {
				image = left2;
			}
			break;
			
		case "right":
			if (spriteNum == 1) {
				image = right1;
			} else {
				image = right2;
			}
			break;
		}
		
		g2.drawImage(image, mapX, mapY, null);
	}
	
	protected BufferedImage setupEntityImage(String imagePath, int width, int height) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath));
			// scale player tile
			image = GraphicsTools.scaleTile(image, width, height);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return image;
	}
	
	protected int computeAttack() {

		// return strength * currentWeapon.getAttackValue();
		return strength;
	}
	
	protected int computeDefense() {
		
		// return dexterity * currentShield.getDefenseValue();
		return dexterity;
	}
	
	public int getSpriteNum() {
		
		return this.spriteNum;
	}
	
	public void setDirection(String direction) {
		
		this.direction = direction;
	}
	
	public String getDirection() {
		
		return this.direction;
	}
	
	public int getMapX() {
		
		return this.mapX;
	}
	
	public void setMapX(int mapX) {
		
		this.mapX = mapX;
	}

	public int getMapY() {
		
		return this.mapY;
	}
	
	public void setMapY(int mapY) {
		
		this.mapY = mapY;
	}
}
