package rop;

import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.lang.reflect.Type;

public abstract class BaseEndpoint<E, M> {
    protected Response response;
    protected abstract Type getModelType();
    public abstract E sendRequest();
    protected abstract int getSuccessStatusCode();

    public M getResponseModel(){
        return response.as(getModelType());
    }

    public E assertRequestSuccess() {
        return assertStatusCode(getSuccessStatusCode());
    }

    private E assertStatusCode(int successStatusCode) {
        Assertions.assertThat(response.getStatusCode()).as("Status Code").isEqualTo(successStatusCode);
        return (E) this;
    }

}
