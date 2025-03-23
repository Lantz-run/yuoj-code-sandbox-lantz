package com.yuoj.yuojcodesandbox.SandBox.JavaSandbox;

import com.yuoj.yuojcodesandbox.model.ExecuteCodeRequest;
import com.yuoj.yuojcodesandbox.model.ExecuteCodeResponse;
import org.springframework.stereotype.Component;

/**
 * <p>Project: yuoj-code-sandbox
 * <p>Powered by Lantz On 2025/2/14
 *
 * @author Lantz
 * @version 1.0
 * @Description JavaNativeCodeSandBox
 * @since 1.8
 */
@Component
public class JavaNativeCodeSandBox extends JavaCodeSandboxTemplate {


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        return super.executeCode(executeCodeRequest);
    }
}
