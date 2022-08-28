package sprint.api;

import io.restassured.response.ValidatableResponse;
import sprint.objects.Courier;

import static io.restassured.RestAssured.given;

public class CourierApi extends ApiSettings {
    public ValidatableResponse createNewCourier(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(api + "/courier")
                .then();
    }

    public ValidatableResponse loginCourier(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(api + "/courier/login")
                .then();
    }

    public ValidatableResponse deleteCourier(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .delete(api + "/courier/" + courier.getId())
                .then();
    }
}
