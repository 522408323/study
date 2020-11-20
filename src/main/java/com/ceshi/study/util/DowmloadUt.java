package com.ceshi.study.util;

import com.sshtools.j2ssh.SftpClient;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.sftp.FileAttributes;
import com.sshtools.j2ssh.sftp.SftpFile;
import com.sshtools.j2ssh.transport.ConsoleKnownHostsKeyVerification;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName: DowmloadUt
 * @Author: shenyafei
 * @Date: 2020/11/16
 * @Desc
 **/
public class DowmloadUt {

    public static void ddd(){
        SshClient client = new SshClient();
        FileOutputStream fos = null;
        try {
            ConsoleKnownHostsKeyVerification console = new ConsoleKnownHostsKeyVerification();
            client.connect("106.14.67.195", 22, new IgnoreHostKeyVerification());//IP和端口
            //设置用户名和密码
            PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
            pwd.setUsername("root");
            pwd.setPassword("Facon2020#");
            int result = client.authenticate(pwd);
            BASE64Encoder encoder = new BASE64Encoder();
            if (result == AuthenticationProtocolState.COMPLETE) {//如果连接完成
                SftpClient sftp = client.openSftpClient();
                List<SftpFile> list = sftp.ls("/root/AMH");
                for (SftpFile f : list) {
                    String name = f.getFilename();
                    System.out.println("==========start=========="+name);
                    String filename = name.split("\\.")[0];
                    String fpqqlsh = filename.split("_")[0];
                    File file = new File("D:\\" + f.getFilename());
                    fos = new FileOutputStream(file);
                    FileAttributes fa = sftp.get(f.getAbsolutePath(), fos);
                    fos.write(fa.toByteArray());
                    System.out.println("==========end=========="+name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
    public static void main(String[] args){
        ddd();
    }
}
