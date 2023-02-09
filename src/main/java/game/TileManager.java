package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import javax.imageio.ImageIO;

import objects.LevelMap;

public class TileManager {

	private MapTile[] mapTiles;
	private Tile[] viewTiles;

	private final int DIRECTION_UP = 0;
	private final int DIRECTION_RIGHT = 1;
	private final int DIRECTION_DOWN = 2;
	private final int DIRECTION_LEFT = 3;

	private int tileSize;
	private int viewSize;
	private int maxMapRow;
	private int maxMapCol;
	private int startMapX;
	private int startMapY;
		
	private int mapTileCodes[][];
	
	private LevelMap map;
	
	public TileManager(GamePanel gp, LevelMap map) {
		
		this.map = map;
		tileSize = gp.getTileSize();
		viewSize = gp.getViewSize();
		maxMapRow = gp.getMaxWorldRow();
		maxMapCol = gp.getMaxWorldColumn();
		startMapX = gp.getMapScreenColumn() * tileSize;
		startMapY = gp.getMapScreenRow() * tileSize;
		mapTiles = new MapTile[16];
		viewTiles = new Tile[6];
		mapTileCodes = new int[maxMapCol][maxMapRow];
		
		// initialize map to blank tiles
		for (int counterX = 0; counterX < maxMapCol; counterX++) {
			for (int counterY = 0; counterY < maxMapRow; counterY++) {
				mapTileCodes[counterX][counterY] = 0;
			}
		}
		
		loadTileImages();
		//generateDungeon();
		loadDungeonMap(this.map.getFileName());
	}
	
	private void loadDungeonMap(String mapFile) {
		
		try {
			
			InputStream inputStream = getClass().getResourceAsStream("/maps/" + mapFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			
			int intX = 0;
			int intY = 0;
			
			while (intX < maxMapCol && intY < maxMapRow) {
				
				String inputLine = br.readLine();
				
				while (intX < maxMapCol) {
					String tileCodes[] = inputLine.split(" ");
					
					int tileCode = Integer.parseInt(tileCodes[intX]);
					mapTileCodes[intX][intY] = tileCode;
					intX++;
				}
				
				if (intX >= maxMapCol) {
					intX = 0;
					intY++;
				}
			}
			
			br.close();
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void generateDungeon() {

		Random random = new Random();
		
		int startX = 0;
		int startY = 0;
		int currentDirection = 0; // UP
		int startTileIndex = 0;
		
		boolean isFinished = false;
		
		// dungeon ranges are >=0 to < maxMapRow/maxMapCol
		// compute starting point
		// ...ALWAYS on an edge, but not on a corner
		// either 0 or 1
		int XorY = random.nextInt(2);
		
		if (XorY > 0) {
			// XorY > 0, so Y
			startY = random.nextInt(2);
			
			if (startY > 0) {
				// startY > 0, so bottom edge facing up
				startY = maxMapRow - 1;
				currentDirection = DIRECTION_UP;
				startTileIndex = 1;
			} else {
				// startY == 0, so top edge facing down
				currentDirection = DIRECTION_DOWN;
				startTileIndex = 4;
			}
			
			startX = random.nextInt(maxMapCol - 1) + 1;
		} else {
			// XorY == 0, so X
			startX = random.nextInt(2);
			
			if (startX > 0) {
				// startX > 0, so left edge facing right
				startX = maxMapCol - 1;
				currentDirection = DIRECTION_RIGHT;
				startTileIndex = 2;
			} else {
				// startX == 0, so right edge facing left
				currentDirection = DIRECTION_LEFT;
				startTileIndex = 8;
			}

			startY = random.nextInt(maxMapRow - 1) + 1;
		}
	
		int currentX = startX;
		int currentY = startY;
		int currentTileIndex = startTileIndex;
		mapTileCodes[currentX][currentY] = currentTileIndex;
		
		while (!isFinished) {
			
			// move path
			
			// generate random tile with valid "from" direction
			
			// determine next path
			boolean validDirection = false;
			int nextDirection = 0;
			
			while (!validDirection) {
				nextDirection = random.nextInt(4);
				validDirection = mapTiles[currentTileIndex].isDirectionViable(nextDirection);
			}
			
			currentDirection = nextDirection;
			
		}
	}
	
	private void loadTileImages() {
		
		// index matches exit bitmap:
		// Up == 1
		// Right == 2
		// Down == 4
		// Left == 8
		
		// map tiles
		setupMapTile(0, "blank.png");
		setupMapTile(1, "down_dead_end.png");
		setupMapTile(2, "left_dead_end.png");
		setupMapTile(3, "right_up_hallway.png");
		setupMapTile(4, "up_dead_end.png");
		setupMapTile(5, "up_down_hallway.png");
		setupMapTile(6, "right_down_hallway.png");
		setupMapTile(7, "left_t_intersection.png");
		setupMapTile(8, "right_dead_end.png");
		setupMapTile(9, "left_up_hallway.png");
		setupMapTile(10, "left_right_hallway.png");
		setupMapTile(11, "down_t_intersection.png");
		setupMapTile(12, "left_down_hallway.png");
		setupMapTile(13, "right_t_intersection.png");
		setupMapTile(14, "up_t_intersection.png");
		setupMapTile(15, "4_way_intersection.png");
		
		// view tiles
		setupViewTile(0, "4_way_intersection_large.png");
		setupViewTile(1, "dead_end_large.png");
		setupViewTile(2, "hallway_large.png");
		setupViewTile(3, "left_turn_hallway_large.png");
		setupViewTile(4, "right_turn_hallway_large.png");
		setupViewTile(5, "t_intersection_large.png");
	}
	
	private void setupMapTile(int tileIndex, String imagePath) {

		try {
			mapTiles[tileIndex] = new MapTile();
			BufferedImage scaledImage = ImageIO.read(getClass().getResourceAsStream("/mapTiles/" + imagePath));
			scaledImage = GraphicsTools.scaleTile(scaledImage, tileSize, tileSize);
			mapTiles[tileIndex].setImage(scaledImage);
			mapTiles[tileIndex].setExitBitMap(tileIndex);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private void setupViewTile(int tileIndex, String imagePath) {

		try {
			viewTiles[tileIndex] = new Tile();
			BufferedImage scaledImage = ImageIO.read(getClass().getResourceAsStream("/viewTiles/" + imagePath));
			scaledImage = GraphicsTools.scaleTile(scaledImage, viewSize, viewSize);
			viewTiles[tileIndex].setImage(scaledImage);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void drawMap(Graphics2D g2) {
		
		for (int counterX = 0; counterX < maxMapCol; counterX++) {

			int screenX = startMapX + (counterX * tileSize);
			
			for (int counterY = 0; counterY < maxMapRow; counterY++) {
				
				int screenY = startMapY + (counterY * tileSize);
				int tileNum = mapTileCodes[counterX][counterY];
				g2.drawImage(mapTiles[tileNum].getImage(), screenX, screenY, null);
			}
		}		

		// draw a box around the map
		drawBox(g2, startMapX, startMapY, tileSize * 8, tileSize * 8);

	}
	
	private void drawBox(Graphics2D g2, int boxX, int boxY, int boxWidth, int boxHeight) {

		Color c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5)); // pixel width
		g2.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 25, 25);
	}
	
	public int[][] getMapTileCodes() {
		return mapTileCodes;
	}
	
	public Tile[] getMapTiles() {
		return mapTiles;
	}
	
	public Tile[] getViewTiles() {
		return viewTiles;
	}
}
