package com.yuoj.yuojcodesandbox.model;

import lombok.Data;

/**
 * <p>Project: yuoj-backend
 * <p>Powered by Lantz On 2025/1/15
 *
 * @author Lantz
 * @version 1.0
 * @Description JudgeCase
 * @since 1.8
 */

/**
 * 判题信息
 */
@Data
public class JudgeInfo {

    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 消耗时间（ms)
     */
    private Long time;

    /**
     * 消耗内存 (kb)
     */
    private Long memory;

}
