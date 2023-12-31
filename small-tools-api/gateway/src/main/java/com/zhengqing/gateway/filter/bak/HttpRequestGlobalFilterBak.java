//package com.zhengqing.gateway.filter;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.StandardCharsets;
//
///**
// * <p>
// * 全局过滤器 - 拦截http请求做逻辑处理
// * </p>
// *
// * @author zhengqingya
// * @description 这里主要解决post/put请求获取不到Body内容问题
// * @date 2021/1/3 18:38
// */
//@Slf4j
////@Component
////@Order(2)
//public class HttpRequestGlobalFilter implements GlobalFilter {
//
//    /**
//     * 过滤器
//     */
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        String requestUrl = request.getPath().toString();
//        String requestMethod = request.getMethodValue();
//        if (HttpMethod.POST.toString().equals(requestMethod) || HttpMethod.PUT.toString().equals(requestMethod)) {
//            return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
//                byte[] bytes = new byte[dataBuffer.readableByteCount()];
//                dataBuffer.read(bytes);
//                String postRequestBodyStr = new String(bytes, StandardCharsets.UTF_8);
//                if (requestUrl.contains("/web/api/file")) {
//                    log.debug("\n 请求url:`{}` \n 请求类型：{} \n 文件上传", requestUrl, requestMethod);
//                } else {
//                    log.debug("\n 请求url:`{}` \n 请求类型：{} \n 请求参数：{}", requestUrl, requestMethod, postRequestBodyStr);
//                }
//                exchange.getAttributes().put("POST_BODY", postRequestBodyStr);
//                DataBufferUtils.release(dataBuffer);
//                Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
//                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
//                    return Mono.just(buffer);
//                });
//                // 下面的将请求体再次封装写回到request里，传到下一级，否则，由于请求体已被消费，后续的服务将取不到值
//                ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
//                    @Override
//                    public Flux<DataBuffer> getBody() {
//                        return cachedFlux;
//                    }
//                };
//                // 封装request，传给下一级
//                return chain.filter(exchange.mutate().request(mutatedRequest).build());
//            });
//        } else if (HttpMethod.GET.toString().equals(requestMethod)
//                || HttpMethod.DELETE.toString().equals(requestMethod)) {
//            MultiValueMap<String, String> getRequestParams = request.getQueryParams();
//            log.debug("\n 请求url:`{}` \n 请求类型：{} \n 请求参数：{}", requestUrl, requestMethod, getRequestParams);
//            return chain.filter(exchange);
//        }
//        return chain.filter(exchange);
//    }
//
//}
