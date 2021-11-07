package in.santhosh.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.santhosh.dao.IRCTRailwayDao;
import in.santhosh.exception.PosidexException;
import in.santhosh.response.IRCTResponse;

@Service
public class IRCTRailwayServiceImpl implements IRCTRailwayService {

	@Autowired
	IRCTRailwayDao dao;
	
	Logger logger = Logger.getLogger(IRCTRailwayServiceImpl.class);

	@Override
	public int getResponse(String requestId, String from, String to, String trainNumber, String trainName) throws PosidexException {

		logger.info("In IRCTRailwayServiceImpl class getResponse method");
		List<IRCTResponse> trainsList = null;

		int count = dao.checkTarget(from, to, trainNumber,trainName);
		int insertCountInReport = 0;
		
		logger.info("Trains Count :: "+count);

		if (count > 0) {

			trainsList = dao.getListOfTrains(from, to, requestId,trainNumber,trainName);

			if (trainsList != null) {

				insertCountInReport = dao.insertInReport(trainsList);

			}
			else {
				logger.info("Error While Fetching the List of Trains");
				throw new PosidexException("Got Error While Fetching List of Trains");
			}

		}

		else {
			logger.info("No trains Found");
			throw new PosidexException("No Trains Found");
		}
		return insertCountInReport;

	}

}
