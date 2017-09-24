/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tworope.reactor.reservation.data;

import com.orientechnologies.orient.core.id.ORecordId;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.frames.FramedGraph;
import com.tinkerpop.frames.FramedGraphFactory;
import com.tinkerpop.frames.modules.javahandler.JavaHandlerModule;
import com.tworope.reactor.reservation.dto.ReservationDTO;
import com.tworope.reactor.reservation.frames.Reservation;
import com.tworope.reactor.reservation.util.ReactiveFrame;

/**
 *
 * @author tobah
 */
public class ReservationDAO {
    
    public boolean saveReservation(ReservationDTO reservationDTO){
        OrientGraphFactory factory = new ReactiveFrame().getOrientGraphFactory();
        FramedGraph<OrientGraph> framedGraph = null;
        try {

            OrientGraph graph = factory.getTx();
            framedGraph = new FramedGraphFactory(new JavaHandlerModule()).create(graph);
            
            Reservation reservation = framedGraph.addVertex("class:Reservation", Reservation.class);
            
            reservation.setReservationDate(reservationDTO.getReservationDate());
            reservation.setReservationDuration(reservationDTO.getReservationDuration());
            reservation.setReservationStatus(reservationDTO.getReservationStatus());
            
            graph.commit();
            System.out.println("Reservation id before save " + reservation.asVertex().getId());
             return true;
        } catch (Exception e) {
            System.out.println("exception to add Reservation " + e);
        } finally {
            if (framedGraph != null) {
                framedGraph.shutdown();
            }
        }
        return false;
    }
    
    public ReservationDTO saveReservationDTO(Reservation reservation){
        
        ReservationDTO reservationDTO = new ReservationDTO();
        
        reservationDTO.setReservationDate(reservation.getReservationDate());
        reservationDTO.setReservationDuration(reservation.getReservationDuration());
        reservationDTO.setReservationStatus(reservation.getReservationStatus());
        
        return reservationDTO;
    }
    
    public ReservationDTO getAllTickets() {

        OrientGraphFactory factory = new ReactiveFrame().getOrientGraphFactory();
        FramedGraph<OrientGraph> framedGraph = null;
        ReservationDTO reservationDTO = null;
        
        try {

            OrientGraph graph = factory.getTx();
            framedGraph = new FramedGraphFactory(new JavaHandlerModule()).create(graph);

            ORecordId orid = new ORecordId("#25:0");

            Reservation reservation = framedGraph.getVertex(orid, Reservation.class);
            
            reservationDTO = saveReservationDTO(reservation);
            
            System.out.println("Reservation details " + reservationDTO);
            
        } catch (Exception e) {
            System.out.println("exception to retrieve Reservation " + e);
        } finally {
            if (framedGraph != null) {
                framedGraph.shutdown();
            }
        }
        return reservationDTO;
    }
    
    public boolean deleteReservation(){
        
        OrientGraphFactory factory = new ReactiveFrame().getOrientGraphFactory();
        FramedGraph<OrientGraph> framedGraph = null;

        try {

            OrientGraph graph = factory.getTx();
            framedGraph = new FramedGraphFactory(new JavaHandlerModule()).create(graph);

            ORecordId orid = new ORecordId("#25:0");

            Reservation reservation = framedGraph.getVertex(orid, Reservation.class);

            framedGraph.removeVertex(reservation.asVertex());
            
            System.out.println("Reservation deleted " + reservation.asVertex().getId());
            
            return true;
            
        } catch (Exception e) {
            System.out.println("exception to delete Reservation " + e);
        } finally {
            if (framedGraph != null) {
                framedGraph.shutdown();
            }
        }
        
        return false;
    }
}
