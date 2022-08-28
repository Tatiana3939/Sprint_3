package sprint.api;

import io.restassured.response.ValidatableResponse;
import sprint.objects.Order;

import java.net.URI;
import java.net.http.HttpRequest;

import static io.restassured.RestAssured.given;

public class OrderApi extends ApiSettings {

    public ValidatableResponse createNewOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(api + "/orders")
                .then();
    }

    public ValidatableResponse getOrderList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(api + "/orders")
                .then();
    }

    public HttpRequest getMetroStationList() {
        return HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + api +"/stations/search"))
                .build();
    }
}
