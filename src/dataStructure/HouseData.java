package dataStructure;

import java.util.ArrayList;

public class HouseData {
	public String id;
	public int[] position;
	public ArrayList<String> neighboors;
	
	public HouseData(String id, int[] position) {
		this.id = id;
		this.position = position;
		this.neighboors = new ArrayList<String>();
	}
	
	public void linkHouse(HouseData h) {
		this.neighboors.add(h.id);
		h.neighboors.add(this.id);
	}
}
