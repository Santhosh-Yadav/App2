package in.santhosh.response;

public class ResponseMessageBuilder {

	private String requestStatus;
	private String statusMessage;

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	@Override
	public String toString() {
		return "ResponseMessageBuilder [requestStatus=" + requestStatus + ", statusMessage=" + statusMessage + "]";
	}

}
