package com.org.bbb.conexion;

import java.sql.Connection;

public interface Conexion {

	public abstract Connection creaConexion(String dbHost, String dbUser, String dbPass, String dbPort, String dbName,
			String dbClassDriver, String dbUrl);

}