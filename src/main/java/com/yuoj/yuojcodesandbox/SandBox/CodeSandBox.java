package com.yuoj.yuojcodesandbox.SandBox;
/**
 * <p>Project: yuoj-backend
 * <p>Powered by Lantz On 2025/2/7
 *
 * @author Lantz
 * @version 1.0
 * @Description CodeSandBox
 * @since 1.8
 */


import com.yuoj.yuojcodesandbox.model.ExecuteCodeRequest;
import com.yuoj.yuojcodesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandBox {
    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
