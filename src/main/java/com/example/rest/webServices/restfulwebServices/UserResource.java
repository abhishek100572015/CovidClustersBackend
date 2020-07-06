package com.example.rest.webServices.restfulwebServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {

	@GetMapping("/del")
	public String retrieve() {
		return "Abhishek";
	}
@GetMapping("/showAll")
	public HashMap<String, Pair<Integer, Integer>> viewMapContents() {
		if(!start) {
			hexagons.put("axa", Pair.of(0, 0));
			start = true;
		}
		return hexagons;
	}

	// For deleting a Hexagon
	@DeleteMapping("/delete/{name}")
	public boolean retrieveAllUsers(@PathVariable String name) {
		return removeHexagon(name);
	}

	// For querying
	@GetMapping("/neighbors/{name}")
	public HashMap<String, Pair<Integer, Integer>> retrieveUser(@PathVariable String name) {
		HashMap<String, Pair<Integer, Integer>> ans = queryAllNeighbours(name);
		return ans;

	}

	// For Adding a new Hexagon
	@PostMapping("/AddHexagon")
	@ResponseBody
	public HashMap<String, Pair<Integer, Integer>> createUser(@RequestBody LoginForm login) {

		String newHexagon = login.getNewHexagon();
		String neighbor = login.getName();
		int boundary = login.getBoundary();
		HashMap<String, Pair<Integer, Integer>> result = addNewHexagon(newHexagon, neighbor, boundary);
		return result;

	}

        boolean start = false;
	static int[] xmove = { 0, 1, 1, 0, -1, -1 };
	static int[] ymove = { 1, 0, -1, -1, -1, 0 };

	static int[] xmove1 = { 0, 1, 1, 0, -1, -1 };
	static int[] ymove1 = { 1, 1, 0, -1, 0, 1 };

	static HashMap<String, Pair<Integer, Integer>> hexagons = new HashMap<String, Pair<Integer, Integer>>();

	private static String existsHexagonWithCenter(int newCordinateX, int newCordinateY) {

		for (HashMap.Entry<String, Pair<Integer, Integer>> entry : hexagons.entrySet()) {
			Pair<Integer, Integer> cordinates = entry.getValue();
			int xcoOrdinate = cordinates.getFirst();
			int ycoOrdinate = cordinates.getSecond();
			if (xcoOrdinate == newCordinateX && ycoOrdinate == newCordinateY) {
				return entry.getKey();
			}
		}
		return "";
	}

	private static ArrayList<Pair<String, Integer>> queryAllNeighboursWithBoundary(String name) {

		ArrayList<Pair<String, Integer>> ans = new ArrayList<>();
		Pair<Integer, Integer> cordinates = hexagons.get(name);
		int cordinatesX = cordinates.getFirst();
		int cordinatesY = cordinates.getSecond();

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
				ans.add(Pair.of(nameOfHexagon, i));
			}
		}
		return ans;
	}

	private static HashMap<String, Pair<Integer, Integer>> addNewHexagon(String name, String neighbor, int boundary) {

		if (hexagons.size() == 0) {
			HashMap<String, Pair<Integer, Integer>> ans = new HashMap<>();
			hexagons.put(name, Pair.of(0, 0));
			ans.put(name, hexagons.get(name));
			return ans;

		} else {
			Pair<Integer, Integer> points = hexagons.get(neighbor);
			int newCenterX, newCenterY;
			if (points.getFirst() % 2 == 1) {
				newCenterX = points.getFirst() + xmove1[boundary];
				newCenterY = points.getSecond() + ymove1[boundary];
			} else {
				newCenterX = points.getFirst() + xmove[boundary];
				newCenterY = points.getSecond() + ymove[boundary];
			}
			hexagons.put(name, Pair.of(newCenterX, newCenterY));
		}
		return queryAllNeighbours(name);
	}

	private static Boolean bfs(String name, int xCordinate, int yCordinate) {
		int cnt = 1;
		HashMap<String, Boolean> vis = new HashMap<String, Boolean>();
		vis.put(name, true);
		LinkedList<String> q = new LinkedList<>();
		q.add(name);
		while (q.size() != 0) {
			name = q.poll();

			Set<String> neighbors = queryAllNeighbours(name).keySet();

			Iterator<String> itr = neighbors.iterator(); // traversing over HashSet

			while (itr.hasNext()) {
				String nextHex = itr.next();
				if (vis.get(nextHex) == null) {
					vis.put(nextHex, true);
					q.add(nextHex);
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
		int xCordinate = pr.getFirst();
		int yCordinate = pr.getSecond();

		int newCordinateX, newCordinateY;
		for (int i = 0; i < 6; i++) {

			if (xCordinate % 2 == 1) {
				newCordinateX = xCordinate + xmove1[i];
				newCordinateY = yCordinate + ymove1[i];
			} else {
				newCordinateX = xCordinate + xmove[i];
				newCordinateY = yCordinate + ymove[i];
			}

			String newName = existsHexagonWithCenter(newCordinateX, newCordinateY);
			if (!newName.equals("")) {
				if (!bfs(newName, newCordinateX, newCordinateY)) {
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

	private static HashMap<String, Pair<Integer, Integer>> queryAllNeighbours(String name) {

		HashMap<String, Pair<Integer, Integer>> ans = new HashMap<>();
		ans.put(name, hexagons.get(name));
		Pair<Integer, Integer> cordinates = hexagons.get(name);
		int cordinatesX = cordinates.getFirst();
		int cordinatesY = cordinates.getSecond();

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
				ans.put(nameOfHexagon, hexagons.get(nameOfHexagon));
			}
		}
		return ans;
	}

	private static Boolean removeHexagon(String name) {
		if (removalIsPossible(name)) {
			return true;
		}
		return false;

	}

}
