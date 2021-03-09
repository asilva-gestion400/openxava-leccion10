package com.ayto.multas.as400;

import java.math.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;

import org.openxava.util.*;

import com.ibm.as400.access.*;

public class ProgramaAS400 {
	
	private static final Logger log = Logger.getLogger(ProgramaAS400.class.getCanonicalName());
	
	private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(5); ;
		
	private AS400 as400;
	
	private String rutaPrograma;

	private int sizeParametros;
	
	private ProgramParameter[] parametros;
	
	private ProgramCall programa;
	
	private Semaphore mutex;
	
	public ProgramaAS400(ConfiguracionAS400 configuracion,String rutaPrograma,int numeroParametros,int sizeParametros) throws Exception{
		
		this(configuracion,rutaPrograma,numeroParametros,sizeParametros,null);
	}
	
	public ProgramaAS400(ConfiguracionAS400 configuracion,String rutaPrograma,int numeroParametros,int sizeParametros,List<String> libreriasAdicionales) throws Exception{
		
		this.as400 = new AS400(
				configuracion.getIp(),
				configuracion.getUser(),
				configuracion.getPassword());
		
		addLibreriasAdicionales(libreriasAdicionales);
		
		this.rutaPrograma = rutaPrograma;
		
		this.sizeParametros = sizeParametros;
		
		parametros = new ProgramParameter[numeroParametros];
		
		for(int i = 0; i < numeroParametros; i++) parametros[i] = new ProgramParameter(sizeParametros);
	}
	
	private void addLibreriasAdicionales(List<String> librerias) throws Exception{

		if (librerias != null && !librerias.isEmpty()) {
			CommandCall cc = new CommandCall(as400);
			for(String libreria: librerias) {
				cc.run("ADDLIBLE " + libreria);
			}
		}		
	}
	
	private static final int SEMAFORO_MUTEX = 0;
	
	private static final String ENDJOB = "ENDJOB JOB(%s/%s/%s)  OPTION(*CNTRLD) DELAY(10)  SPLFILE(*NO)";
	
	private static final String IDENTIFICADOR_PROCESO = "EJECUTANDO PROGRAMA %s JOB(%s/%s/%s)";
	
	public void ejecutar(ConfiguracionAS400 configuracion, int secondsTimeOut) throws LlamadaProgramaAS400Exception, TimeoutException {
		
		String comandoFinalizarEjecucion = "";
		
		boolean error = false;
		
		try{
			
			mutex = new Semaphore(SEMAFORO_MUTEX);
			
			programa = new ProgramCall(as400, rutaPrograma, parametros);
			
			comandoFinalizarEjecucion = String.format(ENDJOB,
					programa.getServerJob().getNumber(),
					programa.getServerJob().getUser(),
					programa.getServerJob().getName());
			
			log.info(String.format(IDENTIFICADOR_PROCESO,
					rutaPrograma,
					programa.getServerJob().getNumber(),
					programa.getServerJob().getUser(),
					programa.getServerJob().getName()));
			
			HiloProgramaAS400 hilo =  new HiloProgramaAS400();

			Future<Boolean> ejecutado = EXECUTOR.submit(hilo);
			
			error = !mutex.tryAcquire(secondsTimeOut,TimeUnit.SECONDS);

			if(error){
				
				ejecutado.cancel(true);
			
				throw new TimeoutException();
			}
			
			if(!ejecutado.get()) throw new LlamadaProgramaAS400Exception("No se ha podido ejecutar el programa RPG");
			
		}catch(Exception ex){
			
			throw new LlamadaProgramaAS400Exception(ex);
			
		}finally{
			
			if(as400 != null){
				
				as400.disconnectAllServices();

				if(error && !Is.empty(comandoFinalizarEjecucion)) {
					
					cancelarTrabajo(configuracion,comandoFinalizarEjecucion);
				}
			}
		}
	}
	
	private void cancelarTrabajo(ConfiguracionAS400 configuracion,String comandoFinalizarEjecucion) throws LlamadaProgramaAS400Exception {
		
		AS400 as400CancelJob = new AS400(
				configuracion.getIp(),
				configuracion.getUser(),
				configuracion.getPassword());
		
		try{
			
			CommandCall cc = new CommandCall(as400CancelJob);

			cc.run(comandoFinalizarEjecucion);
			
		}catch(Exception ex){
			
			throw new LlamadaProgramaAS400Exception("Problema al intentar cancelar el trabajo",ex);
			
		} finally{
			as400CancelJob.disconnectService(AS400.COMMAND);
		}
	}
	
	private class HiloProgramaAS400 implements Callable<Boolean> {

		@Override
		public Boolean call() throws Exception {
			
			try{
				return programa.run();
				
			}finally{
				
				mutex.release();
			}
		}
	}
	
	public void setParametrosString(int posicion,String valor,int longitud) {
		
		setParametrosString(posicion,valor,longitud,Align.LEFT);
	}
	
	public void setParametrosString(int posicion,String valor,int longitud,Align align) {
		
		valor = Strings.fix(valor,longitud, align);
		
		parametros[posicion] = new ProgramParameter(new AS400Text(sizeParametros).toBytes(valor),longitud);
	}

	public void setParametrosZoned(int posicion,double valor,int longitud, int parteDecimal) {
		
		BigDecimal valorBigDecimal = BigDecimal.valueOf(valor).setScale(parteDecimal, BigDecimal.ROUND_HALF_UP);
		
		parametros[posicion] = new ProgramParameter(new AS400ZonedDecimal(longitud,parteDecimal).toBytes(valorBigDecimal),longitud);
	}
	
	public void setParametrosPacked(int posicion,double valor,int longitud, int parteDecimal) {
		
		BigDecimal valorBigDecimal = BigDecimal.valueOf(valor).setScale(parteDecimal, BigDecimal.ROUND_HALF_UP);
		
		parametros[posicion] = new ProgramParameter(new AS400PackedDecimal(longitud,parteDecimal).toBytes(valorBigDecimal),longitud);
	}
	
	public String getParametrosString(int posicion,int longitud){
		
		byte [] data = programa.getParameterList()[posicion].getOutputData();
		
		String parametro = (String) new AS400Text(sizeParametros).toObject(data); 
		
		return tieneValor(data,longitud) ? parametro.trim() : parametro;
	}
	
	public BigDecimal getParametrosZoned(int posicion,int longitud, int parteDecimal){
		
		byte [] data = programa.getParameterList()[posicion].getOutputData();
		
		AS400ZonedDecimal parametro = new AS400ZonedDecimal(longitud,parteDecimal); 
		
		return tieneValor(data,longitud) ? ((BigDecimal)parametro.toObject(data)).setScale(parteDecimal,BigDecimal.ROUND_HALF_UP) : null;
	}
	
	public BigDecimal getParametrosPacked(int posicion,int longitud, int parteDecimal){
		
		byte [] data = programa.getParameterList()[posicion].getOutputData();
		
		AS400PackedDecimal parametro = new AS400PackedDecimal(longitud,parteDecimal); 
		
		return tieneValor(data,longitud) ? ((BigDecimal)parametro.toObject(data)).setScale(parteDecimal,BigDecimal.ROUND_HALF_UP) : null;
	}
	
	public boolean tieneValor(byte[] data,int longitud) {
		
		return data != null && data.length > 0 && data.length <= longitud;
	}	
}
