package com.yuoj.yuojcodesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>Project: yuoj-backend
 * <p>Powered by Lantz On 2025/2/7
 *
 * @author Lantz
 * @version 1.0
 * @Description ExecuteCodeRequest
 * @since 1.8
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeRequest {

    private List<String> inputList;

    private String code;

    private String language;
}
