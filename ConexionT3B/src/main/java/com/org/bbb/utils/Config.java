package com.org.bbb.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component("configIp")
@PropertySource(value = "file:ConexionBBB.properties")
public class Config {

	@Value("${host}")
	private String host;
	
	@Value("${port}")
	private String port;
	
	@Value("${pass}")
	private String pass;
	
	@Value("${user}")
	private String user;
	
	@Value("${name}")
	private String name;
	
	@Value("${jdni}")
	private String jdni;
	
	@Value("${surl}")
	private String surl;
	
	@Value("${driv}")
	private String driv;

	@Value("${port-s-b}")
	private String portSB;
	
	@Value("${host-s-b}")
	private String hostSB;
	
	@Value("${user-s-b}")
	private String userSB;
	
	@Value("${name-s-b}")
	private String nameSB;
	
	@Value("${pass-s-b}")
	private String passSB;
	
	public String getPassSB() {
		return passSB;
	}

	public void setPassSB(String passSB) {
		this.passSB = passSB;
	}

	public String getNameSB() {
		return nameSB;
	}

	public void setNameSB(String nameSB) {
		this.nameSB = nameSB;
	}

	@Value("${surl-s-b}")
	private String surlSB;
	
	@Value("${driv-s-b}")
	private String drivSB;

	@Override
	public String toString() {
		return "Config [host=" + host + ", port=" + port + ", pass=" + pass + ", user=" + user + ", name=" + name
				+ ", jdni=" + jdni + ", surl=" + surl + ", driv=" + driv + ", portSB=" + portSB + ", hostSB=" + hostSB
				+ ", userSB=" + userSB + ", surlSB=" + surlSB + ", drivSB=" + drivSB + "]";
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJdni() {
		return jdni;
	}

	public void setJdni(String jdni) {
		this.jdni = jdni;
	}

	public String getSurl() {
		return surl;
	}

	public void setSurl(String surl) {
		this.surl = surl;
	}

	public String getDriv() {
		return driv;
	}

	public void setDriv(String driv) {
		this.driv = driv;
	}

	public String getPortSB() {
		return portSB;
	}

	public void setPortSB(String portSB) {
		this.portSB = portSB;
	}

	public String getHostSB() {
		return hostSB;
	}

	public void setHostSB(String hostSB) {
		this.hostSB = hostSB;
	}

	public String getUserSB() {
		return userSB;
	}

	public void setUserSB(String userSB) {
		this.userSB = userSB;
	}

	public String getSurlSB() {
		return surlSB;
	}

	public void setSurlSB(String surlSB) {
		this.surlSB = surlSB;
	}

	public String getDrivSB() {
		return drivSB;
	}

	public void setDrivSB(String drivSB) {
		this.drivSB = drivSB;
	}

	

}
