package jme.shionn.model;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;

public class MapLoader {


	private static final String TERRAIN_LIGHTING_J3MD = "Common/MatDefs/Terrain/TerrainLighting.j3md";
	private static final String HEIGHT_BBASED_J3MD = "Common/MatDefs/Terrain/HeightBasedTerrain.j3md";
	private AssetManager assetManager;
	private final SimpleApplication app;
	private GlbModelLoader loader;

	public MapLoader(SimpleApplication app) {
		this.app = app;
		this.assetManager = app.getAssetManager();
		this.loader = new GlbModelLoader(app);
	}


	public Spatial loadTest1() {

		Material material = new Material(assetManager, TERRAIN_LIGHTING_J3MD);
		material.setBoolean("useTriPlanarMapping", false);
		material.setBoolean("WardIso", true);
		material.setTexture("AlphaMap", assetManager.loadTexture("Textures/Terrain/splat/alphamap.png"));

		material.setTexture("DiffuseMap", loadTexture("splat/grass.jpg"));
		material.setTexture("NormalMap", loadTexture("splat/grass_normal.jpg"));
		material.setFloat("DiffuseMap_0_scale", 64);

		// DIRT texture
		Texture dirt = assetManager.loadTexture("Textures/Terrain/splat/dirt.jpg");
		dirt.setWrap(WrapMode.Repeat);
		Texture dirtN = assetManager.loadTexture("Textures/Terrain/splat/dirt_normal.png");
		dirtN.setWrap(WrapMode.Repeat);
		material.setTexture("DiffuseMap_1", dirt);
		material.setTexture("NormalMap_1", dirtN);
		material.setFloat("DiffuseMap_1_scale", 16);

		// ROCK texture
		Texture rock = assetManager.loadTexture("Textures/Terrain/splat/road.jpg");
		rock.setWrap(WrapMode.Repeat);
		Texture rockN = assetManager.loadTexture("Textures/Terrain/splat/road_normal.png");
		rockN.setWrap(WrapMode.Repeat);
		material.setTexture("DiffuseMap_2", rock);
		material.setTexture("NormalMap_2", rockN);
		material.setFloat("DiffuseMap_2_scale", 128);

		Texture heightMapImage = assetManager.loadTexture("Textures/Terrain/splat/mountains512.png");
		AbstractHeightMap heightmap = new ImageBasedHeightMap(heightMapImage.getImage(), .25f);
		heightmap.load();

		int patchSize = 64;
		int totalSize = 512;
		TerrainQuad terrain = new TerrainQuad("terrain", patchSize + 1, totalSize + 1, heightmap.getHeightMap());
		terrain.setMaterial(material);
		TerrainLodControl control = new TerrainLodControl(terrain, app.getCamera());
		control.setLodCalculator(new DistanceLodCalculator(65, 2.7f)); // patch size, and a multiplier
//		terrain.setLocalScale(10,3,10);
		terrain.setLocalTranslation(0, -120, 0);
		terrain.setLocalScale(new Vector3f(5, 5, 5));
		terrain.setShadowMode(ShadowMode.CastAndReceive);

		return terrain;
	}

	private Texture loadTexture(String file) {
		Texture tex = assetManager.loadTexture("Textures/Terrain/" + file);
		tex.setWrap(WrapMode.Repeat);
		return tex;
	}

	public Spatial loadTest2() {
		try {
			int patchSize = 64;
			int totalSize = 512;

			Material material = new Material(assetManager, HEIGHT_BBASED_J3MD);
			material.setTexture("slopeColorMap", loadTexture("cartoon/stone.png"));
			material.setTexture("region1ColorMap", loadTexture("hand/sand.png"));
			material.setVector3("region1", new Vector3f(20, 35, 64));
			material.setTexture("region2ColorMap", loadTexture("cartoon/grass.png"));
			material.setVector3("region2", new Vector3f(35, 60, 64));
			material.setTexture("region3ColorMap", loadTexture("cartoon/dirt.png"));
			material.setVector3("region3", new Vector3f(60, 100, 64));
			material.setTexture("region4ColorMap", loadTexture("cartoon/stone.png"));
			material.setVector3("region4", new Vector3f(100, 150, 64));

			Texture heightMapImage = assetManager.loadTexture("Textures/Terrain/splat/mountains512.png");
			AbstractHeightMap heightmap = new ImageBasedHeightMap(heightMapImage.getImage(), .25f);
			heightmap.load();
//		MidpointDisplacementHeightMap heightmap = new MidpointDisplacementHeightMap(totalSize + 1, 1, .5f, 0);
//		heightmap.load();
//			HillHeightMap heightmap = new HillHeightMap(totalSize + 1, 500, 2, 50, 0);
//			FluidSimHeightMap heightmap = new FluidSimHeightMap(totalSize + 1, 100, 0, 100, 1, 1, 1, 1, 0);
			TerrainQuad terrain = new TerrainQuad("terrain", patchSize + 1, totalSize + 1, heightmap.getHeightMap());
			terrain.setMaterial(material);
			terrain.setLocalTranslation(0, -40, 0);
			terrain.setLocalScale(new Vector3f(5, 2, 5));
			terrain.setShadowMode(ShadowMode.Receive);
			return terrain;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public Spatial loadBuild() {
		Node node = new Node();
//		node.attachChild(load("rocks-a", 0, 0));
//		node.attachChild(load("rocks-b", 5, 0));
//		node.attachChild(load("rocks-c", 10, 0));
//		node.attachChild(load("rocks-sand-a", 0, 5));
//		node.attachChild(load("rocks-sand-b", 5, 5));
//		node.attachChild(load("rocks-sand-c", 10, 5));
		return node;
	}

	private Spatial load(String file, float x, float z) {
		Spatial spatial = loader.load(file);
		spatial.setLocalTranslation(x, 0, z);
		return spatial;
	}

}
