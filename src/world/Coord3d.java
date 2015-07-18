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
        return (this.x + " - " + this.y + " - " + this.z).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if(obj instanceof Coord3d)
			return obj.hashCode() == this.hashCode();
		
		return (this == obj);
	}
	
	@Override
	public String toString() {		
		return "x" + this.x + ", y" + this.y + ", z" + this.z;
	}
}
