/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tworope.reactor.reservation.test;

import com.tworope.reactor.reservation.data.ReservationDAO;
import com.tworope.reactor.reservation.dto.ReservationDTO;
import java.util.Date;

/**
 *
 * @author tobah
 */
public class Test {
    
    public static void main(String[] args) {
        Test test = new Test();
        ReservationDAO reservationDAO = new ReservationDAO();
        reservationDAO.saveReservation(test.createReservationDTO());
    }
    
    public ReservationDTO createReservationDTO(){
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setReservationDate(new Date());
        reservationDTO.setReservationDuration(4);
        reservationDTO.setReservationStatus("pending");
        
        return reservationDTO;
    }
}
