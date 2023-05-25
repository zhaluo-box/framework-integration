package org.framework.integration.example.web.mvc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.SimpleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created  on 2023/5/23 15:15:20
 *
 * @author zl
 */
@Slf4j
public class LogInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("请求头 :  {}", request.getHeaderNames().nextElement());
        log.info("请求参数 :  {}", request.getParameterMap());

        LogContextHolder.setData("header", request.getHeaderNames().nextElement());
        LogContextHolder.setData("params", request.getParameterMap());
        log.info("preHandle-log-context : {}", LogContextHolder.getLocalMap());

        return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("后置处理");
        log.info("postHandle : {}", LogContextHolder.getLocalMap());
        AsyncHandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("同步线程完成");
        log.info("afterCompletion : {}", LogContextHolder.getLocalMap());
        System.out.println("afterConcurrentHandlingStarted remove start!");
        LogContextHolder.remove();
        System.out.println("afterConcurrentHandlingStarted remove end!");
        log.info("afterCompletion 2 : {}", LogContextHolder.getLocalMap());
        AsyncHandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("afterConcurrentHandlingStarted : {}", LogContextHolder.getLocalMap());
        System.out.println("afterConcurrentHandlingStarted remove start!");
        LogContextHolder.remove();
        System.out.println("afterConcurrentHandlingStarted remove end!");
    }

    public Map<String, String> extractRequestHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        var result = new HashMap<String, String>();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            result.put(key, value);
        }
        return result;
    }

    public Map<String, String> extractResponseHeaders(HttpServletResponse response) {
        var headerNames = response.getHeaderNames();
        var responseHeaders = new HashMap<String, String>(headerNames.size());
        headerNames.forEach(headerName -> responseHeaders.put(headerName, response.getHeader(headerName)));
        return responseHeaders;
    }

    public static class JsonUtil {

        private static final ObjectMapper objectMapper = new ObjectMapper();

        static {
            // 对象没有属性时，也要序列化
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        }

        /**
         * 将Json转换指定类名的Java对像
         *
         * @param json      json字符串
         * @param className 类名
         * @return 指定类名的对像
         */
        public static Object toObject(String json, String className) {
            try {
                SimpleType javaType = SimpleType.constructUnsafe(Class.forName(className));
                return objectMapper.readValue(json, javaType);
            } catch (Exception e) {
                log.error("将字符串：{}转为对象：{}时出错。", json, className);
                throw new RuntimeException(e);
            }
        }

        /**
         * 将Json转换为指定类的Java对像
         *
         * @param json  json字符串
         * @param clazz 类
         * @param <T>   指定类
         * @return 指定类的对像
         */
        public static <T> T toObject(String json, Class<T> clazz) {
            Assert.hasLength(json, "要转对象的JSON字符串不能为空。");
            try {
                return objectMapper.readValue(json, clazz);
            } catch (IOException e) {
                log.error("将字符串：{}转为对象：{}时出错。", json, clazz);
                throw new RuntimeException(e);
            }
        }

        /**
         * 将Java对像转换为Json
         *
         * @param object Java对像
         * @return json
         */
        public static String toJSON(Object object) {
            Assert.notNull(object, "要转JSON的对像不能为空。");

            try {
                return objectMapper.writeValueAsString(object);
            } catch (Exception e) {
                log.error("将对象：{}转为JSON字符串时出错。", object);
                throw new RuntimeException(e);
            }
        }

        /**
         * 将json转换为Java对像列表
         *
         * @param json         json
         * @param elementClass 列表中的java类型
         * @param <T>          java类型
         * @return java对像列表
         */
        public static <T> List<T> toList(String json, Class<T> elementClass) {
            CollectionLikeType type = objectMapper.getTypeFactory().constructCollectionLikeType(ArrayList.class, elementClass);
            try {
                return objectMapper.readValue(json, type);
            } catch (Exception e) {
                log.error("将JSON:{}转为List<{}>时出错。", json, elementClass);
                throw new RuntimeException(e);
            }
        }

        public static <T> Set<T> toSet(String json, Class<T> elementClass) {
            CollectionLikeType type = objectMapper.getTypeFactory().constructCollectionLikeType(HashSet.class, elementClass);
            try {
                return objectMapper.readValue(json, type);
            } catch (Exception e) {
                log.error("将JSON:{}转为Set<{}>时出错。", json, elementClass);
                throw new RuntimeException(e);
            }
        }

    }
}
