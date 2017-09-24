/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tworope.reactor.reservation;

import com.tworope.reactor.reservation.data.ReservationDAO;
import com.tworope.reactor.reservation.dto.ReservationDTO;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author tobah
 */
public class ReservationVerticle extends AbstractVerticle {
    
    private final static Logger LOGGER = Logger.getLogger(ReservationVerticle.class.getName());

    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String CONTENT_TYPE_TEXT = "content-type";

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ReservationVerticle());
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        startHTTPServer();
    }

    private Future<Void> startHTTPServer() {
        Future<Void> future = Future.future();
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route().handler(CorsHandler.create("*")
                .allowedHeader("Content-Type"));
        router.route().handler(BodyHandler.create());

        router.get("/reservation").handler(this::homeRoute);
        router.get("/reservation").handler(this::saveReservationRoute);
        router.delete("/reservation").handler(this::deleteReservationRoute);

        server.requestHandler(router::accept).listen(8080, ar -> {
            if (ar.succeeded()) {
                LOGGER.info("Web Server started");
                future.complete();
            } else {
                LOGGER.info("Server failed to start");
                future.fail(ar.cause());
            }
        });

        return future;
    }

    private void homeRoute(RoutingContext context) {

        vertx.<ReservationDTO>executeBlocking(future -> {
            ReservationDTO reservationDTO = null;
            try {
                ReservationDAO reservationDAO = new ReservationDAO();
                reservationDTO = reservationDAO.getAllTickets();
                System.out.println("Same block code goes here");
            } catch (Exception e) {
                System.out.println("Error occurred " + e);
            }
            future.complete(reservationDTO);
        }, response -> {
            if (response.succeeded()) {
                context.response().putHeader(CONTENT_TYPE_TEXT, JSON_CONTENT_TYPE)
                        .end(Json.encodePrettily(response.result()));
            } else {
                context.response().putHeader(CONTENT_TYPE_TEXT, JSON_CONTENT_TYPE)
                        .end(Json.encodePrettily(new JsonObject().put("error", response.cause())));
                System.out.println("Something happened " + response.cause());
            }
        });

    }
    
    private void saveReservationRoute(RoutingContext context) {

        vertx.executeBlocking(future -> {
            
            ReservationDTO reservationDTO = new ReservationDTO();
            
            reservationDTO.setReservationDuration(Integer.parseInt(context.request().getParam("duration")));
            reservationDTO.setReservationDate(new Date(context.request().getParam("date")));
            reservationDTO.setReservationStatus(context.request().getParam("status"));
            
            try {
                ReservationDAO reservationDAO = new ReservationDAO();
                reservationDAO.saveReservation(reservationDTO);
                
                System.out.println("Same block code goes here");
                
            } catch (Exception e) {
                System.out.println("Error occurred " + e);
            }
            future.complete();
        }, response -> {
            if (response.succeeded()) {
                context.response().putHeader(CONTENT_TYPE_TEXT, JSON_CONTENT_TYPE)
                        .end(Json.encodePrettily(new JsonObject().put("success", response.result())));
            } else {
                context.response().putHeader(CONTENT_TYPE_TEXT, JSON_CONTENT_TYPE)
                        .end(Json.encodePrettily(new JsonObject().put("error", response.cause())));
                System.out.println("Something happened " + response.cause());
            }
        });

    }
    
    private void deleteReservationRoute(RoutingContext context){
        
        vertx.executeBlocking(future -> {
            
            ReservationDTO reservationDTO = new ReservationDTO();
            
            
            try {
                ReservationDAO reservationDAO = new ReservationDAO();
                reservationDAO.deleteReservation();
                
                System.out.println("Same block code goes here");
                
            } catch (Exception e) {
                System.out.println("Error occurred " + e);
            }
            future.complete();
        }, response -> {
            if (response.succeeded()) {
                context.response().putHeader(CONTENT_TYPE_TEXT, JSON_CONTENT_TYPE)
                        .end(Json.encodePrettily(new JsonObject().put("success", response.result())));
            } else {
                context.response().putHeader(CONTENT_TYPE_TEXT, JSON_CONTENT_TYPE)
                        .end(Json.encodePrettily(new JsonObject().put("error", response.cause())));
                System.out.println("Something happened " + response.cause());
            }
        });
        
        
    }
}
