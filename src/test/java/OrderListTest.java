import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import sprint.api.ApiSettings;
import sprint.api.HttpStatus;
import sprint.objects.OrderLogics;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderListTest {
    ApiSettings apiSettings = new ApiSettings();
    OrderLogics orderLogics;

    @Before
    public void setup() {
        orderLogics = new OrderLogics();
        apiSettings.pingServer();
    }

    @Test
    @DisplayName("Get list of order")
    @Description("Get list of order")
    public void getOrderList() {
        ValidatableResponse response = orderLogics.getOrderListResponse();
        assertEquals(HttpStatus.OK.getValue(), response.extract().statusCode());
        List<String> orders = response.extract().body().path("orders");
        assertNotNull(orders);
    }
}
