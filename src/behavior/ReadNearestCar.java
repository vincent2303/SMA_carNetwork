package behavior;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import agent.Passenger;
import dataStructure.CarData;
import dataStructure.Performatifs;
import dataStructure.RequestData;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import messageFilter.AnswerNearest;
import smaUtils.SmaUtils;

public class ReadNearestCar extends Behaviour {
	
	private boolean hasAnswer = false;
	private ArrayList<CarData> propositionList;
	
	public ReadNearestCar(ArrayList<CarData> propositionList) {
		this.propositionList = propositionList;
	}

	@Override
	public void action() {
		
		MessageTemplate mt = new MessageTemplate(new AnswerNearest());
		ACLMessage m = myAgent.receive(mt);
		
		if (m==null) {
			block();
		} else {
			hasAnswer = true;
			Passenger passenger = (Passenger) myAgent;
			String idNearestCar = m.getContent();
			
			RequestData req = new RequestData(passenger.fromId, passenger.toId, passenger.id);
			
			// when we have the answer, we send a refuse message to all car exept the one we accept
			for(int i=0; i<propositionList.size(); i++) {
				CarData proposition = propositionList.get(i);
				
				AID carAID = SmaUtils.getAIDFromId(proposition.id, myAgent);
				ACLMessage answer = new ACLMessage(Performatifs.ANSWER_PROPOSITION);
				answer.addReceiver(carAID);
				if(proposition.id.equals(idNearestCar)) {
					try {
						answer.setContentObject(req);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				myAgent.send(answer);
				myAgent.addBehaviour(new GoInside()); // wait until he has a message to go in
			}
		}
	}

	@Override
	public boolean done() {
		return hasAnswer;
	}
	
}
