package nl.stil4m.mollie;

import java.util.Map;
import java.util.function.Consumer;

public class ResponseOrError<V> {

    private final int status;
    private final V data;
    private final Map error;
    private final Boolean success;

    private ResponseOrError(int status, V data, Map error, Boolean success) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.success = success;
    }

    public static <V, T> ResponseOrError withError(int status, Map error) {
        return new ResponseOrError<>(status, null, error, false);
    }

    public static <V, T> ResponseOrError withData(int status, V data) {
        return new ResponseOrError<>(status, data, null, true);
    }

    public void get(Consumer<V> onSuccess, Consumer<Map> onError) {
        if (success) {
            onSuccess.accept(data);
        } else {
            onError.accept(error);
        }
    }

    public int getStatus() {
        return status;
    }

    public V getData() {
        return data;
    }

    public Map getError() {
        return error;
    }

    public Boolean getSuccess() {
        return success;
    }
}
