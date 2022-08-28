package sprint.objects;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import sprint.api.CourierApi;
import sprint.api.HttpStatus;

public class CourierLogics {
    Courier courier = new Courier();
    CourierApi courierApi = new CourierApi();

    public Courier createCourierWithRandomParameters() {
        courier.setId(RandomStringUtils.randomNumeric(10));
        courier.setLogin(RandomStringUtils.randomAlphanumeric(10));
        courier.setPassword(RandomStringUtils.randomAlphanumeric(10));
        courier.setFirstName(RandomStringUtils.randomAlphanumeric(10));
        return courier;
    }

    @Step("Create courier")
    public ValidatableResponse createNewCourier(Courier courier) {
        return courierApi.createNewCourier(courier);
    }

    @Step("Sign in courier")
    public ValidatableResponse loginCourier(String login, String password) {
        Courier courier = new Courier(login, password);
        return courierApi.loginCourier(courier);
    }

    @Step("Courier is unique")
    public boolean checkCourierExists(String login, String password) {
        Courier courier = new Courier(login, password);
        ValidatableResponse response = courierApi.loginCourier(courier);
        if (response.extract().statusCode() == HttpStatus.OK.getValue()) {
            System.out.println("Courier is not unique!"+ courier + response);
            return true;
        } else {
            System.out.println("Courier does not exist.");
            return false;
        }
    }

    @Step("Delete courier")
    public ValidatableResponse deleteCourier(Courier courier) {
            int courierId = loginCourier(courier.getLogin(), courier.getPassword())
                    .extract().body().path("id");
            courier.setId(String.valueOf(courierId));
        return courierApi.deleteCourier(courier);
    }
}
