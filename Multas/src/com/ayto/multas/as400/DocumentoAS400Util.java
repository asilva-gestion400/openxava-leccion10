package com.ayto.multas.as400;

import java.io.*;

import com.ibm.as400.access.*;

public class DocumentoAS400Util {
	
	private AS400 as400;
	
	public DocumentoAS400Util(ConfiguracionAS400 configuracion){
		
		as400 = new AS400(
				configuracion.getIp(),
				configuracion.getUser(),
				configuracion.getPassword());
	}
	
	public DocumentoAS400Util(AS400 as400){
		
		this.as400 = as400;
	}
	
	public byte[] getDocumento(String urlDocumento) throws Exception {
		
		try(IFSFileInputStream input400 = new IFSFileInputStream(as400, urlDocumento)) {
			
			urlDocumento = urlDocumento.trim();
	
			byte[] documento = new byte[input400.available()];
			
			input400.read(documento);

			return documento;
			
		} finally{
			
			if(as400 != null) as400.disconnectService(AS400.FILE);
		}
	
	}

	public void setDocumento(String urlDocumento, byte[] documento) throws Exception {
		
		try(IFSFileOutputStream output400 = new IFSFileOutputStream(as400, urlDocumento);) {
			
			urlDocumento = urlDocumento.trim();

			output400.write(documento);
		
		} finally{
			
			if(as400 != null) as400.disconnectService(AS400.FILE);
		}
	}
	
	public boolean removeDocumento(String urlDocumento) throws IOException {
		
		try {
			
			IFSFile ifsFile = new IFSFile(as400, urlDocumento);
			
			return ifsFile.delete();
			
		} finally{
			
			if(as400 != null) as400.disconnectService(AS400.FILE);
		}
	}
	
	public boolean existeDocumento(String urlDocumento) {
		
		try {
			
			IFSFile ifsFile = new IFSFile(as400, urlDocumento);
			
			return ifsFile.exists();
			
		} catch (IOException e) {
			
			return false;
			
		} finally{
			
			if(as400 != null) as400.disconnectService(AS400.FILE);
		}
	}
}
