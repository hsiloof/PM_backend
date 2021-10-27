package com.jinax.pm_backend.Utils;

/**
 * @author : chara
 */
public class UserRoleConverter {
    public static String getRole(int role){
        if (role == 0) {
            return "admin";
        }
        return "user";
    }
}
