package com.example.rest.webServices.restfulwebServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javafx.util.Pair;

@RestController
public class UserResource {

	@Autowired
	private UserDaoService service;

	@GetMapping("/del")
	public String retrieve() {
		return "Abhishek";
	}

	// For deleting a Hexagon
	@GetMapping("/delete/{name}")
	public boolean retrieveAllUsers(@PathVariable String name) {
//		removeHexagon(name);
		return true;
	}

	// For querying
	@GetMapping("/neighbors/{name}")
	public ArrayList<String> retrieveUser(@PathVariable String name) {
		ArrayList<String> ans = queryAllNeighbours(name);
		return ans;

	}

	// For Adding a new Hexagon
	@PostMapping("/AddHexagon/{newHexagon}/{neighbor}/{boundary}")
	public ArrayList<Pair<String, Integer>> createUser(@PathVariable String newHexagon, @PathVariable String neighbor,
			@PathVariable int boundary) {
		ArrayList<Pair<String, Integer>> result = addNewHexagon(newHexagon, neighbor, boundary);
		return result;

	}

	static int[] xmove = { 0, 1, 1, 0, -1, -1 };
	static int[] ymove = { 1, 0, -1, -1, -1, 0 };

	static int[] xmove1 = { 0, 1, 1, 0, -1, -1 };
	static int[] ymove1 = { 1, 1, 0, -1, 0, 1 };

	static HashMap<String, Pair<Integer, Integer>> hexagons = new HashMap<String, Pair<Integer, Integer>>();

	private static String existsHexagonWithCenter(int newCordinateX, int newCordinateY) {

		for (HashMap.Entry<String, Pair<Integer, Integer>> entry : hexagons.entrySet()) {
			Pair<Integer, Integer> cordinates = entry.getValue();
			int xcoOrdinate = cordinates.getKey();
			int ycoOrdinate = cordinates.getValue();
			if (xcoOrdinate == newCordinateX && ycoOrdinate == newCordinateY) {
				return entry.getKey();
			}
		}
		return "";
	}

	private static ArrayList<Pair<String, Integer>> queryAllNeighboursWithBoundary(String name) {

		ArrayList<Pair<String, Integer>> ans = new ArrayList<>();
		Pair<Integer, Integer> cordinates = hexagons.get(name);
		int cordinatesX = cordinates.getKey();
		int cordinatesY = cordinates.getValue();

		int newCordinateX, newCordinateY;
		for (int i = 0; i < 6; i++) {
			if (cordinatesX % 2 == 1) {
				newCordinateX = cordinatesX + xmove1[i];
				newCordinateY = cordinatesY + ymove1[i];
			} else {
				newCordinateX = cordinatesX + xmove[i];
				newCordinateY = cordinatesY + ymove[i];
			}
			String nameOfHexagon = existsHexagonWithCenter(newCordinateX, newCordinateY);
			if (!nameOfHexagon.equals("")) {
				ans.add(new Pair<>(nameOfHexagon, i));
			}
		}
		return ans;
	}

	private static ArrayList<Pair<String, Integer>> addNewHexagon(String name, String neighbor, int boundary) {

		if (hexagons.size() == 0) {
			hexagons.put(name, new Pair<Integer, Integer>(0, 0));
			return new ArrayList<Pair<String, Integer>>();
		} else {
			Pair<Integer, Integer> points = hexagons.get(neighbor);
			int newCenterX, newCenterY;
			if (points.getKey() % 2 == 1) {
				newCenterX = points.getKey() + xmove1[boundary];
				newCenterY = points.getValue() + ymove1[boundary];
			} else {
				newCenterX = points.getKey() + xmove[boundary];
				newCenterY = points.getValue() + ymove[boundary];
			}
			hexagons.put(name, new Pair<Integer, Integer>(newCenterX, newCenterY));
		}
		return queryAllNeighboursWithBoundary(name);
	}

	private static Boolean bfs(String name, int xCordinate, int yCordinate) {
		int cnt = 0;
		HashMap<String, Boolean> vis = new HashMap<String, Boolean>();
		vis.put(name, true);
		LinkedList<String> q = new LinkedList<>();
		q.add(name);
		while (q.size() != 0) {
			name = q.poll();

			ArrayList<String> neighbors = queryAllNeighbours(name);
			for (int i = 0; i < neighbors.size(); i++) {
				if (!vis.get(neighbors.get(i))) {
					vis.put(neighbors.get(i), true);
					q.add(name);
					cnt++;
				}
			}

		}
		return (cnt == hexagons.size());

	}

	private static boolean removalIsPossible(String name) {
		Pair<Integer, Integer> pr;
		pr = hexagons.get(name);
		hexagons.remove(name);
		int xCordinate = pr.getKey();
		int yCordinate = pr.getValue();

		int newCordinateX, newCordinateY;
		for (int i = 0; i < 6; i++) {

			if (xCordinate % 2 == 1) {
				newCordinateX = xCordinate + xmove1[i];
				newCordinateY = yCordinate + ymove1[i];
			} else {
				newCordinateX = xCordinate + xmove[i];
				newCordinateY = yCordinate + ymove[i];
			}

			if (!existsHexagonWithCenter(newCordinateX, newCordinateY).equals("")) {
				if (true) {
					hexagons.put(name, pr);
					System.out.println("Removal Not Possible");
					return false;
				} else {
					return true;
				}

			}
		}
		return true;
	}

	private static ArrayList<String> queryAllNeighbours(String name) {

		ArrayList<String> ans = new ArrayList<>();
		Pair<Integer, Integer> cordinates = hexagons.get(name);
		int cordinatesX = cordinates.getKey();
		int cordinatesY = cordinates.getValue();

		int newCordinateX, newCordinateY;
		for (int i = 0; i < 6; i++) {
			if (cordinatesX % 2 == 1) {
				newCordinateX = cordinatesX + xmove1[i];
				newCordinateY = cordinatesY + ymove1[i];
			} else {
				newCordinateX = cordinatesX + xmove[i];
				newCordinateY = cordinatesY + ymove[i];
			}
			String nameOfHexagon = existsHexagonWithCenter(newCordinateX, newCordinateY);
			if (!nameOfHexagon.equals("")) {
				ans.add(nameOfHexagon);
			}
		}
		return ans;
	}

	private static void removeHexagon(String name) {
		if (removalIsPossible(name)) {
			System.out.println("Removed");
		}
	}

}
