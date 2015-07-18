package world;

public class ChunkGenerator {
	
	public static float[][] generateNoise(int x, int z, int h) {
		if(!SimplexNoise.seedIsSet) {
			SimplexNoise.setSeed(666);
		}
	    float[][] noise = new float[Chunk.CHUNK_SIZE_X][Chunk.CHUNK_SIZE_Z];
	    //Frequency = features. Higher = more features
	    float layerF = 0.003f;
	    //Weight = smoothness. Higher frequency = more smoothness
	    float weight = 1;
	   
	    for(int k = 0; k < 3; k++) {
	            for(int i = 0; i < Chunk.CHUNK_SIZE_X; i++) {
	                    for(int j = 0; j < Chunk.CHUNK_SIZE_Z; j++) {
	                            noise[i][j] += (float) SimplexNoise.noise(i * layerF, j * layerF) * weight;
	                            noise[i][j] =  Math.max(-1.0f, Math.min(1.0f, noise[i][j]));
	                            if(k == 3) {
	                            	noise[i][j] *= h;
	                            }
	                    }
	            }
	            layerF *= 3.5f;
	            weight *= 0.5f;
	    }
	   
	    return noise;
	}
}
