package com.org.bbb.conexion.impl;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.org.bbb.conexion.Conexion;
import com.org.bbb.utils.Config;
import com.org.bbb.utils.HostSybase;

public class ConexionImp implements Conexion {

	@Autowired
	@Qualifier("configuracion")
	private Config configuracion;

	@Autowired
	@Qualifier("configIp")
	private HostSybase configIp;

	@Override
	public Connection creaConexion(String dbHost, String dbUser, String dbPass, String dbPort, String dbName,
			String dbClassDriver, String dbUrl) {
		Connection conn = null;

		try {
			Class.forName(dbClassDriver);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return conn;
	}

}