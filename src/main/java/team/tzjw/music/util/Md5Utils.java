package team.tzjw.music.util;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Md5Utils {
    public static String encryption(String username,String password){
        Md5Hash md5Hash = new Md5Hash(password,username,1000);
        return md5Hash.toString();
    }
    public static void main(String[] args) {
        String pwd= encryption("admin","123");
        System.out.println(pwd);
    }
}
