package behavior;

import java.util.ArrayList;

import agent.Car;
import agent.Map;
import dataStructure.CarData;
import dataStructure.DestinationData;
import dataStructure.HouseData;
import dataStructure.Performatifs;
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
		ACLMessage question = myAgent.receive(mt);
		if (question==null) {
			block();
		} else {
			
			DestinationData destData = SmaUtils.getDestinationDataDataFromMessage(question);
			String passengerFromId = destData.passengerFromId;
			ArrayList<CarData> carDataList = destData.dataPropositionList;

			CarData nearestCarData = carDataList.get(0);
			
			ArrayList<HouseData> drivingPath = ((Map) myAgent).findPath(nearestCarData.from.id , passengerFromId);
			int minDistance = ((Map) myAgent).computeDistanceFromDrivingPath(drivingPath);
			
			for(int i = 1; i<carDataList.size(); i++) {
				
				CarData currentCarData = carDataList.get(i);
				drivingPath = ((Map) myAgent).findPath(currentCarData.from.id , passengerFromId);
				int currentDistance = ((Map) myAgent).computeDistanceFromDrivingPath(drivingPath);
				if(currentDistance<minDistance) {
					minDistance = currentDistance;
					nearestCarData = currentCarData;
				}
			}
			
			ACLMessage answer = new ACLMessage(Performatifs.ANSWER_NEAREST_CAR);
			answer.addReceiver(question.getSender());
			answer.setContent(nearestCarData.id);
			((Map) myAgent).send(answer);
		}
	}

}
