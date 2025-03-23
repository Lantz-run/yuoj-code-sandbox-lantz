package com.yuoj.yuojcodesandbox.SandBox.CppSandbox;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.yuoj.yuojcodesandbox.SandBox.CodeSandBox;
import com.yuoj.yuojcodesandbox.model.ExecuteCodeRequest;
import com.yuoj.yuojcodesandbox.model.ExecuteCodeResponse;
import com.yuoj.yuojcodesandbox.model.ExecuteMessage;
import com.yuoj.yuojcodesandbox.model.JudgeInfo;
import com.yuoj.yuojcodesandbox.utils.ProcessUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Project: yuoj-code-sandbox
 * <p>Powered by Lantz On 2025/2/24
 *
 * @author Lantz
 * @version 1.0
 * @Description JavaCodeSandboxTemplate
 * @since 1.8
 */
@Component
@Slf4j
public abstract class CppCodeSandboxTemplate implements CodeSandBox {

    private static final String GLOBAL_CODE_DIR_NAME = "tmpCode";

    private static final String GLOBAL_CPP_CLASS_NAME = "Main.cpp";

    private static final String GLOBAL_CPP_EXE_NAME = "Main";

    private static final long TIME_OUT = 5000L;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
//        System.setSecurityManager(new MySecurityManager());

        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();

        // 1. 把用户的代码保存为文件
        File userCodeFile = saveCodeToFile(code);

        // 2. 编译代码，得到 exe 文件
        ExecuteMessage compileFileExecuteMessage = compileFile(userCodeFile);
        System.out.println(compileFileExecuteMessage);

        // 3. 执行代码，得到输出结果
        List<ExecuteMessage> executeMessageList = runFile(userCodeFile, inputList);

        // 4. 收集整理输出结果
        ExecuteCodeResponse outputResponse = getOutputResponse(executeMessageList);

