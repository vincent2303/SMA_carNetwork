package behavior;

import agent.Car;
import dataStructure.HouseData;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import smaUtils.SmaUtils;

public class DriveToSend extends TickerBehaviour {

	Car car;
	
	public DriveToSend(Car c, long period) {
		super(c, period);
		car = c;
		// first, we remove the first element of pathToGet and pathToSend
		// (because the first element is is id of the house he is already on)
		car.drivingInfo.pathToSend.remove(0);
	}

	@Override
	protected void onTick() {
		
		// we check if we are arrived
		if(car.drivingInfo.pathToSend.size() == 0 && car.carData.to == null) {
			// car.state = Car.WAIT_PASSENGER_TO_GO_INSIDE;
			// System.out.println("engaged with from drive" + car.engagedWithAID.toString());
			// car.addBehaviour(new TakePassenger());
			System.out.println("ARRIVED, SEND PASSENGER !");
			car.resetCar();
			this.stop();
			return;
		}
				
		if(car.carData.to == null) {
			HouseData nextHouseToGo = car.drivingInfo.pathToSend.remove(0);
			car.carData.to = nextHouseToGo;
		}
		
		int distanceDone = car.carData.distance;
		int distanceToDo = (int) SmaUtils.computeDistance(car.carData.from.position, car.carData.to.position);
		
		// if the distance left is higher than the distance the car can do in one tick, we increment the distance
		if(distanceDone+Car.CAR_SPEED < distanceToDo) {
			car.carData.distance += Car.CAR_SPEED;
			return;
		} 
		// else, we put the car on the next house
		else if(distanceDone <= distanceToDo) {
			car.carData.from = car.carData.to;
			car.carData.to = null;
			car.carData.distance = 0;
			return;
		}
	}

}
