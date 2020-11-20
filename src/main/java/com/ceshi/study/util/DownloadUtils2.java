package com.ceshi.study.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DownloadUtils
 * @Author: shenyafei
 * @Date: 2020/11/16
 * @Desc
 **/
public class DownloadUtils2 {


    /**
     * 该目录不存在
     */
    public static final String DIR_NOT_EXIST = "该目录不存在";

    /**
     * "该目录下没有文件
     */
    public static final String DIR_CONTAINS_NO_FILE = "该目录下没有文件";

    /**
     * FTP地址
     **/
    private final String ftpAddress = "106.14.67.195";
    /**
     * FTP端口
     **/
    private final int ftpPort = 21;
    /**
     * FTP用户名
     **/
    private final String ftpUsername = "root";
    /**
     * FTP密码
     **/
    private final String ftpPassword = "Facon2020#";
    /**
     * FTP基础目录
     **/
    private final String basePath = "/";

    /**
     * 本地字符编码
     **/
    private static String localCharset = "GBK";

    /**
     * FTP协议里面，规定文件名编码为iso-8859-1
     **/
    private static String serverCharset = "ISO-8859-1";

    /**
     * UTF-8字符编码
     **/
    private static final String CHARSET_UTF8 = "UTF-8";

    /**
     * OPTS UTF8字符串常量
     **/
    private static final String OPTS_UTF8 = "OPTS UTF8";

    /**
     * 设置缓冲区大小4M
     **/
    private static final int BUFFER_SIZE = 1024 * 1024 * 4;

    /**
     * FTPClient对象
     **/
    private static FTPClient ftpClient = null;


    /**
     * 下载该目录下所有文件到本地
     *
     * @param ftpPath  FTP服务器上的相对路径，例如：test/123
     * @param savePath 保存文件到本地的路径，例如：D:/test
     * @return 成功返回true，否则返回false
     */
    public boolean downloadFiles(String ftpPath, String savePath) {
        // 登录
        login(ftpAddress, ftpPort, ftpUsername, ftpPassword);
        if (ftpClient != null) {
            try {
                String path = changeEncoding(basePath + ftpPath);
                // 判断是否存在该目录
                if (!ftpClient.changeWorkingDirectory(path)) {
                    System.out.println(basePath + ftpPath + DIR_NOT_EXIST);
                    return Boolean.FALSE;
                }
                ftpClient.enterLocalPassiveMode();  // 设置被动模式，开通一个端口来传输数据
                String[] fs = ftpClient.listNames();
                // 判断该目录下是否有文件
                if (fs == null || fs.length == 0) {
                    System.out.println(basePath + ftpPath + DIR_CONTAINS_NO_FILE);
                    return Boolean.FALSE;
                }
                for (String ff : fs) {
                    String ftpName = new String(ff.getBytes(serverCharset), localCharset);
                    File file = new File(savePath + '/' + ftpName);
                    try (OutputStream os = new FileOutputStream(file)) {
                        ftpClient.retrieveFile(ff, os);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeConnect();
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 连接FTP服务器
     *
     * @param address  地址，如：127.0.0.1
     * @param port     端口，如：21
     * @param username 用户名，如：root
     * @param password 密码，如：root
     */
    private void login(String address, int port, String username, String password) {
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(address, port);
            ftpClient.login(username, password);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //限制缓冲区大小
            ftpClient.setBufferSize(BUFFER_SIZE);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                closeConnect();
                System.out.println("FTP服务器连接失败");
            }
        } catch (Exception e) {
            //logger.error("FTP登录失败", e);
            System.out.println("FTP登录失败");
            e.printStackTrace();
        }
    }


    /**
     * FTP服务器路径编码转换
     *
     * @param ftpPath FTP服务器路径
     * @return String
     */
    private static String changeEncoding(String ftpPath) {
        String directory = null;
        try {
            if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(OPTS_UTF8, "ON"))) {
                localCharset = CHARSET_UTF8;
            }
            directory = new String(ftpPath.getBytes(localCharset), serverCharset);
        } catch (Exception e) {
            //logger.error("路径编码转换失败", e);
            System.out.println("路径编码转换失败");
            e.printStackTrace();
        }
        return directory;
    }

    /**
     * 关闭FTP连接
     */
    private void closeConnect() {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (Exception e) {
                //logger.error("关闭FTP连接失败", e);
                System.out.println("路径编码转换失败");
                e.printStackTrace();
            }
        }
    }

