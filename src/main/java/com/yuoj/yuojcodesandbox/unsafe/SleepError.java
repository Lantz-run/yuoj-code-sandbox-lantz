package com.yuoj.yuojcodesandbox.unsafe;

/**
 * <p>Project: yuoj-code-sandbox
 * <p>Powered by Lantz On 2025/2/16
 *
 * @author Lantz
 * @version 1.0
 * @Description SleepError
 * @since 1.8
 */

/**
 * 无限睡眠（阻塞程序执行）
 */
public class SleepError {

    public static void main(String[] args) throws InterruptedException {
        long ONE_HOUR = 60 * 60 * 1000L;
        Thread.sleep(ONE_HOUR);
        System.out.println("睡完了");
    }
}
