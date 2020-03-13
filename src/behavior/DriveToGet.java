package behavior;

import java.io.IOException;
import java.util.ArrayList;

import agent.Car;
import dataStructure.DrivingInfo;
import dataStructure.HouseData;
import jade.core.behaviours.TickerBehaviour;
import smaUtils.SmaUtils;

public class DriveToGet extends TickerBehaviour {
	
	Car car;

	public DriveToGet(Car c, long period) {
		super(c, period);
		car = c;
		// first, we remove the first element of pathToGet and pathToSend
		// (because the first element is is id of the house he is already on)
		car.drivingInfo.pathToGet.remove(0);
	}

	protected void onTick() {
		// we check if we are arrived
		if(car.drivingInfo.pathToGet.size() == 0 && car.carData.to == null) {
			car.state = Car.WAIT_PASSENGER_TO_GO_INSIDE;
			System.out.println("engaged with from drive" + car.engagedWithAID.toString());
			car.addBehaviour(new TakePassenger());
			this.stop();
			return;
		}
				
		if(car.carData.to == null) {
			HouseData nextHouseToGo = car.drivingInfo.pathToGet.remove(0);
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
