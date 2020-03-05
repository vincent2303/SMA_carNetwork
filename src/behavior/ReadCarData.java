package behavior;

import agent.Map;
import dataStructure.CarData;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import messageFilter.CarInfo;
import smaUtils.SmaUtils;

public class ReadCarData extends CyclicBehaviour {
	
	@Override
	public void action() {
		MessageTemplate mt = new MessageTemplate(new CarInfo());
		ACLMessage m = myAgent.receive(mt);
		if (m==null) {
			block();
		}
		else {
			CarData carData = SmaUtils.getCarDataFromMessage(m);
			Map map = (Map) myAgent;
			
			// we update the carMap
			map.carsMap.put(carData.id, carData);
		}
	}
}
