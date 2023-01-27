package game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TileManager {

	GamePanel gp;
	Tile[] mapTiles;
	Tile[] viewTiles;
	int tileSize;
	int viewSize;
	int mapTileCodes[][];
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		tileSize = gp.getTileSize();
		viewSize = gp.getViewSize();
		mapTiles = new Tile[10];
		viewTiles = new Tile[6];
		mapTileCodes = new int[gp.getMaxWorldColumn()][gp.getMaxWorldRow()];
		
		loadTileImages();

		generateDungeon();
	}
	
	private void generateDungeon() {
		
	}
	
	private void loadTileImages() {

		//			// grass
		//			setupTile(0, "001.png", false);

	}
	
	private void setupMapTile(int tileIndex, String imagePath) {

		try {
			mapTiles[tileIndex] = new Tile();
			BufferedImage scaledImage = ImageIO.read(getClass().getResourceAsStream("/mapTiles/" + imagePath));
			scaledImage = GraphicsTools.scaleTile(scaledImage, tileSize, tileSize);
			mapTiles[tileIndex].setImage(scaledImage);
			
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
