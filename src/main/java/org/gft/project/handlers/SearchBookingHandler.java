package org.gft.project.handlers;

import com.google.gson.Gson;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import io.muserver.RouteHandler;
import jakarta.ws.rs.BadRequestException;
import org.gft.project.business.exceptions.EntityNotFoundException;
import org.gft.project.business.exceptions.UnknownException;
import org.gft.project.domain.response.GenericResponse;
import org.gft.project.domain.response.SearchBookingResponse;
import org.gft.project.utils.FormatUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.gft.project.config.DbConnectionHelper.createConnection;

public class SearchBookingHandler implements RouteHandler {

    @Override
    public void handle(MuRequest muRequest, MuResponse muResponse, Map<String, String> map) throws Exception {
        Gson gson = new Gson();
        muResponse.contentType("application/json;charset=utf-8");
        try {
            String date = map.get("date");
            validateRequest(date);
            List<SearchBookingResponse> responses = getBookings(date);
            muResponse.status(200);
            muResponse.write(gson.toJson(responses));
        } catch (BadRequestException e) {
            System.err.println(e.getMessage());
            muResponse.status(500);
            muResponse.write(gson.toJson(new GenericResponse(e.getMessage(), 5000)));
        } catch (EntityNotFoundException e) {
            System.err.println(e.getMessage());
            muResponse.status(500);
            muResponse.write(gson.toJson(new GenericResponse("There is no bookings", 8000)));
        }
    }

    private List<SearchBookingResponse> getBookings(String date) {
        String sql = "Select id, nm_customer, nr_diners, dt_booking, dt_endbooking from tbl_booking where dt_booking like ?";
        try {
            Connection con = createConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + date + "%");
            ResultSet resultSet = pstmt.executeQuery();
            List<SearchBookingResponse> responses = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nmCustomer = resultSet.getString("nm_customer");
                int nrDiners = resultSet.getInt("nr_diners");
                String dtBooking = resultSet.getString("dt_booking");
                String dtEndBooking = resultSet.getString("dt_endbooking");
                SearchBookingResponse response = new SearchBookingResponse(id, nmCustomer, nrDiners, dtBooking, dtEndBooking);
                responses.add(response);
            }
            validateResponse(responses);
            return responses;
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            throw new UnknownException("Unknown Error, please try again", 7000);
        }
    }

    private void validateRequest(String date) {
        if (FormatUtils.validateDate(date)) {
            throw new BadRequestException("date format error, the correct one is yyyy-MM-dd");
        }
    }

    private void validateResponse(List<SearchBookingResponse> responses) {
        if (responses.isEmpty()) {
            throw new EntityNotFoundException();
        }
    }
}
