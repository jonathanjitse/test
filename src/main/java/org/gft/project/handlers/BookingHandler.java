package org.gft.project.handlers;

import com.google.gson.Gson;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import io.muserver.RouteHandler;
import jakarta.ws.rs.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.gft.project.business.exceptions.ExistingBookingException;
import org.gft.project.business.exceptions.UnknownException;
import org.gft.project.domain.request.BookingRequest;
import org.gft.project.domain.response.GenericResponse;
import org.gft.project.utils.FormatUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static org.gft.project.config.DbConnectionHelper.createConnection;

public class BookingHandler implements RouteHandler {

    @Override
    public void handle(MuRequest muRequest, MuResponse muResponse, Map<String, String> map) throws Exception {
        Gson gson = new Gson();
        muResponse.contentType("application/json;charset=utf-8");
        try {
            BookingRequest request = gson.fromJson(muRequest.readBodyAsString(), BookingRequest.class);
            validateRequest(request);
            System.out.println("Inserting " + request);
            exists(request);
            Long res = insert(request);
            System.out.println(res);
            muResponse.status(201);
            muResponse.write(gson.toJson(new GenericResponse("you have a booking! this is your booking code: " + res, 1000)));
        } catch (ExistingBookingException e) {
            System.err.println(e.getMessage());
            muResponse.status(202);
            muResponse.write(gson.toJson(new GenericResponse(e.getMessage(), 4000)));
        } catch (UnknownException e) {
            System.err.println(e.getMessage());
            muResponse.status(500);
            muResponse.write(gson.toJson(new GenericResponse(e.getMessage(), e.getCode())));
        } catch (BadRequestException e) {
            System.err.println(e.getMessage());
            muResponse.status(500);
            muResponse.write(gson.toJson(new GenericResponse(e.getMessage(), 5000)));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            muResponse.status(500);
            muResponse.write(gson.toJson(new GenericResponse("Error Generating booking, please, contact support", 0)));
        }
    }

    private void validateRequest(BookingRequest request) {
        if (request == null) {
            throw new BadRequestException("Empty request is not allowed");
        }

        if (StringUtils.isBlank(request.getNmCustomer()) || FormatUtils.validateName(request.getNmCustomer())) {
            throw new BadRequestException("Incorrect format name");
        }

        if (request.getNrDiners() == 0) { //we can add a max number validation to be OK with restaurant capacity
            throw new BadRequestException("Invalid number of diners");
        }

        if (StringUtils.isBlank(request.getDtBooking()) || FormatUtils.validateDateTime(request.getDtBooking())) {
            throw new BadRequestException("Invalid date to be booking");
        }
    }

    private void exists(BookingRequest request) {
        String sql = "Select id from tbl_booking where nm_customer = ? and nr_diners = ? and dt_booking = ?";
        try {
            Connection con = createConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, request.getNmCustomer());
            pstmt.setInt(2, request.getNrDiners());
            pstmt.setString(3, request.getDtBooking());
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                throw new ExistingBookingException("Looks like you already have a booking, your previous booking code is " + resultSet.getLong(1));
            }
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            throw new UnknownException("Unknown Error, please try again", 2001);
        }
    }

    private Long insert(BookingRequest request) {
        String sql = "INSERT INTO tbl_booking (id, nm_customer, nr_diners, dt_booking, dt_endbooking) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection con = createConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            long newId = getLastIdFromTable(con) + 1;
            pstmt.setLong(1, newId);
            pstmt.setString(2, request.getNmCustomer());
            pstmt.setInt(3, request.getNrDiners());
            pstmt.setString(4, request.getDtBooking());
            pstmt.setString(5, FormatUtils.getEndDate(request.getDtBooking()));
            int updated = pstmt.executeUpdate();
            if (updated > 0) {
                return newId;
            }
            throw new UnknownException("Unknown Error, please try again", 4002);

        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            throw new UnknownException("Unknown Error, please try again", 4001);
        }
    }

    private long getLastIdFromTable(Connection con) throws SQLException {
        String sql = "SELECT MAX(id) FROM tbl_booking";
        try (PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                return 0L;
            }
        }
    }

    private void getCapacity() {
        //TODO implents validation to allow booking
    }
}
