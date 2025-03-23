package com.yuoj.yuojcodesandbox.controller;

import com.yuoj.yuojcodesandbox.SandBox.JavaSandbox.JavaDockerCodeSandbox;
import com.yuoj.yuojcodesandbox.SandBox.JavaSandbox.JavaNativeCodeSandBox;
import com.yuoj.yuojcodesandbox.SandBox.CppSandbox.CppDockerCodeSandBox;
import com.yuoj.yuojcodesandbox.SandBox.CppSandbox.CppNativeCodeSandbox;
import com.yuoj.yuojcodesandbox.model.ExecuteCodeRequest;
import com.yuoj.yuojcodesandbox.model.ExecuteCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Project: yuoj-code-sandbox
 * <p>Powered by Lantz On 2025/2/11
 *
 * @author Lantz
 * @version 1.0
 * @Description MainController
 * @since 1.8
 */
@RestController("/")
public class MainController {

    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Resource
    private JavaNativeCodeSandBox javaNativeCodeSandbox;

    @Resource
    private JavaDockerCodeSandbox javaDockerCodeSandBox;

    @Resource
    private CppNativeCodeSandbox cppNativeCodeSandbox;
    @Resource
    private CppDockerCodeSandBox cppDockerCodeSandBox;

    @GetMapping("/health")
    public String healthCheck(){
        return "ok";
    }

    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    @PostMapping("/executeCode")
    ExecuteCodeResponse executeCode(@RequestBody ExecuteCodeRequest executeCodeRequest, HttpServletRequest
            request, HttpServletResponse response){
        // 基本的认证
        String authHeader = request.getHeader(AUTH_REQUEST_HEADER);
        if (!AUTH_REQUEST_SECRET.equals(authHeader)) {
            response.setStatus(403);
            return null;
        }

        if (executeCodeRequest == null){
            throw new RuntimeException("请求参数为空");
        }
        String language = executeCodeRequest.getLanguage();
        if ("java".equals(language)){
            return javaNativeCodeSandbox.executeCode(executeCodeRequest);
        } else if ("cpp".equals(language)) {
            return cppNativeCodeSandbox.executeCode(executeCodeRequest);
        }
//        if ("java".equals(language)){
//            return javaDockerCodeSandBox.executeCode(executeCodeRequest);
//        } else if ("cpp".equals(language)) {
//            return cppDockerCodeSandBox.executeCode(executeCodeRequest);
//        }
        return null;
    }
}
