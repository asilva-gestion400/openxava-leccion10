package com.ayto.multas.acciones;

import javax.servlet.http.*;

import org.openxava.actions.*;
import org.openxava.util.*;

import com.ayto.multas.as400.*;

public class VisualizarCertificadoEmpadronamiento extends ViewBaseAction implements IRequestAction , IForwardAction {
	
	private HttpServletRequest request;

	private String urlServlet;
	
	private String rutaAS400;
	
	private String nombreDocumento;
	
	@Override
	public void execute() throws Exception {

		String nif = ""; // Recuperar del infractor
		
		nombreDocumento = nif + ".pdf";

		//String rutaAS400 = ...
		
		if(!Is.empty(rutaAS400)) {
			
			request.getSession().setAttribute(VisualizarDocumentoAS400.KEY_PARAMETRO_URL, rutaAS400);
			
			request.getSession().setAttribute(VisualizarDocumentoAS400.KEY_PARAMETRO_NOMBRE_DOCUMENTO, nombreDocumento);
			
			urlServlet = "/Multas" + VisualizarDocumentoAS400.URL_SERVLET;
		}
	}
	
	public String getForwardURI() {
		
		if(!Is.empty(urlServlet)) return "javascript:void(window.open('" + urlServlet + "'))";
		
		return null;
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public boolean inNewWindow() {
		return false;
	}
}
