package agent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;

import behavior.ReadCarData;
import behavior.ReadPassengerData;
import dataStructure.CarData;
import dataStructure.HouseData;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import smaUtils.SmaUtils;

public class Map extends Agent {
	
	public HashMap<String,HouseData> housesMap = new HashMap<String,HouseData>();
	public HashMap<String, CarData> carsMap = new HashMap<String, CarData>();
	// key: id of house, value: hashSet of id of active passenger in this house
	public HashMap<String, HashSet<String>> housePassengersMap = new HashMap<String, HashSet<String>>();
	// key: id of car, value: hashSet of id of active passenger in this house
	public HashMap<String, HashSet<String>> carPassengersMap = new HashMap<String, HashSet<String>>();
	
	// ---------------------------- INIT METHODS --------------------------------------------------------
	
	private void initHouses(ArrayList<HouseData> houses) {
		for (HouseData h : houses) {
			this.housesMap.put(h.id, h);
		}
	}
	
	private void initCars(ArrayList<CarData> cars) {
		for (CarData c : cars) {
			this.carsMap.put(c.id, c);
		}
	}
	
	private void initHousePassengerMap() { // Loop on the houseMap to initialize the housePassengersMap
		for (Entry<String, HouseData> houseMapEntry : housesMap.entrySet()) {
			String houseId = houseMapEntry.getKey();
			HashSet<String> passengerSet = new HashSet<String>();
			housePassengersMap.put(houseId, passengerSet);
	    }
	}
	
	private void initCarPassengersMap() { // Loop on the houseMap to initialize the housePassengersMap
		for (Entry<String, CarData> carMapEntry : carsMap.entrySet()) {
			String carId = carMapEntry.getKey();
			HashSet<String> passengerSet = new HashSet<String>();
			carPassengersMap.put(carId, passengerSet);
	    }
	}
	
	private void register() {
	    ServiceDescription serviceDescription = new ServiceDescription();
	    serviceDescription.setName(getLocalName());
	    serviceDescription.setType("map");
	    
	    DFAgentDescription description = new DFAgentDescription();
	    description.addServices(serviceDescription);
	    
	    try {
			DFService.register(this, description);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}
		
	// -------------------------------- SETUP ----------------------------------------------------------------
	
	protected void setup(){
		
		ArrayList<HouseData> houses = (ArrayList<HouseData>) this.getArguments()[0];
		ArrayList<CarData> cars = (ArrayList<CarData>) this.getArguments()[1];
		
		register();
		
		initHouses(houses);
		initCars(cars);
		initHousePassengerMap();
		initCarPassengersMap();
		launchDrawing();
		
		this.addBehaviour(new ReadPassengerData());
		this.addBehaviour(new ReadCarData());
	}
	
	// ---------------------------- DRAWING SYSTEM --------------------------------------------------------
	
	
	private void launchDrawing() {
		Drawing drawing = new Drawing();
		JFrame f = new JFrame("ma fenetre");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawing.setPreferredSize(new Dimension(800,600));
		f.add(drawing);
		f.pack();
		f.setVisible(true);
		
		(new Thread() {
			private int DRAWING_TICK = 100;
			public void run() {
				while(true) {
					try { 
						Thread.sleep(this.DRAWING_TICK);
						drawing.repaint();
					} 
					catch (InterruptedException e) { } 
				}
			}
		}).start();
	}
	
	class Drawing extends JPanel {
		
		final int HOUSE_DIAMETER = 40;
		final Color HOUSE_COLOR = Color.GRAY;
		
		final Color ROUTE_COLOR = Color.BLACK;
		
		final int CAR_SIZE = 20;
		final Color CAR_COLOR = Color.RED;
		
		final int PASSENGER_SIZE = 12;
		final int PASSENGER_SPACE = 4;
		final Color PASSENGER_COLOR = Color.GREEN;
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			drawAllRoutes(g);
			drawAllHouses(g);
			drawAllCars(g);
			drawAllPassengersInHouses(g);
		}
		
		private void drawHouse(HouseData h, Graphics g) {
			g.setColor(HOUSE_COLOR);
			g.fillOval(h.position[0]-HOUSE_DIAMETER/2, h.position[1]-HOUSE_DIAMETER/2, HOUSE_DIAMETER, HOUSE_DIAMETER);
		}
		
		private void drawRoute(HouseData h0, HouseData h1 , Graphics g) {
			g.setColor(ROUTE_COLOR);
			g.drawLine(h0.position[0],h0.position[1], h1.position[0], h1.position[1]);
		}
		
		private void drawCar(CarData c, Graphics g) {
			g.setColor(CAR_COLOR);
			int[] fromPosition = housesMap.get(c.from).position;
			int[] carPosition;
			if(c.to == null) {
				carPosition = fromPosition;
			} else {
				int[] toPosition = housesMap.get(c.to).position;
				carPosition = SmaUtils.computePosition(fromPosition, toPosition, c.distance);
			}
			g.fillRect(carPosition[0] - CAR_SIZE/2, carPosition[1] - CAR_SIZE/2, CAR_SIZE, CAR_SIZE);
		}
		
		private void drawPassenger(int[] postion, Graphics g) {
			g.setColor(PASSENGER_COLOR);
			g.fillRect(postion[0] - PASSENGER_SIZE/2, postion[1] - PASSENGER_SIZE/2, PASSENGER_SIZE, PASSENGER_SIZE);
		}
		
		private void drawAllRoutes(Graphics g) {
			for (Entry<String, HouseData> houseMapEntry : housesMap.entrySet()) {
				HouseData house = houseMapEntry.getValue();
				for (int neigboorIndex = 0; neigboorIndex < house.neighboors.size(); neigboorIndex++) {
					String neighboorId = house.neighboors.get(neigboorIndex);
					HouseData neighboor = housesMap.get(neighboorId);
					drawRoute(house, neighboor, g);
			    }
		    }
		}
		
		private void drawAllHouses(Graphics g) {
			for (Entry<String, HouseData> houseMapEntry : housesMap.entrySet()) {
				HouseData h = houseMapEntry.getValue();
				drawHouse(h, g);
		    }
		}
		
		private void drawAllCars(Graphics g) {
			for (Entry<String, CarData> carsMapEntry : carsMap.entrySet()) {
				CarData c = carsMapEntry.getValue();
				drawCar(c, g);
		    }
		}
		
		private void drawAllPassengersInHouses(Graphics g) {
			for (Entry<String, HouseData> houseMapEntry : housesMap.entrySet()) {
				HouseData h = houseMapEntry.getValue();
				int passengerNumber = housePassengersMap.get(h.id).size();
				int[] housePosition = h.position;
				for (int i = 0; i < passengerNumber; i++) {
					int x = housePosition[0];
					int y = housePosition[1] - PASSENGER_SIZE/2 - HOUSE_DIAMETER/4 - (PASSENGER_SPACE+PASSENGER_SIZE)*(i+1);
					int[] passengerPosition = { x, y };
					drawPassenger(passengerPosition, g);
				}
		    }
		}
	}
}
