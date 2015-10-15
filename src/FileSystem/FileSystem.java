package FileSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

import main.Logger;
import main.Pixels;

public class FileSystem {
	private static FileReader _freader;
	private static BufferedReader _reader;
	private static Stream<String> _lines;

	private static FileWriter fwriter;
	
	public static void CreateCache() {
		for(String path : Directories.GetCacheableDirectories()) {
			Logger.log("Setting up some folders..");
			if(!new File(path).exists()) {
				Logger.log("\"" + path + "\" not found!", 1);
				if(new File(path).mkdirs())
					Logger.log("\"" + path + "\" created!", 2);
				else
					Logger.err("Couldn't create \"" + path + "\"", 2);
			}			
		}
		Logger.log("Folders set up!");
	}
	
	public static String ReadFile(String path) {
		return ReadFile(path, 2);
	}
	
	public static String ReadFile(String path, int depth) {
		String result = "";
		if(new File(path).exists()) {
			try {
				_freader = new FileReader(path);
				_reader = new BufferedReader(_freader);
				_lines = _reader.lines();
				String fileStrings = "";
				for(Object s:_lines.toArray())
					fileStrings += (String)s;
				result += fileStrings;
				try {
					_lines.close();
					_freader.close();
					_reader.close();
				} catch (IOException e2) {
					Logger.err("Couldn't close readers!", depth);
					return result;
				}
			} catch (IOException e1) {
				Logger.err("An unknown error occured while reading \"" + path + "\"!", depth);
				return null;
			}
		} else {
			result = null;
		}
		
		return result;
	}
	

	public static boolean WriteFile(File file, String data, String friendlyName) {
		return WriteFile(file, data, friendlyName, 2);
	}
	
	public static boolean WriteFile(File path, String data, String friendlyName, int depth) {
		try {
			fwriter = new FileWriter(path);
			fwriter.write(data);
			Logger.log("Successfully written " + friendlyName + "to file!", 3);
			return true;
		} catch (IOException e) {
			Logger.err("Could not read/write " + friendlyName + " to file!", 4);
			return false;
		} finally {
			if(fwriter != null) {
				try {
					fwriter.close();
					return true;
				} catch (IOException e) {
					Logger.err("Could not close writer!", 4);
					return false;
				}
			}
		}
	}
	
	public static File CreateNewFile(String path, String friendlyName, boolean deleteOld) {
		File file = new File(path);
		if(deleteOld && file.exists()) {
			Logger.log("Old file \"" + friendlyName + "\" found!", 3);
			if(!file.delete())
				Logger.err("Couldn't delete old file \"" + friendlyName + "\"!", 3);
			else
				Logger.log("Old file \"" + friendlyName + "\" deleted!", 3);
		} 
		try {
			if(file.createNewFile()) {
				Logger.log("File \"" + friendlyName + "\" created!", 3);
				if(file.canWrite())
					return file;
			}
			return null;
		} catch (IOException e) {
			Logger.err("Could not create file \"" + friendlyName + "\"!", 3);
			return null;
		}
	}
	
	public static String GetHash(String input)
    {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.reset();
	        byte[] buffer = input.getBytes("UTF-8");
	        md.update(buffer);
	        byte[] digest = md.digest();

	        String hexStr = "";
	        for (int i = 0; i < digest.length; i++) {
	            hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
	        }
	        return hexStr;
		} catch (Exception e) {
			return Integer.toHexString(input.hashCode());
		}
    }
}
