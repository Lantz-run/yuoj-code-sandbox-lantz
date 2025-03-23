package com.yuoj.yuojcodesandbox.unsafe;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Project: yuoj-code-sandbox
 * <p>Powered by Lantz On 2025/2/16
 *
 * @author Lantz
 * @version 1.0
 * @Description MemoryError
 * @since 1.8
 */

/**
 * 无限占用空间（浪费内存）
 */
public class MemoryError {
    public static void main(String[] args) {
        List<byte[]> bytes = new ArrayList<byte[]>();
        while (true) {
            bytes.add(new byte[10000]);
        }
    }
}
