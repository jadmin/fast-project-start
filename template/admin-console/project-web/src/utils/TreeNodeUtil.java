/*
 * @(#)TreeNodeUtil.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.github.javaclub.sword.core.Collections;
import ${packagePrefix}.param.MenuTreeNode;

/**
 * TreeNodeUtil
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: TreeNodeUtil.java ${currentTime} Exp $
 */
public class TreeNodeUtil {

	public static List<MenuTreeNode> assembleTree(List<MenuTreeNode> listNodes) {
        List<MenuTreeNode> newTreeNodes = new ArrayList<>();
        if(null == listNodes || 0 >= listNodes.size()) {
        	return newTreeNodes;
        }
        // 循环赋值最上面的节点数据
        // 赋值最上面节点的值
        newTreeNodes.addAll(listNodes.stream()
                .filter(t -> null == t.getParentId() || Objects.equals(0L, t.getParentId()))
                .collect(Collectors.toList()));
        // 循环处理子节点数据
        for (MenuTreeNode t : newTreeNodes) {
            //递归
            assembleTree(t, listNodes);
        }
        return newTreeNodes;
    }
	
    static void assembleTree(MenuTreeNode node, List<MenuTreeNode> listNodes) {
        if (node != null && !Collections.isEmpty(listNodes)) {
            // 循环节点数据，如果是子节点则添加起来
            listNodes.stream().filter(t -> Objects.equals(t.getParentId(), node.getId())).forEachOrdered(node::addChild);
            // 循环处理子节点数据,递归
            if (!Collections.isEmpty(node.getChildren())) {
                for (MenuTreeNode t : node.getChildren()) {
                    //递归
                    assembleTree(t, listNodes);
                }
            }
        }
    }

}
