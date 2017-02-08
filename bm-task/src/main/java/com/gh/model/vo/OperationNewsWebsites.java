package com.gh.model.vo;

import java.io.Serializable;
import java.util.Date;

import com.gh.model.pojo.BaseEntity;

/**
 * 新闻资源网站模型
 * 
 * @author HeMingwei
 *
 */
public class OperationNewsWebsites extends BaseEntity implements Serializable {
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2777910400520903639L;

	private String id;

    private String name;
    
    private String type;

    private String websiteUrl;
    
    private String enabled;
    
    private String newsUrlRegularExpression;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String remarks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }
    
    public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getNewsUrlRegularExpression() {
		return newsUrlRegularExpression;
	}

	public void setNewsUrlRegularExpression(String newsUrlRegularExpression) {
		this.newsUrlRegularExpression = newsUrlRegularExpression;
	}

	public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}