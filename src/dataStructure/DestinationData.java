package dataStructure;

import java.io.Serializable;
import java.util.ArrayList;

public class DestinationData implements Serializable {
	public String passengerFromId;
	public ArrayList<CarData> dataPropositionList;
	public DestinationData(String passengerFromId, ArrayList<CarData> dataPropositionList) {
		this.passengerFromId = passengerFromId;
		this.dataPropositionList = dataPropositionList;
	}
}
