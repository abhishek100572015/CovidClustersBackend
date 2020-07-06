package Dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class HexagonDao {

	Boolean start = false;

	int[] xmoveForEvenx = { 0, 1, 1, 0, -1, -1 };
	int[] ymoveForEvenx = { 1, 0, -1, -1, -1, 0 };

	int[] xmoveForOddx = { 0, 1, 1, 0, -1, -1 };
	int[] ymoveForOddx = { 1, 1, 0, -1, 0, 1 };

	HashMap<String, Pair<Integer, Integer>> hexagons = new HashMap<String, Pair<Integer, Integer>>();

	private String FindHexagonWithCordinates(int newCordinateX, int newCordinateY) {

		for (HashMap.Entry<String, Pair<Integer, Integer>> entry : hexagons.entrySet()) {

			Pair<Integer, Integer> cordinates = entry.getValue();
			int xcordinate = cordinates.getFirst();
			int ycordinate = cordinates.getSecond();
			if (xcordinate == newCordinateX && ycordinate == newCordinateY) {
				return entry.getKey();
			}
		}
		return "";
	}

	public HashMap<String, Pair<Integer, Integer>> viewMapContents() {
		if (start == false) {
			hexagons.put("axa", Pair.of(0, 0));
			start = true;
		}
		return hexagons;
	}

	private int newXcoordinates(int x, int y, int edge) {
		if (x % 2 == 1) {
			return (x + xmoveForOddx[edge]);
		} else {
			return (x + xmoveForEvenx[edge]);
		}

	}

	private int newYcoordinates(int x, int y, int edge) {
		if (x % 2 == 1) {
			return (y + ymoveForOddx[edge]);
		} else {
			return (y + ymoveForEvenx[edge]);
		}

	}

	public HashMap<String, Pair<Integer, Integer>> addNewHexagon(String name, String neighbor, int boundary) {

		if (hexagons.size() == 0) {
			// Insert the first hexagon at origin
			HashMap<String, Pair<Integer, Integer>> ans = new HashMap<>();
			hexagons.put(name, Pair.of(0, 0));
			ans.put(name, hexagons.get(name));
			return ans;

		} else {

			Pair<Integer, Integer> points = hexagons.get(neighbor);

			int newCenterX = newXcoordinates(points.getFirst(), points.getSecond(), boundary);
			int newCenterY = newYcoordinates(points.getFirst(), points.getSecond(), boundary);

			String oldHexagonPresent = FindHexagonWithCordinates(newCenterX, newCenterY);
			if (!oldHexagonPresent.equals("")) {
				hexagons.remove(oldHexagonPresent);
			}

			hexagons.put(name, Pair.of(newCenterX, newCenterY));
		}
		return queryAllNeighbours(name);
	}

	private Boolean bfs(String name, int xCordinate, int yCordinate) {
		int cnt = 1;
		// to keep track of visited hexagons
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
		// if after the removal of the hexagon all the hexagons can be visited then we
		// can remove the hexagon
		return (cnt == hexagons.size());

	}

	private boolean removalIsPossible(String name) {

		Pair<Integer, Integer> cordinates;
		cordinates = hexagons.get(name);
		hexagons.remove(name);
		int xCordinate = cordinates.getFirst();
		int yCordinate = cordinates.getSecond();

		for (int i = 0; i < 6; i++) {
			int newCordinateX = newXcoordinates(xCordinate, yCordinate, i);
			int newCordinateY = newYcoordinates(xCordinate, yCordinate, i);

			String newName = FindHexagonWithCordinates(newCordinateX, newCordinateY);

			if (!newName.equals("")) {

				if (!bfs(newName, newCordinateX, newCordinateY)) {
					// Removal Not Possible
					hexagons.put(name, cordinates);
					return false;
				} else {
					return true;
				}

			}
		}
		return true;
	}

	public HashMap<String, Pair<Integer, Integer>> queryAllNeighbours(String name) {

		// adding the original hexagon
		HashMap<String, Pair<Integer, Integer>> ans = new HashMap<>();
		ans.put(name, hexagons.get(name));

		Pair<Integer, Integer> cordinates = hexagons.get(name);
		int cordinatesX = cordinates.getFirst();
		int cordinatesY = cordinates.getSecond();

		for (int i = 0; i < 6; i++) {
			int newCordinateX = newXcoordinates(cordinatesX, cordinatesY, i);
			int newCordinateY = newYcoordinates(cordinatesX, cordinatesY, i);

			String nameOfHexagon = FindHexagonWithCordinates(newCordinateX, newCordinateY);
			if (!nameOfHexagon.equals("")) {
				ans.put(nameOfHexagon, hexagons.get(nameOfHexagon));
			}
		}
		return ans;
	}

	public Boolean removeHexagon(String name) {

		if (removalIsPossible(name)) {
			return true;
		}
		return false;

	}

	public void deleteAll() {
		hexagons.clear();

	}

}