    /**
     * 检查指定目录下是否含有指定文件
     *
     * @param ftpPath  FTP服务器文件相对路径，例如：test/123
     * @param fileName 要下载的文件名，例如：test.txt
     * @return 成功返回true，否则返回false
     */
    public boolean checkFileInFtp(String ftpPath, String fileName) {
        // 登录
        login(ftpAddress, ftpPort, ftpUsername, ftpPassword);
        if (ftpClient != null) {
            try {
                String path = changeEncoding(basePath + ftpPath);
                // 判断是否存在该目录
                if (!ftpClient.changeWorkingDirectory(path)) {
                    System.out.println(basePath + ftpPath + DIR_NOT_EXIST);
                    return Boolean.FALSE;
                }
                ftpClient.enterLocalPassiveMode();  // 设置被动模式，开通一个端口来传输数据
                String[] fs = ftpClient.listNames();
                // 判断该目录下是否有文件
                if (fs == null || fs.length == 0) {
                    System.out.println(basePath + ftpPath + DIR_CONTAINS_NO_FILE);
                    return Boolean.FALSE;
                }
                for (String ff : fs) {
                    String ftpName = new String(ff.getBytes(serverCharset), localCharset);
                    if (ftpName.equals(fileName)) {
                        return Boolean.TRUE;
                    }
                }
            } catch (Exception e) {
                //logger.error("请求出错", e);
                System.out.println("请求出错");
                e.printStackTrace();
            } finally {
                closeConnect();
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 下载该目录下所有文件到本地 根据实际需要修改执行逻辑
     *
     * @param ftpPath  FTP服务器上的相对路径，例如：test/123
     * @param savePath 保存文件到本地的路径，例如：D:/test
     * @return 成功返回true，否则返回false
     */
    public Map<String, Object> downLoadTableFile(String ftpPath, String savePath) {
        // 登录
        login(ftpAddress, ftpPort, ftpUsername, ftpPassword);
        Map<String, Object> resultMap = new HashMap<>();
        if (ftpClient != null) {
            try {
                String path = changeEncoding(basePath + "/" + ftpPath);
                // 判断是否存在该目录
                if (!ftpClient.changeWorkingDirectory(path)) {
                    System.out.println(basePath + "/" + ftpPath + DIR_NOT_EXIST);
                    resultMap.put("result", false);
                    return resultMap;
                }
                ftpClient.enterLocalPassiveMode();  // 设置被动模式，开通一个端口来传输数据
                String[] fs = ftpClient.listNames();
                // 判断该目录下是否有文件
                if (fs == null || fs.length == 0) {
                    System.out.println(basePath + "/" + ftpPath + DIR_CONTAINS_NO_FILE);
                    resultMap.put("result", false);
                    return resultMap;
                }
                List<String> tableFileNameList = new ArrayList<>();
                //根据表名创建文件夹
                String tableDirName = savePath + "/" + ftpPath;
                File tableDirs=new File(tableDirName);
                if(!tableDirs.exists()){
                    tableDirs.mkdirs();
                }
                for (String ff : fs) {
                    String ftpName = new String(ff.getBytes(serverCharset), localCharset);
                    File file = new File(tableDirName + "/" + ftpName);
                    //存储文件名导入时使用
                    tableFileNameList.add(tableDirName + "/" + ftpName);
                    try (OutputStream os = new FileOutputStream(file)) {
                        ftpClient.retrieveFile(ff, os);
                    } catch (Exception e) {
                        //logger.error(e.getMessage(), e);
                        System.out.println(e.getMessage());
                    }
                }
                resultMap.put("fileNameList", tableFileNameList);
                resultMap.put("result", true);
                return resultMap;
            } catch (Exception e) {
                //logger.error("下载文件失败", e);
                System.out.println(e.getMessage());
                e.printStackTrace();
            } finally {
                closeConnect();
            }
        }
        resultMap.put("result", false);
        return resultMap;
    }
    public static void main(String[] args){
        DownloadUtils2 downloadUtils = new DownloadUtils2();
        Map<String, Object> result = downloadUtils.downLoadTableFile("AMH", "D:/test");
    }
}
