package behavior;

import java.io.IOException;

import agent.Car;
import dataStructure.Performatifs;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import messageFilter.PassengerRequest;
import smaUtils.SmaUtils;

public class HandleRequest extends CyclicBehaviour {
	
	@Override
	public void action() {
		Car car = (Car) myAgent;
		
		MessageTemplate mt = new MessageTemplate(new PassengerRequest());
		ACLMessage request = myAgent.receive(mt);
		if (request==null) {
			block();
		} else {			
			if(!car.state.equals(Car.WAITING) ) { return; } // the state must be waiting						
			car.state = Car.WAITING_RESPONSE;		
			car.engagedWithAID = request.getSender();
			car.engagedWithId = SmaUtils.getpassengerDataFromMessage(request).id;
			ACLMessage proposition = new ACLMessage(Performatifs.CAR_PROPOSITION);
			try {
				proposition.setContentObject(((Car) myAgent).carData);
				proposition.addReceiver(request.getSender());
				((Car) myAgent).send(proposition);
				myAgent.addBehaviour(new ReadAnswerProposition());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
