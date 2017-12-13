package org.songbai.mutual.admin.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 授权是有一个抽象的概念，是角色{@link AdminRoleModel} 和权限{@link AdminPerssionModel}共同的基类
 * 它代表某种权限或权限集合（AdminRoleModel），可以被授予Actor（即对Actor授予Authority）
 * 它代表一系列可执行的操作或责任，用于限定您在软件系统中能做什么，不能做什么
 * @author wangd
 *
 */
public class AdminAuthorityModel  implements Serializable{
    private static final long serialVersionUID = 7350301565941596735L;

    private Integer id;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 类型
     * 角色、权限
     */
    private String category;

    /**
     * 创建者
     */
    private Integer createId;
    
    /**
     * 创建时间
     */
    private Timestamp createDate;
    /**
     * 最后修改时间
     */
    private Timestamp lastModifyTime;
    
    /**
     * 是否是admin独有
     */
    private  Integer isAdmin ;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Timestamp lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }


	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
    
    
}

