package com.yuoj.yuojcodesandbox.model;

import lombok.Data;

/**
 * <p>Project: yuoj-code-sandbox
 * <p>Powered by Lantz On 2025/2/15
 *
 * @author Lantz
 * @version 1.0
 * @Description ExecuteMessage
 * @since 1.8
 */

/**
 * 进程执行信息
 */
@Data
public class ExecuteMessage {

    private Integer exitValue;

    private String message;

    private String errorMessage;

    private Long time;

    private Long memory;
}
