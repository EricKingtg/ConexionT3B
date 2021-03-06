package com.org.bbb.conexion.impl;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.org.bbb.conexion.Conexion;
import com.org.bbb.utils.Config;

@Component("conexion")
public class ConexionImp implements Conexion {

	@Autowired
	@Qualifier("configuracion")
	private Config configuracion;

	@Override
	public Connection creaConexion(String driver) {

		System.out.println(configuracion.toString());
		Connection conn = null;
		try {
			Class.forName(configuracion.getDriv());
			DriverManager.setLoginTimeout(10);
			if (driver.equalsIgnoreCase("sybase")) {
				conn = DriverManager
						.getConnection(
								configuracion.getSurlSB() + configuracion.getHostSB() + ":" + configuracion.getPortSB()
										+ "/" + configuracion.getNameSB(),
								configuracion.getUserSB(), configuracion.getPassSB());

			}
			if (driver.equalsIgnoreCase("mysql")) {
				conn = DriverManager.getConnection(
						configuracion.getSurl() + "//" + configuracion.getHost() + ":" + configuracion.getPort() + "/"
								+ configuracion.getName()
								+ "?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC",
						configuracion.getUser(), configuracion.getPass());
			}
		} catch (Exception e) {
			System.out.println("Ocurrio un error al obtener la conexion: " + e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}
}