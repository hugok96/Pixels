package textures;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import main.Pixels;

public class Textures {

	public static int grass, grass_top, dirt, rock, stone, obsidian, 
					  oak, oak_top, wood, tnt, tnt_top, tnt_bottom,
					  glass, notFound;
	
	private static HashMap<String, Integer> textures = new HashMap<String, Integer>();
	private static List<Integer> textureList = new ArrayList<Integer>();
	private static int textureMapSize;
	public static float textureSizeInMap;
	private final static int TEXTURE_SIZE = 16;
	private static int textureMapId;
	
	public static int getTextureMapId() {
		return textureMapId;
	}
	public static void initialize() {
		grass = getTextureId("grass");
		grass_top = getTextureId("grass_top");
		dirt = getTextureId("dirt");
		rock = getTextureId("rock");
		stone = getTextureId("stone");
		obsidian = getTextureId("obsidian");
		oak = getTextureId("oak");
		oak_top = getTextureId("oak_top");
		wood = getTextureId("wood");
		tnt = getTextureId("tnt");
		tnt_top = getTextureId("tnt_top");
		tnt_bottom = getTextureId("tnt_bottom");
		glass = getTextureId("glass");
		notFound = getTextureId("tex_not_found");
		Pixels.log("Texture count: " + textures.size(), 1);
	}
	
	public static void generateTextureMap() {
		textureMapSize = getTextureMapSize(textures.size());
		textureSizeInMap = (float) TEXTURE_SIZE / (float) textureMapSize;
		Pixels.log("Texturemap size: " + textureMapSize + "x" + textureMapSize, 1);
		BufferedImage textureMap = new BufferedImage(textureMapSize, textureMapSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = textureMap.createGraphics();
		for(String key:textures.keySet()) {
			BufferedImage block;
			try {
				block = ImageIO.read(new File("res/blocks/" + key + ".png"));				
			} catch (IOException e) {
				Pixels.err("Could not find/read " + key + ".png!", 2);
				try {
					block = ImageIO.read(new File("res/tex_not_found.png"));				
				} catch (IOException ex) {
					Pixels.err("Could not find/read tex_not_found.png!", 3);
					block = new BufferedImage(TEXTURE_SIZE, TEXTURE_SIZE, BufferedImage.TYPE_INT_RGB);
				}
			}
			
			int index = textures.get(key);
			int x = (index%(textureMapSize/TEXTURE_SIZE))*TEXTURE_SIZE;
			int y = (int) Math.floor(index/(textureMapSize/TEXTURE_SIZE))*TEXTURE_SIZE;
			g.drawImage(block, x, y, TEXTURE_SIZE, TEXTURE_SIZE, null);
		}
		
		try {
			ImageIO.write(textureMap, "png", new File("res/texturemap.png"));
		} catch (IOException e) {
			Pixels.err("COULD NOT SAVE TEXTUREMAP TO DISK!", 1);
			System.exit(-1);
		}
		textureMapId = loadTexture("texturemap");
	}
	
	private static int getTextureMapSize(int textureCount) {
		int ts = (int) Math.sqrt(textureCount * 256),
			i = TEXTURE_SIZE;		
		while (ts != i)
			if(ts <= i) 
				ts = i;
			 else 
				i = i + i;	
		return ts;
	}
	
	public static float[] getTexturePosition(int textureId) {
		float rowWidth = ((float)textureMapSize/(float)TEXTURE_SIZE);
		float x = (textureId%rowWidth)/rowWidth;
		float y = (float) (Math.floor(textureId/rowWidth)/rowWidth);
		return new float[]{
			x, 
			y
		};
		
	}
	
	public static int loadTexture(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4F);
		} catch (FileNotFoundException e) {
			Pixels.err("Could not locate "+fileName+".png", 1);
		} catch (IOException e) {
			Pixels.err("An error occured while reading "+fileName+".png", 1);
		}
		int textureID = texture.getTextureID();
		textureList.add(textureID);
		return textureID;
	}
	
	private static int getTextureId(String textureName) {
		if(!textures.containsKey(textureName)) 
			textures.put(textureName, textures.size());
		return textures.containsKey(textureName) ? textures.get(textureName) : textures.size();
	}
	
	public static void clear() {		
		for(int texture:textureList) {
			GL11.glDeleteTextures(texture);
		}
	}
}
