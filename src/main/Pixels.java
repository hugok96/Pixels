package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Timer;

import models.ModelUtils;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;
import textures.Textures;
import blocks.Blocks;
import entities.Entity;
import entities.EntityCamera;
import entities.EntityLight;

public class Pixels {

	public static final int LOGTYPE_LOG = 1;
	public static final int LOGTYPE_ERR = 2;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		log("Good morning!");
		log("Let's get this thing going eh!");
		
		log("Creating display..");
		DisplayManager.createDisplay();
		log("Display created");
		
		log("Initializing textures..");
		Textures.initialize();
		log("Textures initialized!");
		
		log("Generating texturemap..");
		Textures.generateTextureMap();
		log("Texturemap generated!");
		
		log("Initializing blocks..");
		Blocks.initialize();
		log("Blocks initialized!");
		
		
		
		EntityLight light = new EntityLight(new Vector3f(0,50F,0F), new Vector3f(1,1,1));
		
		EntityCamera camera = new EntityCamera();
		
		MasterRenderer renderer = new MasterRenderer();
		
		List<Entity> cubes = new ArrayList<Entity>();
		for(int i = 0; i < 64; i++) {
			cubes.add(new Entity(i%3 == 0 ? Blocks.dirt.getModel() : i%3 == 1 ? Blocks.grass.getModel() : Blocks.rock.getModel(), new Vector3f((i%8)-4, -2F, (float)Math.floor(i/8)-4), 0, 0, 0, 0.5F));
		}
		Timer timer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				camera.move();
			}
		});
		timer.start();
		while(!Display.isCloseRequested()) {
			//game logic
			//entity.increaseRotation(0, 1F, 0);
			//render
			for(Entity cube:cubes) {
				renderer.processEntity(cube);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		timer.stop();
		ModelUtils.clear();
		Textures.clear();/*
		//ModelLoader loader = new ModelLoader();	
		//ModelData data = OBJFileLoader.loadOBJ("cube");
		
		RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		ModelTexture textureDirt = new ModelTexture(loader.loadTexture("dirt"));
		TexturedModel modelDirt = new TexturedModel(model, textureDirt);
		ModelTexture textureGrass = new ModelTexture(loader.loadTexture("grass"));
		TexturedModel modelGrass = new TexturedModel(model, textureGrass);
		ModelTexture textureRock = new ModelTexture(loader.loadTexture("rock"));
		TexturedModel modelRock = new TexturedModel(model, textureRock);

		List<Entity> cubes = new ArrayList<Entity>();
//		for(int i = 0; i < 64; i++) {
//			cubes.add(new Entity(texturedModel, new Vector3f((i%8)-4, -2F, (float)Math.floor(i/8)-4), 0, 0, 0, 0.5F));
//		}
		
		try {
			// get the BufferedImage, using the ImageIO class
			BufferedImage image = 
	        ImageIO.read(new File("res/heightmap.png"));
			int w = image.getWidth();
		    int h = image.getHeight();
		    System.out.println("width, height: " + w + ", " + h);

		    for (int i = 0; i < h; i++) {
		      for (int j = 0; j < w; j++) {
		        int pixel = image.getRGB(j, i);
			    int red = (pixel >> 16) & 0xff;
			    int green = (pixel >> 8) & 0xff;
			    int blue = (pixel) & 0xff;
			    if(!(red == 255 & green == 0 && blue == 255)) {
			    	int pHeight = (int) Math.ceil((red)/8);
			    	cubes.add(new Entity(modelGrass, new Vector3f(j-(w/2), (float) pHeight, i-(h/2)), 0, 0, 0, 0.5F));
			    	for(int g = 0; g < pHeight; g++)
				    	cubes.add(new Entity(g >= 2 ? modelRock : modelDirt, new Vector3f(j-(w/2), (float) pHeight-g-1, i-(h/2)), 0, 0, 0, 0.5F));
			    		
			    }
		      }
		    }
	    } catch (IOException e) {
	      System.err.println(e.getMessage());
	    }
		
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
			//game logic
			//entity.increaseRotation(0, 1F, 0);
			//render
			for(Entity cube:cubes) {
				renderer.processEntity(cube);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		timer.stop();
		renderer.clear();
		loader.clear();
		DisplayManager.closeDisplay();
		*/
	}
	

	public static void log() {
		log(new String(), 0, LOGTYPE_LOG);
	}
	
	public static void err() {
		log(new String(), 0, LOGTYPE_ERR);
	}
	
	public static void log(Object message) {
		log(message, 0, LOGTYPE_LOG);
	}
	
	public static void log(Object message, int level) {
		log(message, level, LOGTYPE_LOG);
	}
	
	public static void err(Object message) {
		log(message, 0, LOGTYPE_ERR);
	}
	
	public static void err(Object message, int level) {
		log(message, level, LOGTYPE_ERR);
	}
	private static void log(Object message, int level, int logType) {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String offset = level > 0 ? new String(new char[level + ((level-1)*2)]).replace("\0"," ") + "- " : "";
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
}
