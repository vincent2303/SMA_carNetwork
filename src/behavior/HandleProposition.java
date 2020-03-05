package behavior;

import jade.lang.acl.ACLMessage;
import smaUtils.SmaUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import agent.Car;
import agent.Passenger;
import dataStructure.CarData;
import dataStructure.DestinationData;
import dataStructure.Performatifs;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;

public class HandleProposition extends WakerBehaviour {

	public HandleProposition(Agent a, int delay) {
		super(a, delay);
	}
	
	@Override
	public void onWake() {
		ACLMessage msg = myAgent.receive();
		
		ArrayList<CarData> dataPropositionList = new ArrayList<CarData>();
		HashMap<AID, CarData> AIDMap = new HashMap<AID, CarData>(); // key: AID value: carData
		
		while(msg != null) {
			CarData carData = SmaUtils.getCarDataFromMessage(msg);
			System.out.println("C'est bon j'ai recu ta propo " + carData.id);
			dataPropositionList.add(carData);
			AIDMap.put(msg.getSender(), carData);
			msg = myAgent.receive();
		}
		
		if(dataPropositionList.size() == 0) { // if no proposition request again
			myAgent.addBehaviour(new Request());
			return;
		}
		
		// else, request the map to get the nearest car
		ACLMessage message = new ACLMessage(Performatifs.ASK_NEAREST);
		
		try {
			String passengerFromId = ((Passenger) myAgent).getPassengerData().fromId;
			DestinationData destinationData = new DestinationData(passengerFromId, dataPropositionList);
			message.setContentObject(destinationData);
		} catch (IOException e) { e.printStackTrace(); }
		
		message.addReceiver(new AID("map", false));
		((Passenger) myAgent).send(message);
	}

}
