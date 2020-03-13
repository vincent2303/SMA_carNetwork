package behavior;

import java.io.IOException;

import agent.Car;
import dataStructure.CarData;
import dataStructure.DrivingInfo;
import dataStructure.PathRequest;
import dataStructure.Performatifs;
import dataStructure.RequestData;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import messageFilter.GenericFilter;
import smaUtils.SmaUtils;

public class GetPath extends Behaviour {

	private RequestData request;

	private int etape = 0;

	public GetPath(RequestData request) {
		this.request = request;
	}

	@Override
	public void action() {
		Car car = (Car) myAgent;
		
		if(etape==0) {
			
			CarData carData = car.carData;

			// REQUEST MEP TO GET PATH_TO_GET AND PATH_TO_SEND
			// else, request the map to get the nearest car
			ACLMessage pathRequestMessage = new ACLMessage(Performatifs.ASK_FOR_PATH);

			try {

				PathRequest pathRequest = new PathRequest(request, carData);

				pathRequestMessage.setContentObject(pathRequest);
			} catch (IOException e) { e.printStackTrace(); }

			pathRequestMessage.addReceiver(new AID("map", false));
			car.send(pathRequestMessage);
			etape++;
		} else {
			MessageTemplate mt = new MessageTemplate(new GenericFilter(Performatifs.ANSWER_FOR_PATH));
			ACLMessage pathMessage = myAgent.receive(mt);

			if(pathMessage==null) {
				block();
			} else {
				DrivingInfo drivingInfo = SmaUtils.getDrivingIfonFromMessage(pathMessage);
				car.drivingInfo = drivingInfo;
				car.state = Car.DRIVING_TO_GET;
				myAgent.addBehaviour(new DriveToGet(car, Car.CAR_DRIVING_TICK));
				etape++;
			}
		}
	}

	@Override
	public boolean done() {
		return etape==2;
	}

}
