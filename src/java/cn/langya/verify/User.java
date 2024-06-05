package cn.langya.verify;

/**
 * @author LangYa466
 * @date 2024/5/6 20:19
 */

public enum User {
    User,
    Free;

    public String getDisplayName() {
        if (this == User) return "赞助用户";
        return "普通用户";
    }
}
