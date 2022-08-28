import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import sprint.api.ApiSettings;
import sprint.api.HttpStatus;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import sprint.objects.CourierLogics;
import sprint.objects.Courier;

import static org.junit.Assert.*;

public class CourierCreateTest {

    Courier courier;
    ApiSettings apiSettings = new ApiSettings();
    CourierLogics courierLogics = new CourierLogics();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        courier = courierLogics.createCourierWithRandomParameters();
        apiSettings.pingServer();
    }

    @Test
    @DisplayName("Courier can be created")
    @Description("Courier can be created")
    public void createNewCourier() {
        ValidatableResponse response = courierLogics.createNewCourier(courier);
        assertEquals(HttpStatus.CREATED.getValue(), response.extract().statusCode());
        assertTrue(response.extract().body().path("ok"));
        System.out.println("Courier is created!");
    }

    @Test
    @DisplayName("Can't create two identical couriers")
    @Description("Can't create two identical couriers")
    public void checkWhatDoNotPossibleCreateTwoSameCouriers() {
        ValidatableResponse creatingFirstCourier = courierLogics.createNewCourier(courier);
        assertEquals(HttpStatus.CREATED.getValue(), creatingFirstCourier.extract().statusCode());
        ValidatableResponse creatingSecondCourier = courierLogics.createNewCourier(courier);
        assertEquals(HttpStatus.CONFLICT.getValue(), creatingSecondCourier.extract().statusCode());
    }

    @Test
    @DisplayName("Can't create a courier without a login")
    @Description("Can't create a courier without a login")
    public void checkPossibleCreateCourierWithoutLogin() {
        courier.setLogin(null);
        ValidatableResponse creatingCourierWithoutLogin = courierLogics.createNewCourier(courier);
        assertEquals(HttpStatus.BAD_REQUEST.getValue(), creatingCourierWithoutLogin.extract().statusCode());
        assertEquals("Недостаточно данных для создания учетной записи", creatingCourierWithoutLogin.extract().body().path("message"));
    }

    @Test
    @DisplayName("Can't create a courier without a password")
    @Description("Can't create a courier without a password")
    public void checkPossibleCreateCourierWithoutPassword() {
        courier.setPassword(null);
        ValidatableResponse creatingCourierWithoutPassword = courierLogics.createNewCourier(courier);
        assertEquals(HttpStatus.BAD_REQUEST.getValue(), creatingCourierWithoutPassword.extract().statusCode());
        assertEquals("Недостаточно данных для создания учетной записи", creatingCourierWithoutPassword.extract().body().path("message"));
    }

    @Test
    @DisplayName("Can't create a courier with duplicate a login")
    @Description("Can't create a courier with duplicate a login")
    public void doNotPossibleCreateCourierWithAlreadyExistingLogin() {
        ValidatableResponse createFirstCourier = courierLogics.createNewCourier(courier);
        assertEquals(HttpStatus.CREATED.getValue(), createFirstCourier.extract().statusCode());

        ValidatableResponse createCourierWithExistLogin = courierLogics.createNewCourier(courier);
        assertEquals(createCourierWithExistLogin.extract().statusCode(), HttpStatus.CONFLICT.getValue());
        assertEquals(
                "Этот логин уже используется. Попробуйте другой.", createCourierWithExistLogin.extract().body().path("message"));
    }

    @After
    @DisplayName("Delete data")
    @Description("Delete data")
    public void clearData() {
        if (courierLogics.checkCourierExists(courier.getLogin(), courier.getPassword())) {
            courierLogics.deleteCourier(courier);
        }
        assertFalse(courierLogics.checkCourierExists(courier.getLogin(), courier.getPassword()));
    }

}
