package behavior;

import agent.Car;
import dataStructure.RequestData;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import messageFilter.AnswerProposition;
import smaUtils.SmaUtils;

public class ReadAnswerProposition  extends Behaviour {
	
	private boolean hasAnswer = false;

	@Override
	public void action() {
		MessageTemplate mt = new MessageTemplate(new AnswerProposition());
		ACLMessage m = myAgent.receive(mt);
		if (m==null) {
			block();
		}
		else {
			hasAnswer = true;
			RequestData request = SmaUtils.getRequestDataFromMessage(m);
			if(request != null) {
				myAgent.addBehaviour(new GetPath(request));
			} else {
				((Car) myAgent).resetCar();
			}
		}
		
	}

	@Override
	public boolean done() {
		return hasAnswer;
	}

}
