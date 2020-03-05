package behavior;

import java.util.ArrayList;

import agent.Map;
import dataStructure.CarData;
import dataStructure.DestinationData;
import dataStructure.HouseData;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import messageFilter.AskNearestCar;
import smaUtils.SmaUtils;

public class FindNearestCar extends CyclicBehaviour {

	@Override
	public void action() {
		
		MessageTemplate mt = new MessageTemplate(new AskNearestCar());
		ACLMessage message = myAgent.receive(mt);
		if (message==null) {
			block();
		} else {
			
			DestinationData destData = SmaUtils.getDestinationDataDataFromMessage(message);
			String passengerFromId = destData.passengerFromId;
			ArrayList<CarData> carDataList = destData.dataPropositionList;

			CarData nearestCarData = carDataList.get(0);
			
			ArrayList<HouseData> drivingPath = ((Map) myAgent).findPath(nearestCarData.from , passengerFromId);
			int minDistance = ((Map) myAgent).computeDistanceFromDrivingPath(drivingPath);
			
			for(int i = 1; i<carDataList.size(); i++) {
				
				CarData currentCarData = carDataList.get(i);
				drivingPath = ((Map) myAgent).findPath(currentCarData.from , passengerFromId);
				int currentDistance = ((Map) myAgent).computeDistanceFromDrivingPath(drivingPath);
				if(currentDistance<minDistance) {
					minDistance = currentDistance;
					nearestCarData = currentCarData;
				}
			}
			
			System.out.println("voiture la plus proche: " + nearestCarData.id + "   d=" +minDistance);
			
		}
	}

}
