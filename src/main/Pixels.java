package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Timer;

import models.ModelUtils;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;
import textures.Textures;
import world.World;
import blocks.Blocks;
import entities.EntityCamera;
import entities.EntityLight;

public class Pixels {
	public static final String RES_DIRECTORY = "res/";
	public static final String LOG_DIRECTORY = "logs/";
	public static final String CACHE_DIRECTORY = "cache/";
	public static final String BLOCK_DIRECTORY = RES_DIRECTORY + "blocks/";
	public static final String HASHES_DIRECTORY = CACHE_DIRECTORY + "hashes/";
	public static final String OBJ_DIRECTORY = RES_DIRECTORY + "obj/";
	public static final String TEXTURE_FILE_NOT_FOUND = RES_DIRECTORY + "tex_not_found.png";
	public static final String TEXTUREMAP_FILE = CACHE_DIRECTORY + "texturemap.png";
	public static final String HASHES_BLOCKS_FILE = HASHES_DIRECTORY + "blocks.phash";
	public static final String LOG_LOG_FILE =  LOG_DIRECTORY + "log.log";
	public static final String LOG_ERR_FILE = LOG_DIRECTORY + "err.log";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logger.initialize();
		
		Logger.log("Good morning!");
		Logger.log("Let's get this thing going eh!");
		
		Logger.log("Setting up some folders..");
		if(!new File(Pixels.RES_DIRECTORY).exists()) {
			Logger.log("Resource directory not found!", 1);
			if(new File(Pixels.RES_DIRECTORY).mkdirs())
				Logger.log("Resource directory created!", 2);
			else
				Logger.err("Couldn't create resource directory", 2);
		}
		if(!new File(Pixels.LOG_DIRECTORY).exists()) {
			Logger.log("Logs directory not found!", 1);
			if(new File(Pixels.LOG_DIRECTORY).mkdirs())
				Logger.log("Logs directory created!", 2);
			else
				Logger.err("Couldn't create logs directory", 2);
		}
		if(!new File(Pixels.CACHE_DIRECTORY).exists()) {
			Logger.log("Cache directory not found!", 1);
			if(new File(Pixels.CACHE_DIRECTORY).mkdirs())
				Logger.log("Cache directory created!", 2);
			else
				Logger.err("Couldn't create cache directory", 2);
		}
		if(!new File(Pixels.BLOCK_DIRECTORY).exists()) {
			Logger.log("Blocks directory not found!", 1);
			if(new File(Pixels.BLOCK_DIRECTORY).mkdirs())
				Logger.log("Blocks directory created!", 2);
			else
				Logger.err("Couldn't create blocks directory", 2);
		}
		if(!new File(Pixels.HASHES_DIRECTORY).exists()) {
			Logger.log("Hashes directory not found!", 1);
			if(new File(Pixels.HASHES_DIRECTORY).mkdirs())
				Logger.log("Hashes directory created!", 2);
			else
				Logger.err("Couldn't create hashes directory", 2);
		}
		if(!new File(Pixels.OBJ_DIRECTORY).exists()) {
			Logger.log("OBJ directory not found!", 1);
			if(new File(Pixels.OBJ_DIRECTORY).mkdirs())
				Logger.log("OBJ directory created!", 2);
			else
				Logger.err("Couldn't create OBJ directory", 2);
		}
		Logger.log("Folders set up!");
		
		Logger.log("Creating display..");
		DisplayManager.createDisplay();
		Logger.log("Display created");
		
		Logger.log("Initializing textures..");
		Textures.initialize();
		Logger.log("Textures initialized!");
		
		Logger.log("Generating texturemap..");
		Textures.generateTextureMap();
		Logger.log("Texturemap generated!");
		
		Logger.log("Initializing blocks..");
		Blocks.initialize();
		Logger.log("Blocks initialized!");
		
		Logger.log("Creating World..");
		World.generate();
		Logger.log("World created!");
		Logger.log();
		Logger.log("Starting game!");
		
		
		
		EntityLight light = new EntityLight(new Vector3f(0,150F,0F), new Vector3f(1,1,1));
		
		EntityCamera camera = new EntityCamera();
		
		MasterRenderer renderer = new MasterRenderer();
		
		Timer timer = new Timer(25, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				camera.move();
			}
		});
		timer.start();
		while(!Display.isCloseRequested()) {			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		timer.stop();
		ModelUtils.clear();
		Textures.clear();		
	}
}
