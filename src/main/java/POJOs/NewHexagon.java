package POJOs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewHexagon {

	@JsonProperty("newHexagon")
	public String newHexagon;

	@JsonProperty("neighbor")
	public String neighborName;

	public void setNeighborName(String neighborName) {
		this.neighborName = neighborName;
	}

	@JsonProperty("sharedEdgeNeighbor")
	public int boundary;

	public void setNewHexagon(String newHexagon) {
		this.newHexagon = newHexagon;
	}

	public void setBoundary(int boundary) {
		this.boundary = boundary;
	}

	public String getNewHexagon() {
		return newHexagon;
	}

	public String getNeighborName() {
		return neighborName;
	}

	public int getBoundary() {
		return boundary;
	}

}
