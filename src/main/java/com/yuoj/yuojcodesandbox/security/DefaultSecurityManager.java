package com.yuoj.yuojcodesandbox.security;

/**
 * <p>Project: yuoj-code-sandbox
 * <p>Powered by Lantz On 2025/2/18
 *
 * @author Lantz
 * @version 1.0
 * @Description DefaultSecurityManager
 * @since 1.8
 */

import java.security.Permission;

/**
 * 默认安全管理器
 */
public class DefaultSecurityManager extends SecurityManager{

    // 检查所有权限
    @Override
    public void checkPermission(Permission perm) {
        System.out.println("默认不做任何限制");
        System.out.println(perm);
//        super.checkPermission(perm);
    }
}
