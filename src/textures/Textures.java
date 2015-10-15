package textures;

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

import main.Logger;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import FileSystem.Directories;
import FileSystem.Extensions;
import FileSystem.FileSystem;
import FileSystem.Files;
import FileSystem.HashFile;

public class Textures {

	public static int grass, grass_top, dirt, rock, stone, obsidian, 
					  oak, oak_top, wood, tnt, tnt_top, tnt_bottom,
					  glass, notFound, egg;
	
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
		Logger.log("Texture count: " + textures.size(), 1);
	}
	
	public static void generateTextureMap() {
		textureMapSize = getTextureMapSize(textures.size());
		textureSizeInMap = (float) TEXTURE_SIZE / (float) textureMapSize;
		Logger.log("Texturemap size: " + textureMapSize + "x" + textureMapSize, 1);
		
		if(checkTexturesChanged()) {
			BufferedImage textureMap = new BufferedImage(textureMapSize, textureMapSize, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = textureMap.createGraphics();
			for(String key:textures.keySet()) {
				BufferedImage block;
				try {
					block = ImageIO.read(new File(Directories.BLOCKS + key + Extensions.TEXTURES));				
				} catch (IOException e) {
					Logger.err("Could not find/read " + key + Extensions.TEXTURES, 2);
					try {
						block = ImageIO.read(new File(Files.TEXTURE_NOT_FOUND));				
					} catch (IOException ex) {
						Logger.err("Could not find/read tex_not_found.png!", 3);
						block = new BufferedImage(TEXTURE_SIZE, TEXTURE_SIZE, BufferedImage.TYPE_INT_RGB);
					}
				}
				
				int index = textures.get(key);
				int x = (index%(textureMapSize/TEXTURE_SIZE))*TEXTURE_SIZE;
				int y = (int) Math.floor(index/(textureMapSize/TEXTURE_SIZE))*TEXTURE_SIZE;
				g.drawImage(block, x, y, TEXTURE_SIZE, TEXTURE_SIZE, null);
			}
			
			try {
				ImageIO.write(textureMap, Extensions.TEXTURES.substring(1), new File(Files.TEXTURE_TEXTUREMAP));
			} catch (IOException e) {
				Logger.err("COULD NOT SAVE TEXTUREMAP TO DISK!", 1);
				System.exit(-1);
			}
		}
		textureMapId = loadTexture(Files.TEXTURE_TEXTUREMAP, true);
	}
	
	private static boolean checkTexturesChanged() {
		boolean hasChanged = false;
		String hashFile = FileSystem.ReadFile(Files.TEXTURE_HASHES);
		
		if(hashFile != null) {
			HashFile hashes = HashFile.Import(hashFile);
			if(hashes.Size() != textures.size()) {
				hasChanged = true;
			} else {
				for(String key:textures.keySet()) {
					int index = Textures.getTextureId(key);
					String block = FileSystem.ReadFile(Directories.BLOCKS + key + Extensions.TEXTURES);
					String[] hashData = hashes.GetEntry(index);
					if(hashData == null || (block == null && hashData[0] == "1") || !FileSystem.GetHash(block).equals(hashData[2])) {
						hasChanged = true;
						break;
					}	
				}
			}
		} else {
			hasChanged = true;
		}
		
		if(hasChanged)
			createTextureHashes();
		return hasChanged;
	}
	
	private static void createTextureHashes() {
		Logger.log("Creating texture hash file..", 2);
		File cacheFile = FileSystem.CreateNewFile(Files.TEXTURE_HASHES, "Texture-Hashes", true);	
		if(cacheFile != null) {
			Logger.err("Failed to create a new Texture-Hashes file!", 3);
		} else {
			HashFile hashes = new HashFile();

			Logger.log("Looping through textures..", 3);
			for(String key:textures.keySet()) {
				String file = FileSystem.ReadFile(Directories.BLOCKS + key + Extensions.TEXTURES);
				if(file != null) {
					hashes.AddEntry(textures.get(key), new String[]{"1", key, FileSystem.GetHash(file)});
				} else {
					hashes.AddEntry(textures.get(key), new String[]{"0", key});
				}
			}

			Logger.log("Writing hashes to file..", 3);
			FileSystem.WriteFile(cacheFile, hashes.Export(), "Texture Hashes");
		}
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
		return loadTexture(fileName, false);
	}
	
	public static int loadTexture(String fileName, boolean hasDirectory) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture(Extensions.TEXTURES.substring(1).toUpperCase(), new FileInputStream(hasDirectory ? fileName : Directories.RESOURCES+fileName+Extensions.TEXTURES));
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			//GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4F);
		} catch (FileNotFoundException e) {
			if(hasDirectory)
				Logger.err("Could not locate "+fileName, 1);
			else
				Logger.err("Could not locate "+fileName+Extensions.TEXTURES, 1);
		} catch (IOException e) {
			if(hasDirectory)
				Logger.err("An error occured while reading "+fileName+Extensions.TEXTURES, 1);
			else
				Logger.err("An error occured while reading "+fileName+Extensions.TEXTURES, 1);
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
