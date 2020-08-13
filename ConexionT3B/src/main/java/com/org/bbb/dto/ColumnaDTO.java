package com.org.bbb.dto;

public class ColumnaDTO {

	public String nombre = "";
	public String etiqueta = "";
	public int longitud = 0;
	public int idTipo = 0;
	public String tipo = "";
	public String clase = "";

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public int getLongitud() {
		return longitud;
	}

	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}

	public int getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	@Override
	public String toString() {
		return "ColumnaDTO [nombre=" + nombre + ", etiqueta=" + etiqueta + ", longitud=" + longitud + ", idTipo="
				+ idTipo + ", tipo=" + tipo + ", clase=" + clase + "]";
	}

}
