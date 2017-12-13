package org.songbai.mutual.admin.model;

import java.util.Date;

import org.songbai.mutual.model.user.AppVersionModel;


public class AppVersionMongoModel {
	private Integer modifyUser;
	
	private AppVersionModel oldVersion;
	
	private AppVersionModel newVesion;
	
	private Date modifyTime;
	
	private String modifyName;

	public Integer getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(Integer modifyUser) {
		this.modifyUser = modifyUser;
	}

	public AppVersionModel getOldVersion() {
		return oldVersion;
	}

	public void setOldVersion(AppVersionModel oldVersion) {
		this.oldVersion = oldVersion;
	}

	public AppVersionModel getNewVesion() {
		return newVesion;
	}

	public void setNewVesion(AppVersionModel newVesion) {
		this.newVesion = newVesion;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	
	
	
	
}
