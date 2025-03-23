package com.yuoj.yuojcodesandbox.security;

import cn.hutool.core.io.FileUtil;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <p>Project: yuoj-code-sandbox
 * <p>Powered by Lantz On 2025/2/18
 *
 * @author Lantz
 * @version 1.0
 * @Description TestSecurityManager
 * @since 1.8
 */
public class TestSecurityManager {

    public static void main(String[] args) {
        System.setSecurityManager(new DenySecurityManager());
        List<String> list = FileUtil.readLines(
                "D:\\StarProjects\\yuoj-code-sandbox\\src\\main\\resources\\application.yml", StandardCharsets.UTF_8);
        System.out.println(list);
    }
}
