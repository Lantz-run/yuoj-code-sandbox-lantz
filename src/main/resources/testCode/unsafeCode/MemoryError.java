/**
 * <p>Project: yuoj-code-sandbox
 * <p>Powered by Lantz On 2025/2/16
 *
 * @author Lantz
 * @version 1.0
 * @Description MemoryError
 * @since 1.8
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 无限占用空间（浪费内存）
 */
public class Main {
    public static void main(String[] args) {
        List<byte[]> bytes = new ArrayList<>();
        while (true) {
            bytes.add(new byte[10000]);
        }
    }
}
