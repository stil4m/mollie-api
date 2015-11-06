package nl.stil4m.mollie;

public class Response<T> {

    public final int status;
    public final T payload;

    public Response(int status, T payload) {
        this.status = status;
        this.payload = payload;
    }
}
