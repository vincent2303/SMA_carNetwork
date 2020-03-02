package behavior;

import java.io.IOException;

import agent.Car;
import jade.core.behaviours.TickerBehaviour;

public class Drive extends TickerBehaviour {
	
	Car car;

	public Drive(Car c, long period) {
		super(c, period);
		car = c;
	}

	protected void onTick() {
		car.carData.distance += 5;
	}
}
