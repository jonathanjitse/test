package org.gft.project;

import io.muserver.Method;
import io.muserver.MuServerBuilder;
import org.gft.project.config.DbConnectionHelper;
import org.gft.project.handlers.BookingHandler;
import org.gft.project.handlers.SearchBookingHandler;
import org.h2.tools.RunScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {
        Connection con = crearConexion();
        inicializarBasedeDatos(con);
        MuServerBuilder.httpServer()
                .withHttpPort(8080)
                .addHandler(Method.POST, "/booking/create", new BookingHandler())
                .addHandler(Method.GET, "/booking/search/{date}", new SearchBookingHandler())
                .start();
    }

    private static Connection crearConexion() throws SQLException {

        System.out.println("Creating DB connection");

        DbConnectionHelper.setUsername("sa");
        DbConnectionHelper.setPassword("");
        DbConnectionHelper.setConnectionUrl("jdbc:h2:mem:h2Demo");

        return DbConnectionHelper.createConnection();

    } // crearConexion

    private static void inicializarBasedeDatos(Connection conn) throws SQLException {

        System.out.println("Creationg Table to use");
        try {
            RunScript.execute(conn, new FileReader("/Users/j.aguilar.rodriguez/Documents/onefinancialWS/test/src/main/resources/createTable.sql"));
        } catch (FileNotFoundException e) {
            System.out.println("there is a problem creationg tables");
            throw new RuntimeException();
        }
    }
}