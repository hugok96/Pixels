package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class EntityCamera {

	private Vector3f position = new Vector3f(0, 50F, 50F);
	private float pitch=20, yaw, roll;
	private int speed = 1;
	
	public EntityCamera() {
		
	}
	
	public void move() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z-=speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x+=speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z+=speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x-=speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			position.y-=speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			position.y+=speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_K)) {
			pitch-=5F;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_I)) {
			pitch+=5F;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_O)) {
			roll-=6.66F;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_U)) {
			roll+=6.66F;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_J)) {
			yaw-=5F;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_L)) {
			yaw+=5F;
		}
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	
}
