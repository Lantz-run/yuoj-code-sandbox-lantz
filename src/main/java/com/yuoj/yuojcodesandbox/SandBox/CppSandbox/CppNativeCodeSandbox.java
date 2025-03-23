package com.yuoj.yuojcodesandbox.SandBox.CppSandbox;

import com.yuoj.yuojcodesandbox.model.ExecuteCodeRequest;
import com.yuoj.yuojcodesandbox.model.ExecuteCodeResponse;
import org.springframework.stereotype.Component;

/**
 * <p>Project: yuoj-code-sandbox
 * <p>Powered by Lantz On 2025/2/24
 *
 * @author Lantz
 * @version 1.0
 * @Description JavaNativeCodeSandbox
 * @since 1.8
 */

/**
 * Java 原生代码沙箱实现（直接复用模板方法）
 */

@Component
public class CppNativeCodeSandbox extends CppCodeSandboxTemplate {


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        return super.executeCode(executeCodeRequest);
    }
}
