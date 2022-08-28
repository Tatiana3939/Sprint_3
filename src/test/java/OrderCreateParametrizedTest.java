import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import sprint.api.ApiSettings;
import sprint.api.HttpStatus;
import sprint.objects.Order;
import sprint.objects.OrderLogics;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderCreateParametrizedTest {
    private final String[] color;
    private OrderLogics orderLogics;
    ApiSettings apiSettings = new ApiSettings();

    public OrderCreateParametrizedTest(String[] color, String testName) {
        this.color = color;
    }

    @Before
    public void setup() {
        orderLogics = new OrderLogics();
        apiSettings.pingServer();
    }

    @Parameterized.Parameters(name = "Color in order: {1}")
    public static Object[][] isOrderCreated() {
        return new Object[][]{
                {new String[]{"BLACK"}, "Black"},
                {new String[]{"GREY"}, "Gray"},
                {new String[]{"BLACK", "GREY"}, "Black and gray"},
                {new String[]{}, "Color isn't selected"},
        };
    }

    @Test
    @DisplayName("Create new order with different color")
    @Description("Create new order with different color")
    public void createNewOrderWithColor() throws IOException, InterruptedException {
        Order order = orderLogics.getOrderWithRandomData();
        order.setColor(color);
        ValidatableResponse response = orderLogics.createNewOrderResponse(order);
        assertEquals(HttpStatus.CREATED.getValue(), response.extract().statusCode());
        int trackOrder = response.extract().body().path("track");
        assertTrue(trackOrder > 0);
    }
}
