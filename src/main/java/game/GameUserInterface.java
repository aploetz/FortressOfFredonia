package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class GameUserInterface {

	private boolean gameFinished = false;
	private int tileSize;
	private int menuOptionNum = 0;
	
	public int slotCol = 0;
	public int slotRow = 0;
	
	private String currentDialog = "";
	
	private List<String> messages = new ArrayList<>();
	private List<Integer> messagesCounter = new ArrayList<>();
	
	private final int MAX_MENU_OPTIONS = 3;
	
	GamePanel gp;
	Graphics2D g2;
	Font arialP40 = new Font("Arial", Font.PLAIN, 40);
	Font arialB80 = new Font("Arial", Font.BOLD, 80);
	
	public GameUserInterface(GamePanel gp) {
		this.gp = gp;
		this.tileSize = gp.getTileSize();
	}
	
	public int getItemIndexFromSlot() {
		return (slotRow * 5) + slotCol;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(arialB80);
		g2.setColor(Color.white);
		

		if (gp.getGameState() == gp.PLAY_STATE) {
			// Play state
			drawMessages();
		} else if (gp.getGameState() == gp.TITLE_STATE) {
			// title screen
			drawTitleScreen();
	    } else if (gp.getGameState() == gp.PAUSE_STATE) {
			// Pause state
			drawPauseScreen();
		} else if (gp.getGameState() == gp.DIALOG_STATE) {
			drawDialogScreen();
		} else if (gp.getGameState() == gp.INVENTORY_STATE) {
			drawInventoryScreen();
		}
	}
	
	private void drawPauseScreen() {
		
		String text = "PAUSED";
		int textX = getXForCenteredText(text);
		int textY = gp.getScreenHeight() / 2;
		
		g2.setColor(Color.WHITE);
		g2.drawString(text, textX, textY);	
	}
	
	private void drawTitleScreen() {
		
		// background
		g2.setColor(new Color(0, 0, 120));
		g2.fillRect(0,  0, gp.getScreenWidth(), gp.getScreenHeight());
		
		// title
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,72F));
		String text = "The Fortress of Fredonia";
		int titleX = getXForCenteredText(text);
		int titleY = tileSize * 3;
		
		// shadow
		g2.setColor(Color.darkGray);
		g2.drawString(text, titleX + 5, titleY + 5);
		
		// main color
		g2.setColor(Color.white);
		g2.drawString(text, titleX, titleY);
		
		// main character image
		//int bbX = gp.getScreenWidth() / 2;
		//int bbY = tileSize * 5;
		//g2.drawImage(gp.getPlayer().getEntityImage(), bbX - tileSize, bbY, tileSize *2, tileSize *2, null);
		
		// menu
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,36F));
		
		String newGame = "New Game";
		int ngX = getXForCenteredText(newGame);
		int ngY = tileSize * 9;
		g2.drawString(newGame, ngX, ngY);
		
		String loadGame = "Load Game";
		int lgX = getXForCenteredText(loadGame);
		int lgY = tileSize * 10;
		g2.drawString(loadGame, lgX, lgY);
		
		String quitGame = "Quit";
		int qgX = getXForCenteredText(quitGame);
		int qgY = tileSize * 11;
		g2.drawString(quitGame, qgX, qgY);
		
		// arrow on menu
		if (menuOptionNum == 0) {
			g2.drawString(">", ngX - tileSize, ngY);
		} else if (menuOptionNum == 1) {
			g2.drawString(">", lgX - tileSize, lgY);
		} else if (menuOptionNum == 2) {
			g2.drawString(">", qgX - tileSize, qgY);
		}
		
	}
	
	private void drawInventoryScreen() {
		
		final int frameX = tileSize * 9;
		final int frameY = tileSize;
		final int frameWidth = tileSize * 6;
		final int frameHeight = tileSize * 5;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// inventory slots
		final int slotXStart = frameX + (tileSize / 2);
		final int slotYStart = frameY + (tileSize / 2);
		int slotX = slotXStart;
		int slotY = slotYStart;
		int slotSize = tileSize + 3;
		
		// draw player's current items
//		for (Entity item : gp.getPlayer().getInventory()) {
//			
//			// "equip" cursor
//			if (item == gp.getPlayer().getCurrentWeapon() ||
//					item == gp.getPlayer().getCurrentShield()) {
//				g2.setColor(new Color(240,190,90));
//				g2.fillRoundRect(slotX, slotY, tileSize, tileSize, 10, 10);
//			}
//			
//			
//			if (item != null) {
//				// make sure item is not null
//				g2.drawImage(item.getDn1(), slotX, slotY, null);
//				slotX += slotSize;
//				
//				if (slotX > (slotXStart + (4 * slotSize))) {
//					// if we've already drawn slot col #4,
//					// then reset slotX and increment slotY.
//					slotX = slotXStart;
//					slotY += slotSize;
//				}
//			}
//		}
		
		// cursor
		int cursorX = slotXStart + (slotSize * slotCol);
		int cursorY = slotYStart + (slotSize * slotRow);
		int cursorWidth = slotSize;
		int cursorHeight = slotSize;
		
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10); // "roundness" setting is 10, 10
		
		// item description frame
		int dFrameX = frameX;
		int dFrameY = frameY + frameHeight;
		int dFrameWidth = frameWidth;
		int dFrameHeight = tileSize * 3;
		
		// draw description text
		int textX = dFrameX + 20;
		int textY = dFrameY + tileSize;
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(28F));
		final int lineHeight = 32;
		int itemIndex = getItemIndexFromSlot();
		
