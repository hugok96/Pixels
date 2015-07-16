package world;

import org.lwjgl.util.vector.Vector3f;

public class Coord3d {
	
	public int x, y, z;
	
	public Coord3d(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f toVector() {
		return new Vector3f(this.x, this.y, this.z);
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
        hash = hash * 17 + ("X:" + this.x).hashCode();;
        hash = hash * 31 + ("Y:" + this.y).hashCode();
        hash = hash * 13 + ("Z:" + this.z).hashCode();;
        return hash;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if(obj instanceof Coord3d)
			return obj.hashCode() == this.hashCode();
		
		return (this == obj);
	}
}
