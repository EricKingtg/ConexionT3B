package com.org.bbb.consulta;

import java.sql.Connection;
import java.util.ArrayList;

import com.org.bbb.dto.QryRespDTO;

public interface Consulta {

	public QryRespDTO consultaPLSQL(String driver, String sql);

	public QryRespDTO consulta(String driver, String sql);

	public QryRespDTO ejecutaSelectSP(String driver, String sql, ArrayList<Object> params);

	public QryRespDTO ejecutaSP(String driver, String sql, ArrayList<Object> params, ArrayList<Integer> paramsOut);

	public QryRespDTO ejecutaFN(String driver, String sql, ArrayList<Object> params, ArrayList<Integer> paramsOut);

	public void cerrarConexion(Connection conn);

}