package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.Model;
import models.ModelBlock;
import textures.Textures;

public class EntityPlayer extends EntityModel {
	
	private Vector3f position = new Vector3f(0, 50F, 50F);
	private float movementSpeed = 25;
	private float crouchSpeed = movementSpeed*0.5F;
	private float runSpeed = movementSpeed*2.25F;
	private float turnSpeed = 160;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;

	public EntityPlayer(Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(new ModelBlock(Textures.tnt, Textures.tnt_top, Textures.tnt_bottom), position, rotX, rotY, rotZ, scale);
	}
	
	public void move(EntityCamera camera) {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * (5F/1000F), 0);
		float distance = currentSpeed * (1F/1000F);
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			super.increasePosition(0, -(25*(1F/1000F)), 0);
			camera.increasePosition(0, -(25*(1F/1000F)), 0);
		} else if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			super.increasePosition(0, 25*(1F/1000F), 0);
			camera.increasePosition(0, (25*(1F/1000F)), 0);
		}
		camera.increaseRotation(0, currentTurnSpeed * (5F/1000F), 0);
		camera.increasePosition(dx, 0, dz);
		
	}
	
	private void checkInputs() {
		boolean isRunning = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		boolean isCrouching = Keyboard.isKeyDown(Keyboard.KEY_CAPITAL);
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.currentSpeed = isRunning ? runSpeed : isCrouching ? crouchSpeed : movementSpeed;
		}

		else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.currentSpeed = -(isRunning ? runSpeed : isCrouching ? crouchSpeed : movementSpeed);
		}
		else {
			this.currentSpeed = 0;
		}
		

		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.currentTurnSpeed = -turnSpeed;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentTurnSpeed = turnSpeed;
		} 
		else {
			this.currentTurnSpeed = 0;
		}
	}
}
