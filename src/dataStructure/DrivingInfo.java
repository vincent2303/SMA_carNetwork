package dataStructure;

import java.io.Serializable;
import java.util.ArrayList;

public class DrivingInfo implements Serializable {
	public ArrayList<HouseData> pathToGet;
	public ArrayList<HouseData> pathToSend;
	
	public DrivingInfo(ArrayList<HouseData> pathToGet, ArrayList<HouseData> pathToSend) {
		this.pathToGet = pathToGet;
		this.pathToSend = pathToSend;
	}
}
