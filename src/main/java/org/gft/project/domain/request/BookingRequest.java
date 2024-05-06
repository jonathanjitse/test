package org.gft.project.domain.request;

public class BookingRequest {

    private String nmCustomer;
    private int nrDiners;
    private String dtBooking;
    public BookingRequest() {
    }
    public BookingRequest(String nmCustomer, int nrDiners, String dtBooking) {
        this.nmCustomer = nmCustomer;
        this.nrDiners = nrDiners;
        this.dtBooking = dtBooking;
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

    public void setDtBooking(String dtBokking) {
        this.dtBooking = dtBokking;
    }

    @Override
    public String toString() {
        return "BookingRequest{" +
                "nmCustomer='" + nmCustomer + '\'' +
                ", nrDiners=" + nrDiners +
                ", dtBooking='" + dtBooking + '\'' +
                '}';
    }
}
