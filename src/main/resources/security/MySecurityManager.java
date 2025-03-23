import java.security.Permission;

/**
 * <p>Project: yuoj-code-sandbox
 * <p>Powered by Lantz On 2025/2/19
 *
 * @author Lantz
 * @version 1.0
 * @Description MySecurityManager
 * @since 1.8
 */

/**
 * 禁用所有安全管理器
 */
public class MySecurityManager extends SecurityManager{
    // 检查所有权限
    @Override
    public void checkPermission(Permission perm) {
//        super.checkPermission(perm);
    }

    @Override
    public void checkExec(String cmd) {
        throw new SecurityException("checkExec 权限异常：" + cmd);
    }

    @Override
    public void checkRead(String file) {
        System.out.println(file);
        if (file.contains("hutool")) {
            return;
        }
//        throw new SecurityException("checkRead 权限异常：" + file);
    }

    @Override
    public void checkWrite(String file) {
//        throw new SecurityException("checkWrite 权限异常：" + file);
    }

    @Override
    public void checkDelete(String file) {
//        throw new SecurityException("checkDelete 权限异常：" + file);
    }

    @Override
    public void checkConnect(String host, int port) {
//        throw new SecurityException("checkDelete 权限异常：" + host + "端口："+port);
    }
}
