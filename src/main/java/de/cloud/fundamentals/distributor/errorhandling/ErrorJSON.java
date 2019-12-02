package de.cloud.fundamentals.distributor.errorhandling;

import java.util.Map;

class ErrorJSON {

    private final Integer status;
    private final String error;
    private final String timestamp;
    private final String trace;

    ErrorJSON(int status, Map<String, Object> errorAttributes) {
        this.status = status;
        this.error = (String) errorAttributes.get("error");
        this.timestamp = errorAttributes.get("timestamp").toString();
        this.trace = (String) errorAttributes.get("trace");
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTrace() {
        return trace;
    }

    @Override
    public String toString() {
        return "ErrorJSON{" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", trace='" + trace + '\'' +
                '}';
    }
}