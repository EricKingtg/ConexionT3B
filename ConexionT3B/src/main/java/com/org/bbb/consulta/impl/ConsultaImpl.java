package com.org.bbb.consulta.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.org.bbb.conexion.Conexion;
import com.org.bbb.consulta.Consulta;
import com.org.bbb.dto.CampoDTO;
import com.org.bbb.dto.ColumnaDTO;
import com.org.bbb.dto.QryRespDTO;

@Service("consulta")
public class ConsultaImpl implements Consulta {

	@Autowired
	@Qualifier("conexion")
	private Conexion conexion;

	private final static int OUT = 5;

	@Override
	public QryRespDTO consultaPLSQL(String driver, String sql) {

		Connection conn = conexion.creaConexion(driver);
		QryRespDTO resp = new QryRespDTO();
		CallableStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareCall(sql);
			resp.setMsg("LA CONSULTA NO SE PUDO REALIZAR: " + sql);
			st.execute();
			resp.setRes(1);
			resp.setMsg("BLOQUE ANONIMO EJECUTADO");
		} catch (Exception ex) {
			resp.setRes(0);
			resp.setMsg("ModuloBDConexion" + "|" + this.getClass().toString() + "|" + "consulta: " + sql + " | "
					+ ex.toString());
			System.out.println("ModuloBDConexion" + "|" + this.getClass().toString() + "|" + "consulta: " + sql + " | "
					+ ex.toString());
		} finally {
			cerrarConexion(conn);
			cerrarRecursos(st, rs);
		}
		return resp;
	}

	@Override
	public QryRespDTO consulta(String driver, String sql) {

		Connection conn = conexion.creaConexion(driver);
		QryRespDTO resp = new QryRespDTO();
		CallableStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareCall(sql);
			resp.setMsg("LA CONSULTA NO SE PUDO REALIZAR: " + sql);
			if (sql.contains("INSERT") || sql.contains("UPDATE")) {
				resp.setNumReg(st.executeUpdate(sql));
				resp.setMsg("CONSULTA REALIZADA: " + sql);
				resp.setRes(resp.getNumReg() > 0 ? 1 : 0);
			} else {
				st.execute();
				rs = st.getResultSet();
				if (rs != null) {
					resp.setResultado(rs);
					resp.setMetadatos(rs.getMetaData());
					// columnas
					resp.setColumnas(obtieneColumnas(resp.getMetadatos()));
					// datos
					if (resp.getColumnas().size() > 0) {
						resp.setDatosTabla(obtieneDatos(rs, resp.getColumnas()));
						resp.setNumReg(resp.getDatosTabla().size());
					} else {

					}
					if (resp.getNumReg() > 0) {
						resp.setMsg("CONSULTA REALIZADA: " + sql);
						resp.setRes(1);
					}
				}
			}

		} catch (Exception ex) {
			resp.setRes(0);
			resp.setMsg("ModuloBDConexion" + "|" + this.getClass().toString() + "|" + "consulta: " + sql + " | "
					+ ex.toString());
			System.out.println("ModuloBDConexion" + "|" + this.getClass().toString() + "|" + "consulta: " + sql + " | "
					+ ex.toString());
		} finally {
			cerrarConexion(conn);
			cerrarRecursos(st, rs);
		}
		return resp;
	}

	@Override
	public QryRespDTO ejecutaSelectSP(String driver, String sql, ArrayList<Object> params) {
		
		System.out.println("Consulta.ejecutaSelectSP");
		
		Connection conn = conexion.creaConexion(driver);
		QryRespDTO resp = new QryRespDTO();
		CallableStatement st = null;
		ResultSet rs = null;
		int paramIn = 1;
		resp.setMsg("NO SE PUDO EJECUTAR EL SP: " + limpiaSQL(sql));
		try {
			st = conn.prepareCall(sql);
			if (params != null) {
				for (Object param : params) {
					st.setObject(paramIn++, param);
				}
			}
			rs = st.executeQuery();
			if (rs == null) {
				st.execute();
				rs = st.getResultSet();
				if (rs != null) {
					resp.setResultado(rs);
					resp.setMetadatos(rs.getMetaData());
					resp.setColumnas(this.obtieneColumnas(resp.getMetadatos()));
					if (resp.getColumnas().size() > 0) {
						resp.setDatosTabla(this.obtieneDatos(rs, resp.getColumnas()));
						resp.setNumReg(resp.getDatosTabla().size());
					}
					if (resp.getNumReg() > 0) {
						resp.setMsg("SP EJECUTADO: " + limpiaSQL(sql));
						resp.setRes(1);
					}
				}
			} else {
				resp.setResultado(rs);
				resp.setMetadatos(rs.getMetaData());
				resp.setColumnas(this.obtieneColumnas(resp.getMetadatos()));
				if (resp.getColumnas().size() > 0) {
					resp.setDatosTabla(this.obtieneDatos(rs, resp.getColumnas()));
					resp.setNumReg(resp.getDatosTabla().size());
				}
				if (resp.getNumReg() > 0) {
					resp.setMsg("SP EJECUTADO: " + limpiaSQL(sql));
					resp.setRes(1);
				}
			}
		} catch (Exception ex) {
			resp.setRes(0);
			resp.setMsg("ModuloBDConexion" + "|" + this.getClass().toString() + "|" + "ejecutaSelectSP: "
					+ limpiaSQL(sql) + " |" + ex.toString());
			System.out.println("ModuloBDConexion" + "|" + this.getClass().toString() + "|" + "ejecutaSelectSP: "
					+ limpiaSQL(sql) + " |" + ex.toString());
			ex.printStackTrace();
		} finally {
			cerrarConexion(conn);
			cerrarRecursos(st, rs);
		}

		return resp;
		
	}

	@Override
	public QryRespDTO ejecutaSP(String driver, String sql, ArrayList<Object> params, ArrayList<Integer> paramsOut) {
		Connection conn = conexion.creaConexion(driver);
		QryRespDTO resp = new QryRespDTO();
		CallableStatement st = null;
		ResultSet rs = null;
		int paramIn = 1, paramOut = 0;
		ArrayList<CampoDTO> salida = new ArrayList<>();
		resp.setMsg("NO SE PUDO EJECUTAR EL SP: " + limpiaSQL(sql));
		try {
			st = conn.prepareCall(sql);
			if (params != null) {
				for (Object param : params) {
					st.setObject(paramIn++, param);
				}
			}
			paramOut = paramIn;
			if (paramsOut != null) {
				for (Integer param : paramsOut) {
					st.registerOutParameter(paramIn++, param);
				}
			}
			st.execute();
			resp.setMetadatosParam(st.getParameterMetaData());
			if (resp.getMetadatosParam() == null) {
				int paramTotal = (params.size() + paramsOut.size());
				for (int i = paramOut; i <= paramTotal; i++) {
					CampoDTO dato = new CampoDTO();
					dato.setValor(st.getObject(i));
					salida.add(dato);
				}
			} else {
				for (int i = paramOut; i <= resp.getMetadatosParam().getParameterCount(); i++) {
					CampoDTO dato = new CampoDTO();
					dato.setClase(resp.getMetadatosParam().getParameterClassName(i));
					dato.setTipoId(resp.getMetadatosParam().getParameterType(i));
					dato.setEtiqueta(resp.getMetadatosParam().getParameterTypeName(i));
					dato.setValor(st.getObject(i));
					salida.add(dato);
				}
			}

			if (salida.size() > 0) {
				resp.setParamOut(salida);
				resp.setRes(1);
				resp.setMsg("SP EJECUTADO: " + limpiaSQL(sql));
			}

		} catch (Exception ex) {
			resp.setRes(0);
			resp.setMsg("ModuloBDConexion" + "|" + this.getClass().toString() + "|" + "ejecutaSP: " + limpiaSQL(sql)
					+ " | " + ex.toString());
			System.out.println("ModuloBDConexion" + "|" + this.getClass().toString() + "|" + "ejecutaSP: "
					+ limpiaSQL(sql) + " | " + ex.toString());
		} finally {
			cerrarConexion(conn);
			cerrarRecursos(st, rs);
		}
		return resp;
	}

	@Override
	public QryRespDTO ejecutaFN(String driver, String sql, ArrayList<Object> params, ArrayList<Integer> paramsOut) {
		Connection conn = conexion.creaConexion(driver);
		QryRespDTO resp = new QryRespDTO();
		CallableStatement st = null;
		ResultSet rs = null;
		int paramIn = 1, paramOut = 0, paramReturn = 0;
		ArrayList<CampoDTO> salida = new ArrayList<>();
		resp.setMsg("NO SE PUDO EJECUTAR EL SP: " + limpiaSQL(sql));
		try {
			st = conn.prepareCall(sql);
			st.registerOutParameter(paramIn++, paramsOut.get(paramReturn));
			paramReturn++;
			if (params != null) {
				for (Object param : params) {
					st.setObject(paramIn++, param);
				}
			}
			paramOut = paramIn;
			if (paramsOut.size() > 1) {
				for (int i = 1; i < paramsOut.size(); i++) {
					st.registerOutParameter(paramIn++, paramsOut.get(i));
				}
			}
			st.execute();
			resp.setMetadatosParam(st.getParameterMetaData());
			if (resp.getMetadatosParam() == null) {
				int paramTotal = (params.size() + paramsOut.size());
				CampoDTO paramSalida = new CampoDTO();
				paramSalida.setValor(st.getObject(1));

				for (int i = paramOut; i <= paramTotal; i++) {
					CampoDTO dato = new CampoDTO();
					dato.setValor(st.getObject(i));
					salida.add(dato);
				}
			} else {
				for (int i = 1; i <= resp.getMetadatosParam().getParameterCount(); i++) {
					CampoDTO dato = new CampoDTO();
					dato.setClase(resp.getMetadatosParam().getParameterClassName(i));
					dato.setTipoId(resp.getMetadatosParam().getParameterType(i));
					dato.setEtiqueta(resp.getMetadatosParam().getParameterTypeName(i));
					if (resp.getMetadatosParam().getParameterMode(i) == OUT) {
						dato.setValor(st.getObject(i));
						salida.add(dato);
					}

				}
			}

			if (salida.size() > 0) {
				resp.setParamOut(salida);
				resp.setRes(1);
				resp.setMsg("FN EJECUTADO: " + limpiaSQL(sql));
			}
		} catch (Exception ex) {
			resp.setRes(0);
			resp.setMsg("ModuloBDConexion" + "|" + this.getClass().toString() + "|" + "ejecutaSP: " + limpiaSQL(sql)
					+ " | " + ex.toString());
			System.out.println("ModuloBDConexion" + "|" + this.getClass().toString() + "|" + "ejecutaSP: "
					+ limpiaSQL(sql) + " | " + ex.toString());
		} finally {
			cerrarConexion(conn);
			cerrarRecursos(st, rs);
		}
		return resp;
	}

	@Override
	public void cerrarConexion(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (Exception ex) {
			System.out.println("ModuloBDConexion" + "|" + this.getClass().toString() + "|" + "cerrarConexion" + "|"
					+ ex.toString());
		}
	}

	private void cerrarRecursos(CallableStatement st, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
		} catch (Exception ex) {
			System.out.println(
					"ModuloBDConexion" + "|" + this.getClass().toString() + "|" + "cerrar" + "|" + ex.toString());
		}
	}

	private ArrayList<ColumnaDTO> obtieneColumnas(ResultSetMetaData rsmd) {
		ArrayList<ColumnaDTO> lista = new ArrayList<>();
		try {
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				ColumnaDTO dto = new ColumnaDTO();
				try {
					dto.setEtiqueta(rsmd.getColumnLabel(i));
					dto.setNombre(rsmd.getColumnName(i));
					dto.setTipo(rsmd.getColumnTypeName(i));
					dto.setIdTipo(rsmd.getColumnType(i));
					dto.setLongitud(rsmd.getColumnDisplaySize(i));
					dto.setClase(rsmd.getColumnClassName(i));
				} catch (Exception ex) {
					System.out.println("Excepcion obteniendo las columnas: " + ex.toString());
				}
				lista.add(dto);
			}
		} catch (Exception ex) {
			lista = null;
			System.out.println("ModuloBDConexion" + "|" + this.getClass().toString() + "|" + "obtieneColumnas" + "|"
					+ ex.toString());
		}
		return lista;
	}

	private ArrayList<HashMap<String, CampoDTO>> obtieneDatos(ResultSet rs, ArrayList<ColumnaDTO> columnas) {
		ArrayList<HashMap<String, CampoDTO>> datos = new ArrayList<>();
		try {
			while (rs.next()) {
				HashMap<String, CampoDTO> fila = new HashMap<>();
				for (ColumnaDTO dto : columnas) {
					CampoDTO campo = new CampoDTO();
					campo.setEtiqueta(dto.getEtiqueta());
					campo.setNombre(dto.getNombre());
					try {
						campo.setValor(rs.getObject(dto.getEtiqueta()) == null ? "-" : rs.getObject(dto.getEtiqueta()));
					} catch (Exception ex) {
						campo.setValor("");
					}
					campo.setClase(dto.getClase());
					campo.setTipoId(dto.getIdTipo());
					fila.put(dto.getNombre(), campo);
				}
				datos.add(fila);
			}
		} catch (Exception ex) {
			datos = null;
			System.out.println(
					"ModuloBDConexion" + "|" + this.getClass().toString() + "|" + "obtieneDatos" + "|" + ex.toString());
		}
		return datos;
	}

	private String limpiaSQL(String sql) {
		return sql.replaceAll("\\{", "").replaceAll("call", "").replaceAll("\\}", "").replaceAll(",", "")
				.replaceAll("\\?", "").replaceAll("\\(", "").replaceAll("\\)", "");
	}

}
