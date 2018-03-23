/*
 * @(#)TmqTopic.java	2017年9月14日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.domain.enumtype;

/**
 * TmqTopic
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: TmqTopic.java 2017年9月14日 17:41:03 Exp $
 */
public enum TmqTopic {
	
	/**
	 * 测试 
	 */
	KA_USER_LOGINED("test", "dummy"),
	
	;

	
	TmqTopic(String topic, String subTopic) {
        this.topic = topic;
        this.subTopic = subTopic;
    }

    private String topic;

    private String subTopic;

    public String getTopic() {
        return topic;
    }

    public String getSubTopic() {
        return subTopic;
    } 
}
