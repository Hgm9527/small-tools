package com.zhengqing.common.web.handler;

import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.model.vo.ApiResult;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 封装返回值处理
 * </p>
 *
 * @author zhengqingya
 * @description {@link ApiResult}
 * @date 2020/8/1 18:40
 */
public class MyHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler returnValueHandler;

    public MyHandlerMethodReturnValueHandler(HandlerMethodReturnValueHandler returnValueHandler) {
        this.returnValueHandler = returnValueHandler;
    }

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return this.returnValueHandler.supportsReturnType(methodParameter);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        // 判断外层是否由ApiResult包裹
        if (returnValue instanceof ApiResult) {
            this.returnValueHandler.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
            return;
        }
        // 判断该api是否需要是否处理返回值
        String servletPath = nativeRequest.getServletPath();
        boolean ifHandleReturnValue = true;
        for (String api : AppConstant.RETURN_VALUE_HANDLER_EXCLUDE_API_LIST) {
            if (servletPath.contains(api)) {
                ifHandleReturnValue = false;
                break;
            }
        }
        this.returnValueHandler.handleReturnValue(ifHandleReturnValue ? ApiResult.ok(returnValue) : returnValue, returnType, mavContainer, webRequest);
    }

}