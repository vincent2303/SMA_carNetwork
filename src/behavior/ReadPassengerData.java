package behavior;

import agent.Map;
import agent.Passenger;
import dataStructure.PassengerData;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.MessageTemplate;
import messageFilter.PassengerInfo;
import smaUtils.SmaUtils;
import jade.lang.acl.ACLMessage;


public class ReadPassengerData extends CyclicBehaviour {
	
	@Override
	public void action() {
		MessageTemplate mt = new MessageTemplate(new PassengerInfo());
		ACLMessage m = myAgent.receive(mt);
		if (m==null) {
			block();
		}
		else {
			PassengerData passengerData = SmaUtils.getPassengerDataFromMessage(m);
			Map map = (Map) myAgent;
			String passengerState = passengerData.passengerState;
			if(passengerState.equals(Passenger.WAITING)) { // if waiting, the passenger is in the house
				map.housePassengersMap.get(passengerData.fromId).add(passengerData.id);
			}
			else{ // we remove the passenger from the house and (the car will inform the map that it has a passenger)
				map.housePassengersMap.get(passengerData.fromId).remove(passengerData.id);
			}
		}
	}
}
