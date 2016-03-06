package entities;

import org.lwjgl.util.vector.Vector3f;

public class EntityCamera extends EntityBase {

	public EntityCamera(Vector3f pos) {
		super(pos, 0, 0, 0);
	}

	private float pitch=30, yaw, roll;
	
	@Override
	public void increaseRotation(float dx, float dy, float dz) {
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
