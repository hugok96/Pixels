package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import blocks.Block;
import blocks.Blocks;
import entities.Entity;
import entities.EntityCamera;
import entities.EntityLight;
import models.ModelUtils;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;
import textures.Textures;
import world.Coord2d;
import world.Coord3d;
import world.World;

public class Pixels {
	public static World world;
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
		world = new World();
		Logger.log("World created!");
		Logger.log();
		Logger.log("Starting game!");
		
		
		
		EntityLight light = new EntityLight(new Vector3f(0,50F,0F), new Vector3f(1,1,1));
		
		EntityCamera camera = new EntityCamera();
		
		MasterRenderer renderer = new MasterRenderer();
		
		List<Entity> cubes = new ArrayList<Entity>();
		Map<Coord3d, Block> blocks = world.getRenderableBlocks();
		for(Coord3d c:blocks.keySet())
			cubes.add(new Entity(blocks.get(c).getModel(), c.toVector(), 0, 0, 0, 0.5F));
		
		//List<Entity> cubes = new ArrayList<Entity>();
		//for(int i = 0; i < 64; i++) {
		//	cubes.add(new Entity(i%3 == 0 ? Blocks.dirt.getModel() : i%3 == 1 ? Blocks.grass.getModel() : Blocks.rock.getModel(), new Vector3f((i%8)-4, -2F, (float)Math.floor(i/8)-4), 0, 0, 0, 0.5F));
		//}
//		try {
//			// get the BufferedImage, using the ImageIO class
//			BufferedImage image = 
//	        ImageIO.read(new File("res/heightmap.png"));
//			int w = image.getWidth();
//		    int h = image.getHeight();
//		    System.out.println("width, height: " + w + ", " + h);
//
//		    for (int i = 0; i < h; i++) {
//		      for (int j = 0; j < w; j++) {
//		        int pixel = image.getRGB(j, i);
//			    int red = (pixel >> 16) & 0xff;
//			    int green = (pixel >> 8) & 0xff;
//			    int blue = (pixel) & 0xff;
//			    if(!(red == 255 & green == 0 && blue == 255)) {
//			    	int pHeight = (int) Math.ceil((red)/8);
//			    	cubes.add(new Entity(Blocks.grass.getModel(), new Vector3f(j-(w/2), (float) pHeight, i-(h/2)), 0, 0, 0, 0.5F));
//			    	for(int g = 0; g < pHeight; g++)
//				    	cubes.add(new Entity(g >= 2 ? Blocks.rock.getModel() : Blocks.dirt.getModel(), new Vector3f(j-(w/2), (float) pHeight-g-1, i-(h/2)), 0, 0, 0, 0.5F));
//			    		
//			    }
//		      }
//		    }
//	    } catch (IOException e) {
//	      System.err.println(e.getMessage());
//	    }
		Timer timer = new Timer(25, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				camera.move();
			}
		});
		timer.start();
		while(!Display.isCloseRequested()) {
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
}
