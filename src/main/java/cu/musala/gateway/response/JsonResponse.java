package cu.musala.gateway.response;

public class JsonResponse {
    private String message;
    private int code;
    private boolean success;
    private Long id;

    public JsonResponse(String message, int code, boolean success, Long id) {
        this.message = message;
        this.code = code;
        this.success = success;
        this.id = id;
    }

    public JsonResponse(boolean success) {
        this.success = success;
    }

    public JsonResponse(boolean success, long id) {
        this.success = success;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
