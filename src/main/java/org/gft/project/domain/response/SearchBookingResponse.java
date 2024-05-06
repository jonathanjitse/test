package org.gft.project.domain.response;

public class SearchBookingResponse {

    private int bookingId;
    private String nmCustomer;
    private int nrDiners;
    private String dtBooking;
    private String dtEndBooking;
    public SearchBookingResponse() {
    }
    public SearchBookingResponse(int bookingId, String nmCustomer, int nrDiners, String dtBooking, String dtEndBooking) {
        this.bookingId = bookingId;
        this.nmCustomer = nmCustomer;
        this.nrDiners = nrDiners;
        this.dtBooking = dtBooking;
        this.dtEndBooking = dtEndBooking;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getNmCustomer() {
        return nmCustomer;
    }

    public void setNmCustomer(String nmCustomer) {
        this.nmCustomer = nmCustomer;
    }

    public int getNrDiners() {
        return nrDiners;
    }

    public void setNrDiners(int nrDiners) {
        this.nrDiners = nrDiners;
    }

    public String getDtBooking() {
        return dtBooking;
    }

    public void setDtBooking(String dtBooking) {
        this.dtBooking = dtBooking;
    }

    public String getDtEndBooking() {
        return dtEndBooking;
    }

    public void setDtEndBooking(String dtEndBooking) {
        this.dtEndBooking = dtEndBooking;
    }
}
