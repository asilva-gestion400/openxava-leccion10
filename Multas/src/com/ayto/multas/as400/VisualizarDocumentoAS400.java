package com.ayto.multas.as400;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.lang3.*;

public class VisualizarDocumentoAS400 extends HttpServlet {

	public static final String URL_SERVLET = "/visualizarDocumentoAS400";

	public static final String KEY_PARAMETRO_URL = "visualizarDocumentoAS400_url";
	
	public static final String KEY_PARAMETRO_NOMBRE_DOCUMENTO = "visualizarDocumentoAS400_nombreDocumento";

	private String url;
	
	private String nombreDocumento;

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			getParametros(request);

			ConfiguracionAS400 configuracion = new ConfiguracionAS400();
			
			DocumentoAS400Util utilDocumentosAs400 = new DocumentoAS400Util(configuracion);
			
			byte[] documentosEnByte = utilDocumentosAs400.getDocumento(url);

			response.setContentType("application/pdf");
			
			nombreDocumento = StringUtils.stripAccents(nombreDocumento).replace("  ", " ").replace(" ", "_").replace("-", "_").replace(".", "").replace(",", "") + ".pdf";

			response.setHeader("Content-Disposition", "attachment; filename=" + nombreDocumento);
			
			response.getOutputStream().write(documentosEnByte);
			response.getOutputStream().flush();
			response.getOutputStream().close();

		} catch (Exception ex) {
			
			throw new ServletException(ex);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

	private void getParametros(HttpServletRequest request) {

		url = (String) request.getSession().getAttribute(KEY_PARAMETRO_URL);
		
		nombreDocumento = (String) request.getSession().getAttribute(KEY_PARAMETRO_NOMBRE_DOCUMENTO);

		request.getSession().setAttribute(KEY_PARAMETRO_URL, null);
		
		request.getSession().setAttribute(KEY_PARAMETRO_NOMBRE_DOCUMENTO, null);
	}
}