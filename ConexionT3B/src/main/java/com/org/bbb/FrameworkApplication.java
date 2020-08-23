package com.org.bbb;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.org.bbb.consulta.Consulta;
import com.org.bbb.dto.ColumnaDTO;
import com.org.bbb.dto.QryRespDTO;

@SpringBootApplication
public class FrameworkApplication implements ApplicationRunner {

	@Autowired
	@Qualifier("consulta")
	private Consulta consulta;

	public static void main(String[] args) {
		SpringApplication.run(FrameworkApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		System.out.println("Comenzando");
		ArrayList<Object> params = new ArrayList<>();
		params.add(1);
		QryRespDTO resp = consulta.ejecutaSelectSP("mysql", "{CALL SP_APP_CONSULTA_MODULOS_SISTEMA(?)}", params);

		if (resp.getRes() == 1) {
			ArrayList<ColumnaDTO> cols = resp.getColumnas();

			for (ColumnaDTO col : cols) {
				System.out.println("Col: " + col.toString());

			}
		}
	}
}