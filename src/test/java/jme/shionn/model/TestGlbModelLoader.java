package jme.shionn.model;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class TestGlbModelLoader extends SimpleApplication {

	public static void main(String[] args) {
		new TestGlbModelLoader().start();
	}

	@Override
	public void simpleInitApp() {
		flyCam.setMoveSpeed(5);
//		loadModel("platformer/block-grass-hexagon");
		loadAnimModel("mini-characters/Models/GLB/character-male-b");
		buildLights();
	}


	private void loadModel(String file) {
		Spatial spatial = new GlbModelLoader(this).load(file);
		spatial.setLocalTranslation(0, 0, -5);
		rootNode.attachChild(spatial);
	}

	private void loadAnimModel(String file) {
		Spatial spatial = new GlbModelLoader(this).load(file);
		spatial.setLocalTranslation(0, 0, -5);
		rootNode.attachChild(spatial);
	}

	private void buildLights() {
		AmbientLight al = new AmbientLight();
		al.setColor(new ColorRGBA(0.3f, 0.3f, 0.3f, 1.0f));
		rootNode.addLight(al);
		DirectionalLight sun = new DirectionalLight(new Vector3f(-1, -1, -1).normalize(), ColorRGBA.White);
		rootNode.addLight(sun);
	}
}
