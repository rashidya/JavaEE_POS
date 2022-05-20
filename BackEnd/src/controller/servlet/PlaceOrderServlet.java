package controller.servlet;

import business.BOFactory;
import business.custom.CustomerBO;
import business.custom.PlaceOrderBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import entity.Order;
import javafx.collections.ObservableList;

import javax.annotation.Resource;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/order")
public class PlaceOrderServlet extends HttpServlet {
    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ORDER);

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        String id = req.getParameter("id");
        String option = req.getParameter("option");


        try {
            Connection connection = dataSource.getConnection();

            switch (option) {

                case "Customer":
                    CustomerDTO customer = placeOrderBO.getCustomer(id, connection);

                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                    objectBuilder.add("id", customer.getId());
                    objectBuilder.add("name", customer.getName());
                    objectBuilder.add("address", customer.getAddress());
                    objectBuilder.add("contact_No", customer.getContactNo());


                    writer.print(objectBuilder.build());
                    break;

                case "Item":
                    ItemDTO item = placeOrderBO.getItem(id, connection);

                    JsonObjectBuilder objectBuilder1 = Json.createObjectBuilder();

                    objectBuilder1.add("itemId", item.getId());
                    objectBuilder1.add("itemName", item.getItem());
                    objectBuilder1.add("price", item.getUnitPrice());
                    objectBuilder1.add("qty", item.getQty());


                    writer.print(objectBuilder1.build());
                    break;

                case "SearchOrder":
                    /*OrderDTO order = placeOrderBO.getOrder(id, connection);

                    JsonObjectBuilder objectBuilder2 = Json.createObjectBuilder();

                    objectBuilder2.add("itemId", item.getId());
                    objectBuilder2.add("itemName", item.getItem());
                    objectBuilder2.add("price", item.getUnitPrice());
                    objectBuilder2.add("qty", item.getQty());


                    writer.print(objectBuilder2.build());*/
                    break;


                /*case "ID":
                    String id = customerBO.generateID(connection);

                    JsonObjectBuilder objectBuilder1 = Json.createObjectBuilder();
                    objectBuilder1.add("id",id);
                    writer.print(objectBuilder1.build());
                    break;*/
            }

            connection.close();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }

       /* PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");



        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            ObservableList<CustomerDTO> allCustomers = customerBO.getAllCustomers(connection);
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            for (CustomerDTO dto : allCustomers) {
                JsonObjectBuilder ob = Json.createObjectBuilder();

                ob.add("id", dto.getId());
                ob.add("name", dto.getName());
                ob.add("address", dto.getAddress());
                ob.add("contactNo", dto.getContactNo());

                arrayBuilder.add(ob.build());

            }


            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status",200);
            response.add("message", "Table Loaded");
            response.add("data", arrayBuilder.build());

            writer.print(response.build());

            connection.close();
        } catch (SQLException | ClassNotFoundException e) {

            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());

            resp.setStatus(HttpServletResponse.SC_OK);
            e.printStackTrace();

        }*/
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonOb = reader.readObject();


        JsonArray orderItems = jsonOb.getJsonArray("orderItems");

        ArrayList<OrderDetailsDTO> orderDetailItems=new ArrayList<>();

        for (JsonValue item : orderItems) {
            JsonObject jo = item.asJsonObject();
            orderDetailItems.add(new OrderDetailsDTO(
                    jsonOb.getString("orderId"),
                    jo.getString("itemId"),
                    Integer.parseInt(jo.getString("itemQty"))));
        }



        OrderDTO orderDTO = new OrderDTO(
                jsonOb.getString("orderId"),
                jsonOb.getString("orderDate"),
                jsonOb.getString("cusId"),
                orderDetailItems,
                jsonOb.getString("total")

        );

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            if (placeOrderBO.placeOrder(orderDTO, connection)) {

                resp.setStatus(HttpServletResponse.SC_OK);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("message", "Order Successfully Added.");
                objectBuilder.add("status", resp.getStatus());
                writer.print(objectBuilder.build());

            }
            connection.close();
        } catch (SQLException e) {

            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());

            resp.setStatus(HttpServletResponse.SC_OK);
            e.printStackTrace();
        }


    }

}
