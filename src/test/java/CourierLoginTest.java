import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import sprint.api.ApiSettings;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import sprint.objects.CourierLogics;
import sprint.objects.Courier;
import sprint.api.HttpStatus;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import static org.junit.Assert.*;

public class CourierLoginTest {
    Courier courier;
    ApiSettings apiSettings = new ApiSettings();
    CourierLogics courierLogics = new CourierLogics();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        apiSettings.pingServer();
        courier = courierLogics.createCourierWithRandomParameters();
    }

    @Test
    @DisplayName("The courier can log in")
    @Description("The courier can log in")
    public void checkCourierCanLogIn() {
        ValidatableResponse createNewCourierResponse = courierLogics.createNewCourier(courier);
        assertEquals(HttpStatus.CREATED.getValue(), createNewCourierResponse.extract().statusCode());

        ValidatableResponse logInCourier = courierLogics.loginCourier(courier.getLogin(), courier.getPassword());
        assertEquals(HttpStatus.OK.getValue(), logInCourier.extract().statusCode());
        int courierId = logInCourier.extract().body().path("id");
        assertTrue(courierId > 0);
    }

    @Test
    @DisplayName("Courier authorization without login")
    @Description("Courier authorization without login")
    public void checkCourierLogInWithoutLogin() {
        courier.setLogin(null);
        ValidatableResponse logInCourierWithoutData = courierLogics.loginCourier(courier.getLogin(), courier.getPassword());
        assertEquals(HttpStatus.BAD_REQUEST.getValue(), logInCourierWithoutData.extract().statusCode());
        assertEquals("Недостаточно данных для входа", logInCourierWithoutData.extract().body().path("message"));
    }

    @Test
    @DisplayName("Courier authorization without password")
    @Description("Courier authorization without password")
    public void checkCourierLogInWithoutPassword() {
        courier.setPassword(null);
        ValidatableResponse logInCourierWithoutData = courierLogics.loginCourier(courier.getLogin(), courier.getPassword());
        assertEquals(HttpStatus.BAD_REQUEST.getValue(), logInCourierWithoutData.extract().statusCode());
        assertEquals("Недостаточно данных для входа", logInCourierWithoutData.extract().body().path("message"));
    }

    @Test
    @DisplayName("Authorization of a non-existent courier")
    @Description("Authorization of a non-existent courier")
    public void checkCourierAuthWithIncorrectLogin() {
        assertFalse(courierLogics.checkCourierExists(courier.getLogin(), courier.getPassword()));
        ValidatableResponse response = courierLogics.loginCourier(courier.getLogin(), courier.getPassword());
        assertEquals(HttpStatus.NOT_FOUND.getValue(), response.extract().statusCode());
        assertEquals("Учетная запись не найдена", response.extract().body().path("message"));
    }

    @After
    public void clearData() {
        if (courierLogics.checkCourierExists(courier.getLogin(), courier.getPassword())) {
            courierLogics.deleteCourier(courier);
        }
        assertFalse(courierLogics.checkCourierExists(courier.getLogin(), courier.getPassword()));
    }
}
