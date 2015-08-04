package world;

import java.util.Random;


public class ChunkGenerator {
	
	public static float[][] generateNoise(int x, int z) {
		if(!SimplexNoise.seedIsSet) {
			SimplexNoise.setSeed(new Random().nextInt());
		}
	    float[][] noise = new float[Chunk.CHUNK_SIZE][Chunk.CHUNK_SIZE];
	    
	    //Frequency = features. Higher = more features
	    float layerF = Chunk.CHUNK_FEATURES;
	    
	    //Weight = smoothness. Higher frequency = more smoothness
	    float weight = Chunk.CHUNK_SMOOTHNESS;
	    
	    int offsetZ = x * Chunk.CHUNK_SIZE;
	    int offsetX = z * Chunk.CHUNK_SIZE;

	    for(int k = 0; k < 3; k++) {
	            for(int i = 0; i < Chunk.CHUNK_SIZE; i++) {
	                    for(int j = 0; j < Chunk.CHUNK_SIZE; j++) {
		                            noise[i][j] += (float) SimplexNoise.noise((offsetX+i) * layerF, (offsetZ+j) * layerF) * weight;
		                            noise[i][j] =  Math.max(-1.0f, Math.min(1.0f, noise[i][j]));
		                            if(k == 2)
		                            	noise[i][j] *= Chunk.CHUNK_AMPLIFICATION;
		                            
	                    }
	            }
	            layerF *= 3.5f;
	            weight *= 0.5f;
	    }
	   
	    return noise;
	}
}
