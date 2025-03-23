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
 * 禁用所有安全管理器
 */
public class DenySecurityManager extends SecurityManager{

    // 检查所有权限
    @Override
    public void checkPermission(Permission perm) {
        throw new SecurityException("权限异常：" + perm.toString());
    }


}
