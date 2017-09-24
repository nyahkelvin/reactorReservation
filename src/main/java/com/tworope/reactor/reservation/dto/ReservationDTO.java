/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tworope.reactor.reservation.dto;

import java.util.Date;

/**
 *
 * @author tobah
 */
public class ReservationDTO {
    private int reservationDuration;
    private Date reservationDate;
    private String reservationStatus;

    public int getReservationDuration() {
        return reservationDuration;
    }

    public void setReservationDuration(int reservationDuration) {
        this.reservationDuration = reservationDuration;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
    
    
}
