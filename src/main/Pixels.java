package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	public static final String LOG_DIRECTORY = "logs/";
	public static final String RES_DIRECTORY = "res/";
	public static final String BLOCK_DIRECTORY = RES_DIRECTORY + "blocks/";
	public static final String OBJ_DIRECTORY = RES_DIRECTORY + "obj/";
	public static final String TEXTURE_FILE_NOT_FOUND = RES_DIRECTORY + "tex_not_found.png";
	public static final String TEXTUREMAP_NAME = "texturemap";
	public static final String TEXTUREMAP_FILE = RES_DIRECTORY + TEXTUREMAP_NAME + ".png";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logger.initialize();
		
		Logger.log("Good morning!");
		Logger.log("Let's get this thing going eh!");
		
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
		
		
		
		EntityLight light = new EntityLight(new Vector3f(0,50F,0F), new Vector3f(1,1,1));
		
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
