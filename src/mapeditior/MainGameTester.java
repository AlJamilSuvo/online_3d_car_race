package mapeditior;

import entities.*;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import models.RawModel;
import models.TextureModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import randerEngine.DisplayManager;
import randerEngine.Loader;
import randerEngine.MasterRenderer;
import terrains.NormaledTerrain;
import textures.ModelTexture;
import textures.NormaledTerrainTexturePack;
import textures.TerrainTexture;

import toolBoxs.Maths;
import toolBoxs.MousePicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainGameTester {
    private List<NormaledTerrain> terrains =new ArrayList<>();
    static private List<Entity> entities=new ArrayList<>();
    static public boolean canRanderAble=true;

    static private Player player;

    static private Entity test;
    private boolean lock;
    static Loader loader;
    Camera camera;
    MasterRenderer renderer;
    MousePicker picker;
    List<Light> lights;
    static HashMap<String,TextureModel> modelList=new HashMap<>();
    public MainGameTester() {
        DisplayManager.createDisplay();
        lights=new ArrayList<>();
        loader=new Loader();

        light=new Light(new Vector3f(0,1000,0),new Vector3f(0.95f,0.95f,0.95f ));
        lights.add(light);
        ModelTexture wheat=new ModelTexture(loader.loadTexture("wheat"));
        ModelTexture fernTex=new ModelTexture(loader.loadTexture("fern"));
        //fernTex.setUsingFakeLighting(true);
       //fernTex.setHasTransparency(true);
        ModelData ferndata=OBJFileLoader.loadOBJ("fern");
        RawModel fern=loader.LoadToVAO(ferndata.getVertices(), ferndata.getTextureCoords(), ferndata.getNormals(), ferndata.getIndices());



       // wheat.setUsingFakeLighting(true);
        ModelTexture bTreeTex=new ModelTexture(loader.loadTexture("birch_tree"));
        ModelTexture houseTex=new ModelTexture(loader.loadTexture("wood house texture"));
        ModelTexture grillTex=new ModelTexture(loader.loadTexture("grill wall"));
        ModelTexture polytreeTexture=new ModelTexture(loader.loadTexture("lowPolyTree"));
        ModelTexture grassTex=new ModelTexture(loader.loadTexture("grassTexture"));
      //  grassTex.setUsingFakeLighting(true);
        grassTex.setHasTransparency(true);
        ModelTexture buildTex=new ModelTexture(loader.loadTexture("bush"));
        buildTex.setUseFakeLighting(true);
        ModelTexture cocTex=new ModelTexture(loader.loadTexture("pine"));
        ModelData houseData=OBJFileLoader.loadOBJ("wood house");
        ModelData checkPoint=OBJFileLoader.loadOBJ("check_point_collide");
        RawModel checkPointRawModel=loader.LoadToVAO(checkPoint.getVertices(), checkPoint.getTextureCoords(), checkPoint.getNormals(), checkPoint.getIndices());
        RawModel houseRawModel=loader.LoadToVAO(houseData.getVertices(), houseData.getTextureCoords(), houseData.getNormals(), houseData.getIndices());
        ModelData buildingData=OBJFileLoader.loadOBJ("brush");
        RawModel buildingRawModel=loader.LoadToVAO(buildingData.getVertices(), buildingData.getTextureCoords(), buildingData.getNormals(), buildingData.getIndices());
        ModelData polyTreeData=OBJFileLoader.loadOBJ("lowpolyTree");
        RawModel polyTreeModel=loader.LoadToVAO(polyTreeData.getVertices(), polyTreeData.getTextureCoords(), polyTreeData.getNormals(), polyTreeData.getIndices());
        ModelData fernData=OBJFileLoader.loadOBJ("grass");
        RawModel fernModel=loader.LoadToVAO(fernData.getVertices(), fernData.getTextureCoords(), fernData.getNormals(), fernData.getIndices());
        ModelData cocTreeData=OBJFileLoader.loadOBJ("pine");
        RawModel cocTreeRawModel=loader.LoadToVAO(cocTreeData.getVertices(), cocTreeData.getTextureCoords(), cocTreeData.getNormals(), cocTreeData.getIndices());
        ModelData grilldata=OBJFileLoader.loadOBJ("grill wall");
        RawModel grillRawModel=loader.LoadToVAO(grilldata.getVertices(), grilldata.getTextureCoords(), grilldata.getNormals(), grilldata.getIndices());
        ModelData bTreedata =OBJFileLoader.loadOBJ("birch_tree");
        RawModel bTreeRawModel=loader.LoadToVAO(bTreedata.getVertices(), bTreedata.getTextureCoords(), bTreedata.getNormals(), bTreedata.getIndices());


        TextureModel house=new TextureModel(houseRawModel,houseTex);
        house.setNormalTexture();
        TextureModel fernTexModel=new TextureModel(fernModel,grassTex);
        TextureModel polyTreeTexModel=new TextureModel(polyTreeModel,polytreeTexture);
        TextureModel buildingModel=new TextureModel(buildingRawModel,buildTex);
        TextureModel cocTreeModel=new TextureModel(cocTreeRawModel,cocTex);
        TextureModel checkPointModel=new TextureModel(checkPointRawModel,polytreeTexture)   ;
        TextureModel grillModel=new TextureModel(grillRawModel,grillTex);
        TextureModel treeModel=new TextureModel(bTreeRawModel,bTreeTex);
        TextureModel wheatModel=new TextureModel(fernModel,wheat);
        TextureModel FernModel=new TextureModel(fern,fernTex);
        modelList.put("house",house);
        modelList.put("grass",fernTexModel);
        modelList.put("tree",polyTreeTexModel);
        modelList.put("bush",buildingModel);
        modelList.put("Cococnut_Tree",cocTreeModel);
        modelList.put("check_point",checkPointModel);
        modelList.put("grill",grillModel);
        modelList.put("Birch_Tree",treeModel);
        modelList.put("Wheat",wheatModel);
        modelList.put("Fern",FernModel);





        lock=true;




        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("pitch"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("sand"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture rNorTexture = new TerrainTexture(loader.loadTexture("norpitch"));
        TerrainTexture gNorTexture = new TerrainTexture(loader.loadTexture("cross"));
        TerrainTexture bNorTexture = new TerrainTexture(loader.loadTexture("norGrass"));
        TerrainTexture bgNorTexture = new TerrainTexture(loader.loadTexture("norGrass"));
        TerrainTexture blendMap;// = new TerrainTexture(loader.loadTexture("map"));
        NormaledTerrainTexturePack texturePack = new NormaledTerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture, rNorTexture, gNorTexture, bNorTexture, bgNorTexture);
        //NormaledTerrain terrain=new NormaledTerrain(0,0,loader,texturePack,blendMap,"plain height");
        //TerrainTexturePack gtexturePack=new TerrainTexturePack(backgroundTexture,rTexture,gTexture,bTexture);//,rNorTexture,gNorTexture,bNorTexture,bgNorTexture);

        //terrains.add(terrain);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                blendMap=new TerrainTexture(loader.loadTexture("map/a "+j+i));
                NormaledTerrain terrain = new NormaledTerrain(i, j, loader, texturePack, blendMap, "plain height");
                terrains.add(terrain);
            }
        }




        ModelTexture pTexture=new ModelTexture(loader.loadTexture("car texture"));
        ModelData pData=OBJFileLoader.loadOBJ("car final");
        RawModel pRawModel=loader.LoadToVAO(pData.getVertices(), pData.getTextureCoords(), pData.getNormals(), pData.getIndices());
        TextureModel pModel=new TextureModel(pRawModel,pTexture);
       /* List<TextureModel> tn=new ArrayList<>();
        tn.add(pModel);
        EntityPack en=new EntityPack(tn,new Vector3f(0,0,0),0,0,0,5f,null);
        Player player=new Player(en);*/


        ModelTexture mt1=new ModelTexture(loader.loadTexture("/car_hd/black"));
        ModelTexture mt2=new ModelTexture(loader.loadTexture("/car_hd/body"));
        ModelTexture mt3=new ModelTexture(loader.loadTexture("/car_hd/glass"));
        ModelTexture mt4=new ModelTexture(loader.loadTexture("/car_hd/gray"));
        ModelTexture mt5=new ModelTexture(loader.loadTexture("/car_hd/red"));
        ModelTexture mt6=new ModelTexture(loader.loadTexture("/car_hd/tire"));
        ModelTexture mt7=new ModelTexture(loader.loadTexture("/car_hd/white"));
        ModelData data1=OBJFileLoader.loadOBJ("/car_hd/black");
        RawModel rm1=loader.LoadToVAO(data1.getVertices(), data1.getTextureCoords(), data1.getNormals(), data1.getIndices());
        ModelData data2=OBJFileLoader.loadOBJ("/car_hd/body");
        RawModel rm2=loader.LoadToVAO(data2.getVertices(), data2.getTextureCoords(), data2.getNormals(), data2.getIndices());
        ModelData data3=OBJFileLoader.loadOBJ("/car_hd/glass");
        RawModel rm3=loader.LoadToVAO(data3.getVertices(), data3.getTextureCoords(), data3.getNormals(), data3.getIndices());
        ModelData data4=OBJFileLoader.loadOBJ("/car_hd/gray");
        RawModel rm4=loader.LoadToVAO(data4.getVertices(), data4.getTextureCoords(), data4.getNormals(), data4.getIndices());
        ModelData data5=OBJFileLoader.loadOBJ("/car_hd/red");
        RawModel rm5=loader.LoadToVAO(data5.getVertices(), data5.getTextureCoords(), data5.getNormals(), data5.getIndices());
        ModelData data6=OBJFileLoader.loadOBJ("/car_hd/tire");
        RawModel rm6=loader.LoadToVAO(data6.getVertices(), data6.getTextureCoords(), data6.getNormals(), data6.getIndices());
        ModelData data7=OBJFileLoader.loadOBJ("/car_hd/white");
        RawModel rm7=loader.LoadToVAO(data7.getVertices(), data7.getTextureCoords(), data7.getNormals(), data7.getIndices());
        TextureModel tm1=new TextureModel(rm1,mt1);
        TextureModel tm2=new TextureModel(rm2,mt2);
        TextureModel tm3=new TextureModel(rm3,mt3);
        TextureModel tm4=new TextureModel(rm4,mt4);
        TextureModel tm5=new TextureModel(rm5,mt5);
        TextureModel tm6=new TextureModel(rm6,mt6);
        TextureModel tm7=new TextureModel(rm7,mt7);
        List<TextureModel> carModel=new ArrayList<>();
        carModel.add(tm1);
        carModel.add(tm2);
        carModel.add(tm3);
        carModel.add(tm4);
        carModel.add(tm5);
        carModel.add(tm6);
        carModel.add(tm7);
        EntityPack pack=new EntityPack(carModel,new Vector3f(0,0,0),0,0,0,3f,null);



        Player player=new Player(pack);



		                            		/*Player*/
        ModelTexture terrainTexture=new ModelTexture(loader.loadTexture("grass1"));
        terrainTexture.setShineDamper(1.0f);

        renderer=new MasterRenderer(loader);

		/* Terrain Texture*/
        camera=new Camera(player);
        picker=new MousePicker(camera,renderer.getProjectionMatrix(),terrains);
        while(!Display.isCloseRequested()){
            if (Mouse.isButtonDown(3)) {
                lock=false;
            }
            if (Mouse.isButtonDown(1)) lock=true;
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) lock=!lock;
            String str;
            float inFPS=DisplayManager.getFrameTimeSeconds();
            float FPS=(float) Math.pow((double)inFPS,-1.0);
            str="FPS :"+FPS+" ";
            if (lock) DisplayManager.setTitle(str+"Locked");
            else DisplayManager.setTitle(str+"Unlocked");

            light.setColor(new Vector3f(1f,1f,1f));
            light.setPosition(new Vector3f(player.getPosition().x,500,player.getPosition().z));
            skycolor=new Vector3f(0.6f,.6f,.85f);
            NormaledTerrain playerTerrain=getTerrainIndex(player.getPosition().x,player.getPosition().z);
            player.checkInput();
            player.move(playerTerrain);
            camera.move();
            picker.update();
            if (!lock && test!=null) test.setPosition(picker.getCurrentTerrainPoint());
            for (NormaledTerrain t:terrains){
                renderer.procesNormalTerrain(t);
            }
            int i=0;
            if (canRanderAble){
                for(Entity e:entities){
                    Vector3f vert=Vector3f.sub(e.getPosition(),player.getPosition(),null);
                    if (Vector3f.dot(player.getFrontDirection(),vert)>0){
                        if (Maths.getDistance(player,e)<500) renderer.processEntity(e);
                    }
                    else{
                        if(Maths.getDistance(player,e)<150) renderer.processEntity(e);
                    }
                   // renderer.processEntity(e);
                }

            }

            renderer.render(player,lights, camera,skycolor);


            DisplayManager.updateDisplay();



        }
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();



    }

    private NormaledTerrain getTerrainIndex(float x,float z){
        for (NormaledTerrain t:terrains){
            if (x<t.getX() && x>t.getX()-800 && z<t.getZ() && z>t.getZ()-800) return t;
        }
        return terrains.get(0);
    }


    static Light light;
    private static Vector3f skycolor;

    static public int addEntity(TextureModel model,Vector3f position,float rx,float ry,float rz,float scale){
        Entity entity=new Entity(model,position,rx,ry,rz,scale,null);
        System.out.println(entities.size());
        entities.add(entity);
        return entities.indexOf(entity);
    }
    static public void setTestEntity(int index){
        test=entities.get(index);
    }
    static public void setTestParam(float rx, float ry, float rz, float size){
        test.setScale(size);
        test.setRotX(rx);
        test.setRotY(ry);
        test.setRotZ(rz);
    }
    static public void removeEntity(int index){
        if (test==entities.get(index)) test=null;
        entities.remove(index);
    }

    static public Entity getEntity(int index){
        return entities.get(index);
    }

    static public void setCamera(Vector3f positon){
        player.getEntity().setPosition(positon);
    }

    static public List<Entity> getEntities() {
        return entities;
    }

    public static HashMap<String, TextureModel> getModelList() {
        return modelList;
    }
    public  static void nsetTest(){
        test=null;
    }
}
