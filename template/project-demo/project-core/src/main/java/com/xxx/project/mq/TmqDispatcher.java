/*
 * @(#)TmqDispatcher.java	2017年11月16日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.mq;

import static com.github.javaclub.sword.core.B.asList;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.javaclub.sword.Constants;
import com.github.javaclub.sword.algorithm.MD5;
import com.github.javaclub.sword.cache.CacheManager;
import com.github.javaclub.sword.core.Entry;
import com.github.javaclub.sword.domain.ContextParam;
import com.github.javaclub.sword.core.Strings;
import com.shangou.tmq.client.customer.message.MessageListener;
import com.shangou.tmq.client.customer.message.SubMessage;
import com.shangou.tmq.client.customer.message.SubscribeStatus;

/**
 * TmqDispatcher 
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: TmqDispatcher.java 2017年11月16日 17:53:07 Exp $
 */
public abstract class TmqDispatcher<T> implements MessageListener {
	
	private static final Logger log = LoggerFactory.getLogger("mqLogger");
	private static final Logger timeLog = LoggerFactory.getLogger("timeCostLogger");
	private static final Logger appLog = LoggerFactory.getLogger(TmqDispatcher.class);
	
	@Autowired
	protected CacheManager<String, Object> kAMemberCacheManager;
	
	/**
	 * 将tmq消息转成业务数据对象
	 */
	public abstract ContextParam<T> parse(SubMessage subMessage);
	
	/**
	 * 分发到不同的消息处理器
	 */
	public abstract Entry<String, TmqProcessor<T>>[] dispatchedProcessor();

	@Override
	public SubscribeStatus consume(SubMessage subMessage) {
		long start = System.currentTimeMillis();
		final ContextParam<T> context = this.parse(subMessage);
		log.warn("msgId={}\t topic={}\t msgData={}", subMessage.getMsgid(), subMessage.getTopic() + "|" + subMessage.getSubTopic(), context);
		if(null == context || null == context.getTarget()) {
			return SubscribeStatus.SUCCESS;
		}
		SubscribeStatus status = SubscribeStatus.FAILED;
		final String concurencyKey = MD5.getMd5(Strings.concat(subMessage.getTopic(), 
											"|", subMessage.getSubTopic(), 
											"|", subMessage.getMsgid()));
		final String doneKey = concurencyKey + "_done";
		List<String> doneProcessors = null;
		try {
			boolean suc = kAMemberCacheManager.setNX(concurencyKey, "flag", 120L); // 120秒
			if(!suc) { // 已经在处理了
				log.warn("ConcurrentProtected\t msgId={}\t topic={}", subMessage.getMsgid(), subMessage.getTopic() + "|" + subMessage.getSubTopic());
				return SubscribeStatus.FAILED; // 等待下一次重投过来
			}
			String txt = (String) kAMemberCacheManager.get(doneKey);
			String[] doneArray = Strings.split(txt, ";");
			doneProcessors = asList(doneArray);
			
			Entry<String, TmqProcessor<T>>[] allProcessors = dispatchedProcessor();
			for (Entry<String, TmqProcessor<T>> entry : allProcessors) {
				if(doneProcessors.contains(entry.getKey())) {
					continue;
				}
				long processorCostStart = System.currentTimeMillis();
				boolean done = entry.getValue().execute(context);
				long processorCost = System.currentTimeMillis() - processorCostStart;
				timeLog.warn("{}\t costTime={}", entry.getValue().getClass().getName(), processorCost + "ms");
				if(done) {
					doneProcessors.add(entry.getKey());
				}
			}
			
			if(isAllDone(doneProcessors, allProcessors)) {
				status = SubscribeStatus.SUCCESS;
			}
			
		} catch (Exception e) {
			status = SubscribeStatus.FAILED;
			appLog.error("", e);
		} finally {
			if(null != doneProcessors) {
				kAMemberCacheManager.put(doneKey, StringUtils.join(doneProcessors, ";"), 90*86400L); //存放90天后自动失效(有啥任务需要一直重试执行90天的？)
			}
		}
		long cost = System.currentTimeMillis() - start;
		timeLog.warn("{}{}\t totalCostTime={}{}", Constants.LINE_SEPARATER, this.getClass().getName(), 
						cost + "ms", Constants.LINE_SEPARATER);
		
		log.warn("msgId={}\t topic={}\t msgData={}\t result={} | doneProcessors=>{}", subMessage.getMsgid(), 
				subMessage.getTopic() + "|" + subMessage.getSubTopic(), 
				context, status, doneProcessors);
		
		return status;
	}

	/**
	 * 判断所有配置的processor是否已经都成功执行
	 */
	boolean isAllDone(List<String> doneProcessors, Entry<String, TmqProcessor<T>>[] allProcessors) {
		for (Entry<String, TmqProcessor<T>> e : allProcessors) {
			if(!doneProcessors.contains(e.getKey())) {
				return false;
			}
		}
		return true;
	}

}
