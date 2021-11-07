package in.santhosh.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
public class IRCTResponse {

	private String trainName;
	private String trainNo;
	private String availableTickets;
	private String boardingTime;
	private String departureTime;
	private String startingFrom;
	private String toLocation;
	private String requestId;
	private String eventType;

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public String getAvailableTickets() {
		return availableTickets;
	}

	public void setAvailableTickets(String availableTickets) {
		this.availableTickets = availableTickets;
	}

	public String getBoardingTime() {
		return boardingTime;
	}

	public void setBoardingTime(String boardingTime) {
		this.boardingTime = boardingTime;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getStartingFrom() {
		return startingFrom;
	}

	public void setStartingFrom(String startingFrom) {
		this.startingFrom = startingFrom;
	}

	public String getToLocation() {
		return toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	@Override
	public String toString() {
		return "IRCTResponse [trainName=" + trainName + ", trainNo=" + trainNo + ", availableTickets="
				+ availableTickets + ", boardingTime=" + boardingTime + ", departureTime=" + departureTime
				+ ", startingFrom=" + startingFrom + ", toLocation=" + toLocation + ", requestId=" + requestId
				+ ", eventType=" + eventType + "]";
	}

}
