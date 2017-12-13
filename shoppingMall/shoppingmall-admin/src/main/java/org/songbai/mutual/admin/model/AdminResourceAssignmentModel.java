package org.songbai.mutual.admin.model;

import java.io.Serializable;

/**
 * 资源分配
 * 表示系统中将要在权限资源的基础上进行授权的控制
 * 为参与者{@link AdminActorModel} 分配权限资源{@link AdminSecurityResourceModel}
 * @author wangd
 *
 */
public class AdminResourceAssignmentModel implements Serializable{
    
    private static final long serialVersionUID = 3541511285521557709L;
    private Integer id;
    /**
     * 授权Id
     */
    private Integer authorityId;
    /**
     * 权限资源Id
     */
    private Integer securityResourceId;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getAuthorityId() {
        return authorityId;
    }
    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }
    public Integer getSecurityResourceId() {
        return securityResourceId;
    }
    public void setSecurityResourceId(Integer securityResourceId) {
        this.securityResourceId = securityResourceId;
    }
}
