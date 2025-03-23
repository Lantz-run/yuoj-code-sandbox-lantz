import java.io.*;
/**
 * <p>Project: yuoj-code-sandbox
 * <p>Powered by Lantz On 2025/2/17
 *
 * @author Lantz
 * @version 1.0
 * @Description ReadFileError
 * @since 1.8
 */

/**
 * 运行其他程序（比如危险木马）
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        String userDir = System.getProperty("user.dir");
        String filePath = userDir + File.separator + "src/main/resources/木马程序.bat";
        Process process = Runtime.getRuntime().exec(filePath);
        process.waitFor();
        // 分批获取进程的正常输出
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        // 逐行读取
        String compileOutputLine;
        while ((compileOutputLine = bufferedReader.readLine()) != null) {
            System.out.println(compileOutputLine);
        }
        System.out.println("执行异常程序成功");
    }
}