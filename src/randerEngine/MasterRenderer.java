package randerEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.*;
import models.RawModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import models.TextureModel;
import shader.NormaledTerrainShader;
import shader.StaticShader;
import shader.TerrainShader;
import skybox.SkyboxRenderer;
import terrains.EntityTerrain;
import terrains.NormaledTerrain;
import terrains.Terrain;

public class MasterRenderer { 
	
	
	
	private static final float FOV=90.0f;
	private static final float NEAR_PLANE=.1f;
	private static final float FAR_PLANE=1000.0f;
	
	
	
	private StaticShader shader;
	private EntityRenderer renderer;
	private EntityTerrainRenderer entityTerrainRenderer;
	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader;

	private NormaledTerrainRenderer normaledTerrainRenderer;
	private NormaledTerrainShader normaledTerrainShader;



	List<Terrain> terrains=new ArrayList<>();
	List<NormaledTerrain> normaledTerrains=new ArrayList<>();
	private List<EntityPack> entityPacks=new ArrayList<>();
	private Map<TextureModel,List<Entity>> entities=new HashMap<>();
	private Map<RawModel,List<EntityTerrain>> rawModelListMap=new HashMap<>();
	private Matrix4f projectionMatrix;
	public static float RED=.8f;
	public static float GREEN=.8f;
	public static float BLUE=.8f;


	private SkyboxRenderer skyboxRenderer;
	public MasterRenderer(Loader loader){
		createProjectionMatrix();
		skyboxRenderer=new SkyboxRenderer(loader,projectionMatrix);
		shader=new StaticShader();
		terrainShader=new TerrainShader();
		normaledTerrainShader=new NormaledTerrainShader();
		renderer=new EntityRenderer(shader,projectionMatrix);
		terrainRenderer=new TerrainRenderer(terrainShader,projectionMatrix);
		normaledTerrainRenderer=new NormaledTerrainRenderer(normaledTerrainShader,projectionMatrix);
		entityTerrainRenderer=new EntityTerrainRenderer(terrainShader,projectionMatrix);
	}
	public void render(Player player,List<Light> lights,Camera camera,Vector3f skycolor)
	{
		RED=skycolor.x;
		GREEN=skycolor.y;
		BLUE=skycolor.z;
		prepare();
		shader.start();
		shader.loadLight(lights);
		shader.loadSkyColor(skycolor);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		shader.start();
		shader.loadLight(lights);
		shader.loadSkyColor(skycolor);
		shader.loadViewMatrix(camera);
		renderer.render(player);
		for(EntityPack entityPack:entityPacks) renderer.render(entityPack);
		shader.stop();
		terrainShader.start();
		terrainShader.loadLight(lights);
		terrainShader.loadSkyColor(skycolor);
		terrainShader.loadViewMatrix(camera);
		//terrainRenderer.render(terrains);
		entityTerrainRenderer.render(rawModelListMap);
		terrainShader.stop();


		normaledTerrainShader.start();
		normaledTerrainShader.loaduseNormal(false);
		normaledTerrainShader.loadLight(lights);
		normaledTerrainShader.loadSkyColor(skycolor);
		normaledTerrainShader.loadViewMatrix(camera);
		normaledTerrainRenderer.render(normaledTerrains);
		normaledTerrainShader.stop();
		entityPacks.clear();
		skyboxRenderer.renderer(camera,skycolor);
		terrains.clear();
		normaledTerrains.clear();
		entities.clear();
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public void cleanUp(){
		shader.cleanUp();
		terrainShader.cleanUp();
		normaledTerrainShader.cleanUp();
	}
	public void procesTerrain(Terrain terrain){
		terrains.add(terrain);
	}
	public void procesNormalTerrain(NormaledTerrain terrain){normaledTerrains.add(terrain);}
	public void processEntity(Entity entity){
		TextureModel entityModel=entity.getModel();
		List<Entity> batch=entities.get(entityModel);
		if (batch!=null){
			batch.add(entity);
		}else{
			List<Entity> NewBatch=new ArrayList<>();
			NewBatch.add(entity);
			entities.put(entityModel, NewBatch);
		}
	}

	public void processEntityTerrain(EntityTerrain entity){
		RawModel entityModel=entity.getModel();
		List<EntityTerrain> batch=rawModelListMap.get(entityModel);
		if (batch!=null){
			batch.add(entity);
		}else{
			List<EntityTerrain> NewBatch=new ArrayList<>();
			NewBatch.add(entity);
			rawModelListMap.put(entityModel, NewBatch);
		}
	}

	public void renderEntityPack(EntityPack entityPack){
		entityPacks.add(entityPack);
	}
	
	public static void enableCulling(){
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	public static void disableCulling(){
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	public void prepare(){
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(1f, 1f, 1f, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	private void createProjectionMatrix(){
		float aspectRatio=(float)Display.getWidth()/(float)Display.getHeight();
		float y_scale=(float) (1.0f/Math.tan(Math.toRadians( FOV/2.0f)))*aspectRatio;
		float x_scale= y_scale/aspectRatio;
		float frustum_length=FAR_PLANE-NEAR_PLANE;
		projectionMatrix=new Matrix4f();
		projectionMatrix.m00=x_scale;
		projectionMatrix.m11=y_scale;
		projectionMatrix.m22=-((FAR_PLANE+NEAR_PLANE)/frustum_length);
		projectionMatrix.m23=-1;
		projectionMatrix.m32=-((2*FAR_PLANE*NEAR_PLANE)/frustum_length);
		projectionMatrix.m33=0;
	}
}
