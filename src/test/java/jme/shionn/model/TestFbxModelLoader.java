package jme.shionn.model;

import com.jme3.anim.AnimComposer;
import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class TestFbxModelLoader extends SimpleApplication {

	public static void main(String[] args) {
		new TestFbxModelLoader().start();
	}

	@Override
	public void simpleInitApp() {
		flyCam.setMoveSpeed(5);
//		loadAnimeModel("mini-characters/Models/character-female-a");
		loadAnimeModel("mini-characters/Models/untitled");
		buildLights();
	}

	private void loadAnimeModel(String file) {
		Spatial spatial = new FbxModelLoader(this).load(file);
		spatial.setLocalTranslation(0, 0, -5);
		AnimComposer animComposer = spatial.getControl(AnimComposer.class);

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
