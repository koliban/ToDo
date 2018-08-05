package activitytest.com.example.weatherforecast.bean;

public class weatherBean {
    private String resultcode;
    private String  reason;
    private Result result;


    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {

        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
