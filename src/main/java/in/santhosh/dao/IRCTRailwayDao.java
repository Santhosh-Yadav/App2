package in.santhosh.dao;

import java.util.List;

import in.santhosh.response.IRCTResponse;

public interface IRCTRailwayDao {
	
	public int checkTarget(String from, String to, String trainNumber, String trainName);
	
	public List<IRCTResponse> getListOfTrains(String from, String to,String requestId, String trainNumber, String trainName);
	
	public int insertInReport(List<IRCTResponse> listOfTrains);

}
