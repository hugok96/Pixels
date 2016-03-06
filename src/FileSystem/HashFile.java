package FileSystem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import main.Logger;

public class HashFile {
	private HashMap<Integer, String[]> _entries;
	
	public HashFile() {
		_entries = new HashMap<Integer, String[]>();
	}
	
	public HashFile AddEntry(int entryId, String[] data) {
		if(!_entries.containsKey(entryId))
			_entries.put(entryId, data);
		return this;
	}
	
	public HashFile ModifyEntry(int entryId, String[] data) {
		if(!_entries.containsKey(entryId))
			_entries.replace(entryId, data);
		else
			AddEntry(entryId, data);
		return this;
	}
	
	public HashFile RemoveEntry(int entryId, String[] data) {
		if(!_entries.containsKey(entryId))
			_entries.remove(entryId);
		return this;
	}
	
	public String[] GetEntry(int key) {
		return _entries.containsKey(key) ? _entries.get(key) : null;
	}
	
	public HashMap<Integer, String[]> GetEntries() {
		return _entries;
	}
	
	public int Size() {
		return _entries != null ? _entries.size() : 0;
	}
	
	public String Export() {
		String result = "";
		for(Entry<Integer, String[]> entry : _entries.entrySet()) {
			result += entry.getKey();
			for(String s : entry.getValue())
				result += "," + s;
			result += ";";
		}
		return result;
	}
	
	public static HashFile Import(String data) {
		HashFile hashFile = new HashFile();
		if(data.trim().isEmpty()) 
			return hashFile;
		
		for(String entry : data.split(";")) {
			String[] entryData = entry.split(",");
			try {
				hashFile.AddEntry(Integer.parseInt(entryData[0]), Arrays.copyOfRange(entryData, 1, entryData.length));
			} catch(Exception e) {
				Logger.err("An invalid hash entry was found, the hash file might be flawed.", 4);
			}
		}
		return hashFile;
	}
}
