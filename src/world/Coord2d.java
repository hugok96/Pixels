package world;

import org.lwjgl.util.vector.Vector2f;

public class Coord2d {
	
	public int x, y;
	
	public Coord2d(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f toVector() {
		return new Vector2f(this.x, this.y);
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
        hash = hash * 17 + ("X:" + this.x).hashCode();
        hash = hash * 31 + ("Y:" + this.y).hashCode();
        return hash;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if(obj instanceof Coord2d)
			return obj.hashCode() == this.hashCode();
		
		return (this == obj);
	}
}
