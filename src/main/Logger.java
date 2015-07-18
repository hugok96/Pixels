package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	
	public static final int LOGTYPE_LOG = 1;
	public static final int LOGTYPE_ERR = 2;
	private static boolean canLog = false;
	private static FileWriter log;
	private static FileWriter err;
	
	public static void initialize() {
		File logDir = new File(Pixels.LOG_DIRECTORY);
		File logFile = new File(Pixels.LOG_DIRECTORY + "log.log");
		File errFile = new File(Pixels.LOG_DIRECTORY + "err.log");
		boolean canLogL = false;
		if(!logDir.exists()) {
			if(!logDir.mkdir()) {
				canLogL = false;
				err("The Logger was unable to create its log folder!");
			} else {
				if(logDir.canWrite()) 
					canLogL = true;
			}
		} else {
			if(logDir.canWrite()) 
				canLogL = true;
			
		}
			
		if(canLogL) {
			try {
				log = logFile.exists() ? new FileWriter(logFile, true) : new FileWriter(logFile);
			} catch (IOException e) {
				canLog = false;
				err("The Logger was unable to create/open the log file");
			}
			
			try {
				err = errFile.exists() ? new FileWriter(errFile, true) : new FileWriter(errFile);
				canLog = true;
			} catch (IOException e) {
				canLog = false;
				err("The Logger was unable to create/open the errorlog file");
			}
			
			if(canLog) {
				log("", 0 ,LOGTYPE_LOG, true, false);
				log("LOGGER STARTED!", 0 ,LOGTYPE_LOG, true, false);
				log("LOGFILE: LOG.LOG", 1 ,LOGTYPE_LOG, true, false);
				log("Successfully started the Logger!", 0 ,LOGTYPE_LOG, false, true);
				log("", 0 ,LOGTYPE_LOG, true, false);
				
				log("", 0 ,LOGTYPE_ERR, true, false);
				log("LOGGER STARTED!", 0 ,LOGTYPE_ERR, true, false);
				log("LOGFILE: ERR.LOG", 1 ,LOGTYPE_ERR, true, false);
				log("", 0 ,LOGTYPE_ERR, true, false);
			}
		}
	}
	
	public static void clear() {
		if(log != null)
			try {
				log.close();
			} catch (IOException e) {
				err("The Logger was unable to close the log file");
			}
		
		if(err != null)
			try {
				err.close();
			} catch (IOException e) {
				err("The Logger was unable to close the errorlog file");
			}
	}
	
	public static void log() {
		log(new String(), 0, LOGTYPE_LOG, true, true);
	}
	
	public static void err() {
		log(new String(), 0, LOGTYPE_ERR, true, true);
	}
	
	public static void log(Object message) {
		log(message, 0, LOGTYPE_LOG, true, true);
	}
	
	public static void log(Object message, int level) {
		log(message, level, LOGTYPE_LOG, true, true);
	}
	
	public static void err(Object message) {
		log(message, 0, LOGTYPE_ERR, true, true);
	}
	
	public static void err(Object message, int level) {
		log(message, level, LOGTYPE_ERR, true, true);
	}
	private synchronized static void log(Object message, int level, int logType, boolean toFile, boolean toLog) {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String offset = level > 0 ? new String(new char[level + ((level-1)*2)]).replace("\0"," ") + "- " : "";
		
		if(toLog) {
			switch(logType) {
				case LOGTYPE_LOG:
					System.out.print("Pixels.log [" + date + "] " + offset);
					System.out.println(message);
					break;
				case LOGTYPE_ERR:
					System.err.print("Pixels.err [" + date + "] " + offset);
					System.err.println(message);
					break;
			}
		}
		
		
		
		if(canLog && toFile) {
			FileWriter logger = logType == Logger.LOGTYPE_LOG ? log : err;
			try {
				logger.write("[" + date + "] " + offset);
				if(message instanceof String) {
					logger.write(message.toString());
				} else if(message instanceof Integer) {
					logger.write((Integer) message);
				} else if(message instanceof char[]){
					logger.write((char[]) message);
				} else {
					logger.write(message.toString());
				}
				logger.write(System.lineSeparator());
			} catch (IOException e) {
				if(!toFile) {
					e.printStackTrace();
				} else {
					log("The logger was unable to write to its log files!", 0 ,LOGTYPE_ERR, false, true);
					canLog = false;
				}
			} finally {
				try {
					logger.flush();
				} catch (IOException e) {
					if(!toFile) {
						e.printStackTrace();
					} else {
						log("The logger was unable to flush to its log files!", 0 ,LOGTYPE_ERR, false, true);
						canLog = false;
					}
				}
			}
		}
	}
}
