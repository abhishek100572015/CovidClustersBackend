package com.example.rest.webServices.restfulwebServices.Controllers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.webServices.restfulwebServices.Dao.HexagonDao;
import com.example.rest.webServices.restfulwebServices.Exceptions.WrongParametersforHexagon;
import com.example.rest.webServices.restfulwebServices.POJOs.NewHexagon;

@RestController
public class MainController {

	HexagonDao hexagonDao = new HexagonDao();

	@GetMapping("/test")
	public String test() {
		return "Abhishek";
	}

	@GetMapping("/showAll")
	public HashMap<String, Pair<Integer, Integer>> viewMapContents() {
		if (hexagonDao != null) {
			return hexagonDao.viewMapContents();
		}
		return null;
	}

	// For Adding a new Hexagon adjacent to an old hexagon
	@PostMapping("/AddHexagon")
	@ResponseBody
	public HashMap<String, Pair<Integer, Integer>> addHexagon(@RequestBody NewHexagon newHexagon) {

		if (hexagonDao == null || newHexagon == null) {
			return null;
		}
		String newHexName = newHexagon.getNewHexagon();
		String neighborName = newHexagon.getNeighborName();
		int commonBoundary = newHexagon.getBoundary();

		if (hexagonDao.isRequestInValid(newHexName, neighborName, commonBoundary)) {
			throw new WrongParametersforHexagon("Bad request Type for adding Hexagon");
		}

		HashMap<String, Pair<Integer, Integer>> result = hexagonDao.addNewHexagon(newHexName, neighborName,
				commonBoundary);
		return result;

	}

	// For querying the neighbors of hexagon
	@GetMapping("/neighbors/{name}")
	public HashMap<String, Pair<Integer, Integer>> findNeighborsof(@PathVariable String name) {

		if (hexagonDao == null) {
			return null;
		}

		HashMap<String, Pair<Integer, Integer>> ans = hexagonDao.queryAllNeighbours(name);
		return ans;

	}

	// For deleting a Hexagon by name
	@DeleteMapping("/delete/{name}")
	public Map<String, Boolean> deleteHexagon(@PathVariable String name) {

		if (hexagonDao == null) {
			return null;
		}

		Boolean ans = hexagonDao.removeHexagon(name);
		Map<String, Boolean> mp = new LinkedHashMap<>();
		mp.put("status", ans);
		return mp;

	}

}
