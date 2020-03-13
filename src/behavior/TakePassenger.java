package behavior;

import agent.Car;
import dataStructure.DrivingInfo;
import dataStructure.Performatifs;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import messageFilter.GenericFilter;
import smaUtils.SmaUtils;

public class TakePassenger extends Behaviour {
	
	static public final String ASK_PASSENGER = "ASK_PASSENGER";
	static public final String WAIT_ANSWER = "WAIT_ANSWER";
	static public final String READY_TO_GO = "READY_TO_GO";
	
	String currentStep = TakePassenger.ASK_PASSENGER;
	
	@Override
	public void action() {
		Car car = (Car) myAgent;
		
		if(currentStep.equals(TakePassenger.ASK_PASSENGER)) {
			ACLMessage goInsideMessage = new ACLMessage(Performatifs.ASK_PASSENGER_TO_GO_IN);
			goInsideMessage.addReceiver(car.engagedWithAID);
			goInsideMessage.setContent("I'm waiting fro you, you have to go inside the car");
			myAgent.send(goInsideMessage);
			currentStep = TakePassenger.WAIT_ANSWER;
			return;
		}
		
		if(currentStep.equals(TakePassenger.WAIT_ANSWER)) {
			MessageTemplate mt = new MessageTemplate(new GenericFilter(Performatifs.ANSWER_PASSENGER_INSIDE));
			ACLMessage readyToGoMessage = myAgent.receive(mt);

			if(readyToGoMessage==null) {
				block();
			} else {
				car.carData.insidePassengerId = car.engagedWithId;
				currentStep = TakePassenger.READY_TO_GO;
				car.addBehaviour(new DriveToSend(car, Car.CAR_DRIVING_TICK));
			}
		}

		
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return currentStep == TakePassenger.READY_TO_GO;
	}

}
