package objects;

public class LevelMap {

	private String fileName;
	private String startDirection;
	private int startX;
	private int startY;
	private int finishX;
	private int finishY;
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public int getStartX() {
		return startX;
	}
	
	public void setStartX(int startX) {
		this.startX = startX;
	}
	
	public int getStartY() {
		return startY;
	}
	
	public void setStartY(int startY) {
		this.startY = startY;
	}
	
	public int getFinishX() {
		return finishX;
	}
	
	public void setFinishX(int finishX) {
		this.finishX = finishX;
	}
	
	public int getFinishY() {
		return finishY;
	}
	
	public void setFinishY(int finishY) {
		this.finishY = finishY;
	}
	
	public String getStartDirection() {
		return this.startDirection;
	}
	
	public void setStartDirection(String direction) {
		this.startDirection = direction;
	}
}
