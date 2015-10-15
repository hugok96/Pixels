package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Timer;

import models.ModelUtils;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import FileSystem.FileSystem;
import FileSystem.Files;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;
import textures.Textures;
import world.World;
import blocks.Blocks;
import entities.EntityCamera;
import entities.EntityLight;

public class Pixels {	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logger.initialize();
		
		Logger.log("Good morning!");
		Logger.log("Let's get this thing going eh!");

		Logger.log("Creating display...");
		DisplayManager.createDisplay();
		Logger.log("Display created");
		
		Logger.log("Creating cache...");
		FileSystem.CreateCache();
		Logger.log("Cache created!");
		
		Logger.log("Initializing textures...");
		Textures.initialize();
		Logger.log("Textures initialized!");
		
		Logger.log("Generating texturemap...");
		Textures.generateTextureMap();
		Logger.log("Texturemap generated!");
		
		Logger.log("Initializing blocks...");
		Blocks.initialize();
		Logger.log("Blocks initialized!");
		
		Logger.log("Creating World...");
		if(new File(Files.WORLD_TEST_WORLD).exists()) {
			Logger.log("Savegame found!", 1);
			Logger.log("Loading savegame...", 2);
			if(World.load("test")) {
				Logger.log("Savegame successfully loaded!", 2);
			} else {
				Logger.err("Failed to load savegame!", 2);
				Logger.err("Generating new one instead...", 2);
				World.generate();
			}
		} else {
			World.generate();
		}
		Logger.log("World created!");

		Logger.log("Saving World...");
		World.save();
		Logger.log("World saved!");
		
		Logger.log();		
		Logger.log("Starting game!");
		
		
		EntityLight light = new EntityLight(new Vector3f(0,150F,0F), new Vector3f(1,1,1));
		EntityCamera camera = new EntityCamera();
		MasterRenderer renderer = new MasterRenderer();
		
		Timer timer = new Timer(15, new ActionListener() {
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
