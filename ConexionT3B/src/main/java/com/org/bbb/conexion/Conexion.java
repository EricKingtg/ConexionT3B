package com.org.bbb.conexion;

import java.sql.Connection;

public interface Conexion {

	public abstract Connection creaConexion(String cnn);

}