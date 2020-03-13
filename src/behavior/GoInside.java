package behavior;

import agent.Passenger;
import dataStructure.Performatifs;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import messageFilter.GenericFilter;

public class GoInside extends Behaviour {

	@Override
	public void action() {
		
		Passenger passenger = (Passenger) myAgent;
		
		MessageTemplate mt = new MessageTemplate(new GenericFilter(Performatifs.ASK_PASSENGER_TO_GO_IN));
		ACLMessage goInsideCarMessage = myAgent.receive(mt);

		if(goInsideCarMessage==null) {
			block();
		} else {
			ACLMessage iAmInsideMessage = new ACLMessage(Performatifs.ANSWER_PASSENGER_INSIDE);
			iAmInsideMessage.addReceiver(goInsideCarMessage.getSender());
			iAmInsideMessage.setContent("I'm in the car, let's go!");
			myAgent.send(iAmInsideMessage);
			passenger.passengerState = Passenger.INSIDE_CAR;
		}
	}

	@Override
	public boolean done() {
		return ((Passenger) myAgent).passengerState.equals(Passenger.INSIDE_CAR);
	}
	
}
