package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable {

	final int originalTileSize = 16;
	final int scale = 3;

	// screen settings
	final int tileSize = originalTileSize * scale;  // 48x48 by default
	final int viewSize = tileSize * 8;
	final int maxScreenCol = 20;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol; // 768x
	final int screenHeight = tileSize * maxScreenRow; // 576
	
	// world map settings
	final int maxWorldCol = 8;
	final int maxWorldRow = 8;
	
	// frames per second
	final int fPS = 60;
	
	// Game engine
	private TileManager tileMgr = new TileManager(this);
	private Thread gameThread;
	KeyHandler keyHandler = new KeyHandler(this);
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
	}

	public void setupGame() {
		
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void stopGame() {
		gameThread = null;
	}
	
	public void run() {

		double drawInterval = 1000000000/fPS;
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while (gameThread != null) {
			// testing
			// System.out.println("Game loop is running");
			
			update();
			
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime / 1000000;
				
				if (remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long)remainingTime);
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}
	
	private void update() {
		
//		if (gameState == PLAY_STATE) {
//			
//			player.update();
//			
//			for (Entity npc : npcs) {
//				if (npc != null) {
//					npc.update();
//				}
//			}
//			
//			for (int index = 0; index < monsters.length; index++) {
//				if (monsters[index] != null) {
//					if (monsters[index].isAlive() && !monsters[index].isDying()) {
//						monsters[index].update();
//					} else if (!monsters[index].isAlive()) {
//						monsters[index].checkDrop();
//						monsters[index] = null;
//					}
//				}
//			}
//			
//			for (int index = 0; index < projectileList.size(); index++) {
//				if (projectileList.get(index) != null) {
//					if (projectileList.get(index).isAlive()) {
//						projectileList.get(index).update();
//					} else if (!projectileList.get(index).isAlive()) {
//						projectileList.remove(index);
//					}
//				}
//			}
//		}
	}
	

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
	public int getViewSize() {
		return viewSize;
	}
	
	public int getMaxScreenColumn() {
		return maxScreenCol;
	}
	
	public int getMaxScreenRow() {
		return maxScreenRow;
	}
	
	public int getScreenWidth() {
		return screenWidth;
	}
	
	public int getScreenHeight() {
		return screenHeight;
	}
	
	public int getMaxWorldColumn() {
		return maxWorldCol;
	}
	
	public int getMaxWorldRow() {
		return maxWorldRow;
	}
	
	public int getGameState() {
		return this.gameState;
	}
	
	public void setGameState(int newState) {
		this.gameState = newState;
	}
}