//		if (itemIndex < gp.getPlayer().getInventory().size()) {
//
//			drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
//
//			for (String line : gp.getPlayer().getInventory().get(itemIndex).getItemDescription().split("\n")) {
//				g2.drawString(line, textX, textY);
//				textY += lineHeight;
//			}
//		}
	}
	
	private void drawMessages() {
		int messageX = tileSize;
		int messageY = tileSize * 4;
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
		
		for (int messageIndex = 0; messageIndex < messages.size(); messageIndex++) {
			
			if (messages.get(messageIndex) != null) {
				// shadow
				g2.setColor(Color.black);
				g2.drawString(messages.get(messageIndex), messageX + 2, messageY + 2);
				// text
				g2.setColor(Color.white);
				g2.drawString(messages.get(messageIndex), messageX, messageY);
				
				int counter = messagesCounter.get(messageIndex) + 1;
				messagesCounter.set(messageIndex, counter);
				messageY += 50;
				
				if (messagesCounter.get(messageIndex) > 180) {
					messages.remove(messageIndex);
					messagesCounter.remove(messageIndex);
				}
			}
		}
	}
	
	private void drawDialogScreen() {
		
		int windowX = tileSize * 2;
		int windowY = tileSize / 2;
		int windowWidth = gp.getScreenWidth() - (tileSize * 4);
		int windowHeight = tileSize * 4;	
	
		drawSubWindow(windowX, windowY, windowWidth, windowHeight);
		
		// text
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
		int textX = windowX + tileSize;
		int textY = windowY + tileSize;
		
		for (String line : currentDialog.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
			
	}
	
	private void drawSubWindow(int swX, int swY, int swWidth, int swHeight) {

		Color c = new Color(0, 0, 0, 200);
		g2.setColor(c);
		g2.fillRoundRect(swX, swY, swWidth, swHeight, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5)); // pixel width
		g2.drawRoundRect(swX + 5, swY + 5, swWidth - 10, swHeight - 10, 25, 25);
	}

	private int getXForCenteredText(String text) {
		int textSize = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return (gp.getScreenWidth() / 2) - (textSize / 2);
	}

	private int getXForAlignToRightText(String text, int tailX) {
		int textSize = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int newX = tailX - textSize;
		return newX;
	}
	
	public int getSlotCol() {
		return this.slotCol;
	}
	
	public void setSlotCol (int slotColumn) {
		if (slotColumn >= 0 && slotColumn <= 4) {
			this.slotCol = slotColumn;
		}
	}

	public int getSlotRow() {
		return this.slotRow;
	}
	
	public void setSlotRow (int slotRow) {
		if (slotRow >= 0 && slotRow <= 3) {
			this.slotRow = slotRow;
		}
	}
	
	public int getMenuOptionNum() {
		return this.menuOptionNum;
	}
	
	public void decrementMenuOptionNum() {
		if (menuOptionNum > 0) {
			menuOptionNum--;
		}
	}
	
	public void incrementMenuOptionNum() {
		if (menuOptionNum < MAX_MENU_OPTIONS) {
			menuOptionNum++;
		}
	}
}
