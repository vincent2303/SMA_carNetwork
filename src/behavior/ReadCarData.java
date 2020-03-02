package behavior;

import agent.Map;
import dataStructure.CarData;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import messageFilter.CarInfo;

public class ReadCarData extends CyclicBehaviour {

	private CarData getCarData(ACLMessage m) {
		try {
			CarData carData = (CarData) m.getContentObject();
			return carData;
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void action() {
		MessageTemplate mt = new MessageTemplate(new CarInfo());
		ACLMessage m = myAgent.receive(mt);
		if (m==null) {
			block();
		}
		else {
			CarData carData = getCarData(m);
			Map map = (Map) myAgent;
			
			// we update the carMap
			map.carsMap.put(carData.id, carData);
		}
	}
}
