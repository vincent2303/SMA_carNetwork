package behavior;

import agent.Map;
import dataStructure.PassengerData;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import messageFilter.PassengerInfo;
import jade.lang.acl.ACLMessage;


public class ReadPassengerData extends CyclicBehaviour {
	
	private PassengerData getPassengerData(ACLMessage m) {
		try {
			PassengerData passengerData = (PassengerData) m.getContentObject();
			return passengerData;
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void action() {
		MessageTemplate mt = new MessageTemplate(new PassengerInfo());
		ACLMessage m = myAgent.receive(mt);
		if (m==null) {
			block();
		}
		else {
			PassengerData passengerData = getPassengerData(m);
			Map map = (Map) myAgent;
			String passengerState = passengerData.passengerState;
			if(passengerState.equals(PassengerData.WAITING)) { // if waiting, the passenger is in the house
				map.housePassengersMap.get(passengerData.fromId).add(passengerData.id);
			}
			else{ // we remove the passenger from the house and (the car will inform the map that it has a passenger)
				map.housePassengersMap.get(passengerData.fromId).remove(passengerData.id);
			}
		}
	}
}