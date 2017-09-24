/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tworope.reactor.reservation.frames;

import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.VertexFrame;
import java.util.Date;

/**
 *
 * @author tobah
 */
public interface Reservation extends VertexFrame {
    
    @Property("reservation_duration")
    public int getReservationDuration();

    @Property("reservation_duration")
    public void setReservationDuration(int reservationDuration); 
    
    @Property("reservation_date")
    public Date getReservationDate();

    @Property("reservation_date")
    public void setReservationDate(Date reservationDate); 
    
    @Property("reservation_status")
    public String getReservationStatus();

    @Property("reservation_status")
    public void setReservationStatus(String reservationStatus);
    
}
