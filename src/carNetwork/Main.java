package carNetwork;

import jade.core.Runtime;

import java.util.ArrayList;

import agent.Car;
import agent.House;
import agent.Map;
import agent.Passenger;
import dataStructure.CarData;
import dataStructure.HouseData;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Main {

	public static void main(String[] args) throws StaleProxyException {
		
		Runtime rt = Runtime.instance();
		rt.setCloseVM(true);
		Profile pMain = new ProfileImpl("localhost", 8888, null);
		AgentContainer mc = rt.createMainContainer(pMain);
				
		// DEFINE HOUSE AGENTS
		String houseId0 = "h-0";
		String houseId1 = "h-1";
		String houseId2 = "h-2";
		String houseId3 = "h-3";
		String houseId4 = "h-4";
		
		// init static passenger class
		String[] houseIds = { houseId0, houseId1, houseId2, houseId3 };
		Passenger.setHousesIds(houseIds);
		
		int[] position0 = { 100, 200 };
		int[] position1 = { 200, 200 };
		int[] position2 = { 300, 300 };
		int[] position3 = { 150, 400 };
		int[] position4 = { 500, 500 };
		
		HouseData h0 = new HouseData(houseId0, position0);
		HouseData h1 = new HouseData(houseId1, position1);
		HouseData h2 = new HouseData(houseId2, position2);
		HouseData h3 = new HouseData(houseId3, position3);
		HouseData h4 = new HouseData(houseId4, position4);

		h0.linkHouse(h1);
		h1.linkHouse(h2);
		h2.linkHouse(h3);
		h2.linkHouse(h4);
		h3.linkHouse(h0);
		
		ArrayList<HouseData> houses = new ArrayList<HouseData>();
		houses.add(h0);
		houses.add(h1);
		houses.add(h2);
		houses.add(h3);
		houses.add(h4);
		
		AgentController houseAgent0 = mc.createNewAgent(houseId0, House.class.getName(), new Object[]{ h0 } );
		AgentController houseAgent1 = mc.createNewAgent(houseId1, House.class.getName(), new Object[]{ h1 } );
		AgentController houseAgent2 = mc.createNewAgent(houseId2, House.class.getName(), new Object[]{ h2 } );
		AgentController houseAgent3 = mc.createNewAgent(houseId3, House.class.getName(), new Object[]{ h3 } );
		AgentController houseAgent4 = mc.createNewAgent(houseId4, House.class.getName(), new Object[]{ h4 } );

		// DEFINE CAR AGENTS
		String carId0 = "c-0";
		String carId1 = "c-1";
		
		CarData c0 = new CarData(carId0, h0.id);
		CarData c1 = new CarData(carId1, h2.id);
		
		ArrayList<CarData> cars = new ArrayList<CarData>();
		cars.add(c0);
		cars.add(c1);
		
		AgentController carAgent0 = mc.createNewAgent(carId0, Car.class.getName(), new Object[]{ c0 } );
		AgentController carAgent1 = mc.createNewAgent(carId1, Car.class.getName(), new Object[]{ c1 } );

		// START AGENTS
		houseAgent0.start();
		houseAgent1.start();
		houseAgent2.start();
		houseAgent3.start();
		houseAgent4.start();
		carAgent0.start();
		carAgent1.start();
		
		AgentController map = mc.createNewAgent("map", Map.class.getName(), new Object[]{ houses, cars } );
		map.start();
		
	}
}
