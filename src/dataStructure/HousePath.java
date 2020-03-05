package dataStructure;

public class HousePath {
	public String parentId;
	public String houseId;
	public int distance;
	
	public HousePath(String houseId, String parentId, int distance) {
		this.houseId = houseId;
		this.parentId = parentId;
		this.distance = distance;
	}

}
