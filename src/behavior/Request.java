package behavior;

import java.io.IOException;

import agent.Passenger;
import dataStructure.PassengerData;
import dataStructure.Performatifs;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class Request extends OneShotBehaviour {

	static final int WAIT_BEFORE_ACCEPT = 2000;

	@Override
	public void action() {
		
		// ----- SEND REQUEST TO ALL CARS ----
		ServiceDescription serviceDescription = new ServiceDescription();
	    serviceDescription.setType("car");
		DFAgentDescription description = new DFAgentDescription();
		description.addServices(serviceDescription);
		DFAgentDescription[] descriptionList;
		try {
			descriptionList = DFService.search(myAgent, description);
			for (int i = 0; i < descriptionList.length; i++) {
				AID carAID = descriptionList[i].getName();
				PassengerData data = ((Passenger) myAgent).getPassengerData();
				ACLMessage request = new ACLMessage(Performatifs.PASSENGER_REQUEST);
				request.setContentObject(data);
				request.addReceiver(carAID);
				myAgent.send(request);
			}
			
			// ------- HANDLE PROPOSITIONS --------
			myAgent.addBehaviour(new HandleProposition(myAgent, WAIT_BEFORE_ACCEPT));
			
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}	

}
