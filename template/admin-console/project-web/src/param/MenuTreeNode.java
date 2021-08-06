/*
 * @(#)MenuTreeNode.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.param;

import java.util.ArrayList;
import java.util.List;

import com.github.javaclub.sword.spring.BeanCopierUtils;
import ${packagePrefix}.dataobject.MenuModuleDO;

/**
 * MenuTreeNode
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: MenuTreeNode.java ${currentTime} Exp $
 */
public class MenuTreeNode extends MenuModuleDO {

	private static final long serialVersionUID = 1L;

	private Boolean checked = false;
	
	private List<MenuTreeNode> children;

	public MenuTreeNode() {
	}
	
	public MenuTreeNode(MenuModuleDO mmd) {
		BeanCopierUtils.copyProperties(mmd, this);
	}
	
	public MenuTreeNode(MenuModuleDO mmd, boolean isChecked) {
		BeanCopierUtils.copyProperties(mmd, this);
		this.checked = isChecked;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public List<MenuTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTreeNode> children) {
		this.children = children;
	}
	
	public void addChild(MenuTreeNode node) {
        if (this.children == null) {
            this.setChildren(new ArrayList<MenuTreeNode>());
        }
        this.getChildren().add(node);
	}
	
	public boolean hasChild() {
		return null != children && children.size() > 0;
	}

}