        // 5. 文件清理
        boolean b = deleteFile(userCodeFile);
        if (!b) {
            log.error("deleteFile error, userCodeFilePath = {}", userCodeFile.getAbsolutePath());
        }
        return outputResponse;
    }

    /**
     * 1. 把用户的代码保存为文件
     *
     * @param code 用户代码
     * @return
     */
    public File saveCodeToFile(String code) {
        String userDir = System.getProperty("user.dir");
        String globalCodePathName = userDir + File.separator + GLOBAL_CODE_DIR_NAME;
        // 判断全局代码目录是否存在，没有则新建
        if (!FileUtil.exist(globalCodePathName)) {
            FileUtil.mkdir(globalCodePathName);
        }
        // 把用户的代码隔离存放
        String userCodeParentPath = globalCodePathName + File.separator + UUID.randomUUID();
        // 实际的路径
        String userCodePath = userCodeParentPath + File.separator + GLOBAL_CPP_CLASS_NAME;
        File userCodeFile = FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);
        return userCodeFile;
    }

    /**
     * 2. 编译代码，得到 Main 文件
     *
     * @param userCodeFile
     * @return
     */
    // g++ -std=c++17 Main.cpp -o Main
    public ExecuteMessage compileFile(File userCodeFile) {
        // 指定输出文件路径（与源文件路径相同）
//        File outputFile = new File(userCodeFile.getParentFile(), "Main.exe");
        File outputFile = new File(userCodeFile.getParentFile(), "Main");

        // 处理路径中的空格和特殊字符
        String compileCmd = String.format("g++ -std=c++17 %s -o %s", userCodeFile.getAbsolutePath(), outputFile.getAbsolutePath());
        try {
            Process compileProcess = Runtime.getRuntime().exec(compileCmd);
            ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(compileProcess, "编译");

            if (executeMessage.getExitValue() != 0){
                throw new RuntimeException("编译错误：" + executeMessage.getMessage());
            }
            return executeMessage;
        } catch (IOException e) {
//            return getErrorResponse(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 3. 执行文件，得到输出列表结果
     *
     * @param userCodeFile
     * @param inputList
     * @return
     */
    public List<ExecuteMessage> runFile(File userCodeFile, List<String> inputList) {

        List<ExecuteMessage> executeMessageList = new ArrayList<>();
        // .\Main.exe
        // 1. 获取可执行文件路径
        File parentDir = userCodeFile.getParentFile();
        File exeFile = new File(parentDir, "Main.exe");
        if (!exeFile.exists()) {
            throw new RuntimeException("可执行文件未生成");
        }

        // 2. 构造运行命令（处理参数和空格）
//        String inputArgs = String.join(" ", inputList);
        for (String inputArgs : inputList) {
            String runCmd = parentDir + File.separator + "Main.exe";
            try {
                Process runProcess = Runtime.getRuntime().exec(runCmd);

                // 关键修改：向进程写入输入数据
                try (BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(runProcess.getOutputStream()))) {
                    // 将输入参数格式化为程序所需格式（如换行分隔）
                    String formattedInput = inputArgs.replace(" ", "\n") + "\n";
                    writer.write(formattedInput);
                    writer.flush(); // 必须刷新缓冲区
                }

                // 超时控制
                new Thread(() -> {
                    try {
                        Thread.sleep(TIME_OUT);
                        runProcess.destroy();
                        System.out.println("超时中断");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }).start();

                // 读取输出
                ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(runProcess, "运行");
                System.out.println(executeMessage);
                executeMessageList.add(executeMessage);
            } catch (IOException e) {
                throw new RuntimeException("执行异常", e);
            }
        }

        return executeMessageList;
    }

    /**
     * 4. 收集整理输出结果
     *
     * @param executeMessageList
     * @return
     */
    public ExecuteCodeResponse getOutputResponse(List<ExecuteMessage> executeMessageList) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        List<String> outputList = new ArrayList<>();
        // 取用时最大值，便于判断是否超时
        long maxTime = 0;
        long maxMemory = 0;
        for (ExecuteMessage executeMessage : executeMessageList) {
            String errorMessage = executeMessage.getErrorMessage();
            if (StrUtil.isNotBlank(errorMessage)) {
                executeCodeResponse.setMessage(errorMessage);
                // 执行中存在错误
                executeCodeResponse.setStatus(3);
                break;
            }
            outputList.add(executeMessage.getMessage());
            Long time = executeMessage.getTime();
            Long memory = executeMessage.getMemory();
            if (time != null) {
                maxTime = Math.max(maxTime, time);
            }
            if (memory != null) {
                maxMemory = Math.max(maxMemory, memory);
            }
        }
        // 正常运行完成
        if (outputList.size() == executeMessageList.size()) {
            executeCodeResponse.setStatus(1);
        }
        executeCodeResponse.setOutputList(outputList);
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTime(maxTime);
        // 要借助第三方库来获取内存占用，非常麻烦，此处不做实现
         judgeInfo.setMemory(maxMemory);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }

    /**
     * 5. 文件清理
     *
     * @param userCodeFile
     * @return
     */
    public boolean deleteFile(File userCodeFile) {
        if (userCodeFile.getParentFile() != null) {
            String userCodeParentPath = userCodeFile.getParentFile().getAbsolutePath();
            boolean del = FileUtil.del(userCodeParentPath);
            System.out.println("删除" + (del ? "成功" : "失败"));
            return del;
        }
        return true;
    }


    /**
     * 6. 获取错误响应
     *
     * @param e
     * @return
     */
    private ExecuteCodeResponse getErrorResponse(Throwable e) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(new ArrayList<>());
        executeCodeResponse.setMessage(e.getMessage());
        // 表示代码沙箱错误
        executeCodeResponse.setStatus(2);
        executeCodeResponse.setJudgeInfo(new JudgeInfo());
        return executeCodeResponse;
    }
}
