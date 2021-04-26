/*
 * @(#)RequestWithResultMethodProcessor.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import ${packagePrefix}.common.annotation.WithApiResult;
import ${packagePrefix}.common.model.PagedDTO;
import ${packagePrefix}.common.model.PagedVO;
import ${packagePrefix}.common.model.ResultDO;
import ${packagePrefix}.common.utils.TraceIdUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * RequestWithResultMethodProcessor
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: RequestWithResultMethodProcessor.java ${currentTime} Exp $
 */
@Component
@Slf4j
public class RequestWithResultMethodProcessor implements HandlerMethodReturnValueHandler, InitializingBean {

	@Autowired
    private RequestMappingHandlerAdapter adapter;

    private RequestResponseBodyMethodProcessor processor;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<HandlerMethodReturnValueHandler> unmodifiableList = adapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> list = new ArrayList<>(unmodifiableList.size());
        for (HandlerMethodReturnValueHandler returnValueHandler : unmodifiableList) {
            if (returnValueHandler instanceof RequestResponseBodyMethodProcessor) {
                this.processor = (RequestResponseBodyMethodProcessor) returnValueHandler;
                list.add(this);
            } else {
                list.add(returnValueHandler);
            }
        }
        adapter.setReturnValueHandlers(list);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
            throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {

        if (supportWithResult(returnType)) {
            WithApiResult resultAnno = returnType.getContainingClass().getAnnotation(WithApiResult.class);
            if (resultAnno == null) {
                resultAnno = returnType.getMethodAnnotation(WithApiResult.class);
            }

            returnValue = convertValue(returnValue, resultAnno.result());
        }

        processor.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }

    private Object convertValue(Object returnValue, Class<ResultDO> resultType) {
        ResultDO result = null;
        try {
            result = resultType.newInstance();
        } catch (Exception e) {
            log.error("error occured", e);
        }
        result.setStatus(true);
        result.setTraceId(TraceIdUtil.getTraceId());
        if (returnValue != null && returnValue instanceof PagedDTO) {
            PagedDTO paged = (PagedDTO) returnValue;
            result.setEntry(paged.getList());
            result.setTotalRecordSize(paged.getTotalRecord());
            result.setHasNext(paged.getTotalPage() > paged.getPageNum());
        } else if (returnValue != null && returnValue instanceof PagedVO) {
            PagedVO paged = (PagedVO) returnValue;
            result.setEntry(paged.getList());
            result.setTotalRecordSize(paged.getTotalRecord());
            result.setHasNext(paged.getTotalPage() > paged.getPageNum());
        } else {
            result.setEntry(returnValue);
        }

        return result;
    }

    private boolean supportWithResult(MethodParameter returnType) {
        boolean hasAnnotation =
                AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), WithApiResult.class) ||
                        returnType.hasMethodAnnotation(WithApiResult.class);
        if (hasAnnotation) {
            return true;
        }

        return false;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return processor.supportsReturnType(returnType);
    }

}
