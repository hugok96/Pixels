package world;

import org.lwjgl.util.vector.Vector2f;

public class Coord2d {
	
	public int x, z;
	
	public Coord2d(int x, int y) {
		this.x = x;
		this.z = y;
	}
	
	public Vector2f toVector() {
		return new Vector2f(this.x, this.z);
	}
	
	@Override
	public int hashCode() {
        return (this.x + " - " + this.z).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if(obj instanceof Coord2d)
			return obj.hashCode() == this.hashCode();
		
		return (this == obj);
	}
	
	@Override
	public String toString() {
		return "x" + this.x + ", z" + this.z;
	}
}
