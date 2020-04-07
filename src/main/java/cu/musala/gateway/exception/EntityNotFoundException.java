package cu.musala.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Entity")
public class EntityNotFoundException extends RuntimeException {
    private long id;
    public EntityNotFoundException(String message, long id) {
        super(message);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
