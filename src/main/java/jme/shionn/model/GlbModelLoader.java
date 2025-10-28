package jme.shionn.model;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.SceneGraphVisitorAdapter;
import com.jme3.scene.Spatial;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GlbModelLoader {

	private final SimpleApplication app;

	public Spatial load(String model) {
		Spatial spatial = app.getAssetManager().loadModel("Models/" + model + ".glb");
		spatial.depthFirstTraversal(new SceneGraphVisitorAdapter() {
			@Override
			public void visit(Geometry geom) {
				Material original = geom.getMaterial();
				Object texture = original.getParam("BaseColorMap").getValue();
				Material mat = new Material(app.getAssetManager(), Materials.LIGHTING);
				mat.setParam("DiffuseMap", texture);
//				mat.setParam("UseMaterialColors", true);
//				mat.setParam("Diffuse", ColorRGBA.White);
//				mat.setParam("Ambient", ColorRGBA.White.mult(.5f));
				geom.setMaterial(mat);
			}
		});
		spatial.setShadowMode(ShadowMode.CastAndReceive);
		return spatial;
	}

}
