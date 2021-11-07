package in.santhosh.service;

import in.santhosh.exception.PosidexException;

public interface IRCTRailwayService {
	
	public int getResponse(String requestId, String from, String to, String trainNumber, String trainName) throws PosidexException;

}
