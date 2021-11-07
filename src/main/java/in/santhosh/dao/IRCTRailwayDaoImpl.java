package in.santhosh.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import in.santhosh.response.IRCTResponse;

@Repository
public class IRCTRailwayDaoImpl implements IRCTRailwayDao {
	
	Logger logger = Logger.getLogger(IRCTRailwayDaoImpl.class);

	@Autowired
	private Environment environment;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("deprecation")
	@Override
	public int checkTarget(String from, String to,String trainNumber, String trainName) {
		
		logger.info("In IRCTRailwayDaoImpl class checkTarget method");
		logger.info("from :: "+from);
		logger.info("to :: "+to);
		logger.info("trainNumber :: "+trainNumber);
		logger.info("trainName :: "+trainName);

		int count = 0;

		String sql = environment.getProperty("count_from_to_in_target");
		
		logger.info("Checking count in target query :: "+sql);

		count = jdbcTemplate.queryForObject(sql, new Object[] { from, to, trainNumber,trainName }, Integer.class);

		return count;
	}

	@Override
	public List<IRCTResponse> getListOfTrains(String from, String to, String requestId, String trainNumber, String trainName) {

		logger.info("In IRCTRailwayDaoImpl class getListOfTrains method");
		
		List<IRCTResponse> getTrains = new ArrayList<IRCTResponse>();

		String sql = environment.getProperty("get_list_of_trains");
		
		logger.info("Fetching list of trains query :: "+sql);

		return jdbcTemplate.query(sql, new ResultSetExtractor<List<IRCTResponse>>() {

			@Override
			public List<IRCTResponse> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					IRCTResponse train = new IRCTResponse();
					train.setAvailableTickets(rs.getString("available_tickets"));
					train.setBoardingTime(rs.getString("boarding_time"));
					train.setDepartureTime(rs.getString("departure_time"));
					train.setEventType("RESPONSE");
					train.setRequestId(requestId);
					train.setStartingFrom(rs.getString("starting_from"));
					train.setToLocation(rs.getString("to_location"));
					train.setTrainName(rs.getString("train_name"));
					train.setTrainNo(rs.getString("train_number"));

					getTrains.add(train);

				}
				return getTrains;
			}
		}, from, to,trainNumber,trainName);

	}

	@Override
	public int insertInReport(List<IRCTResponse> listOfTrains) {
		logger.info("In IRCTRailwayDaoImpl class insertInReport method");
		int count = 0;
		String sql = environment.getProperty("insert_in_report");
		
		logger.info("Insert in report query :: "+sql);
		for (IRCTResponse train : listOfTrains) {
			String availableTickets = train.getAvailableTickets();
			String boardingTime = train.getBoardingTime();
			String departureTime = train.getDepartureTime();
			String trainNumber = train.getTrainNo();
			String trainName = train.getTrainName();
			String fromLocation = train.getStartingFrom();
			String toLocation = train.getToLocation();
			String eventType = train.getEventType();
			String requestId = train.getRequestId();
			count = jdbcTemplate.update(sql, availableTickets, boardingTime, departureTime, trainNumber, trainName,
					fromLocation, toLocation, eventType, requestId);
		}

		return count;
	}

}
