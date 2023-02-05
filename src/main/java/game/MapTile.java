package game;

import java.util.ArrayList;
import java.util.List;

public class MapTile extends Tile {
	
	private List<String> viableExits = new ArrayList<>();
	private int exitBitMap;
	// Up == 1
	// Right == 2
	// Down == 4
	// Left == 8
	
	public int getExitBitMap() {
		return this.exitBitMap;
	}
	
	public void setExitBitMap(int bitMap) {
		this.exitBitMap = bitMap;
	}
	
	public List<String> getViableExits() {
		return this.viableExits;
	}
	
	public boolean isDirectionViable(int direction) {

		int bitDirection = convertToBitMapDirection(direction);
		int bitMap = exitBitMap;
		int bitFactor = 8;
		
		// let's make sure that we're starting with a relevant bitFactor
		// MUST be less than or equal to the bitMap
		while (bitFactor > bitMap) {
			bitFactor = bitFactor / 2;
		}
		
		while (bitMap >= bitDirection) {
			
			if (bitFactor == bitDirection || bitMap == bitDirection) {
				// if our bitDirection equals our bitFactor or BitMap, then it's "good"
				return true;
			} else {
				// decrement the current map by the current 2s compliment factor
				bitMap -= bitFactor;
				// decrement to the next 2s compliment factor
				bitFactor = bitFactor / 2;
			}
		}

		return false;
	}
	
	private int convertToBitMapDirection(int direction) {
		// direction will come in as a value of 0, 1, 2, or 3
		
		int bitDirection = 2 ^ direction;
		// 2 ^ 0 = 1
		// 2 ^ 1 = 2
		// 2 ^ 2 = 4
		// 2 ^ 3 = 8
		
		return bitDirection;
	}
}
