/**
 * 
 */
package models;

import textures.ModelTexture;
public class TextureModel {
	RawModel rawModel;
	ModelTexture modelTexture;
	ModelTexture normalTexture;
	boolean usingNormal=false;
	public TextureModel(RawModel model,ModelTexture texture){
		this.rawModel=model;
		this.modelTexture=texture;
	}
	public void setNormalTexture(){
		usingNormal=true;
	}



	public RawModel getRawModel() {
		return rawModel;
	}

	public ModelTexture getModelTexture() {
		return modelTexture;
	}

	public ModelTexture getNormalTexture() {
		return normalTexture;
	}

	public boolean isUsingNormal() {
		return usingNormal;
	}
}
