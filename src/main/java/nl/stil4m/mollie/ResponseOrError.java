package nl.stil4m.mollie;

import java.util.Optional;
import java.util.function.Consumer;

public class ResponseOrError<V,T> {

    public final Optional<V> data;
    public final Optional<T> error;
    private Boolean success;

    public ResponseOrError(V data) {
        this.data = Optional.of(data);
        this.error = Optional.empty();
        success = true;
    }
    public ResponseOrError(T error, Boolean success) {
        this.data = data;
        this.error = error;
        this.success = success;
    }

    public void get(Consumer<T> onSuccess, Consumer<T> onError) {
        if (data != null) {

        }
    }

    public V getData() {
        return data;
    }

    public T getError() {
        return error;
    }

    public Boolean getSuccess() {
        return success;
    }
}
