package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Model;
import shaders.StaticShader;
import entities.Entity;
import entities.EntityCamera;
import entities.EntityLight;

public class MasterRenderer {
	
	private StaticShader shader = new StaticShader();
	private Renderer renderer = new Renderer(shader);
	
	private Map<Model, List<Entity>> entities = new HashMap<Model, List<Entity>>();
	
	public void render(EntityLight sun, EntityCamera camera) {
		renderer.prepare();
		shader.start();
		shader.loadLight(sun);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		entities.clear();
	}
	
	public void processEntity(Entity entity) {
		if(entities.containsKey(entity.getModel())) {
			entities.get(entity.getModel()).add(entity);
		} else {
			List<Entity> list = new ArrayList<Entity>();
			list.add(entity);
			entities.put(entity.getModel(), list);
		}
	}
	
	public void clear() {
		shader.clear();
	}
}
