package cn.ulyer.socket.model;

import lombok.Data;

@Data
public class User {

    private String username;

    private String id;

    private String name;

    private String password;



    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if(obj==this){
            return true;
        }
        if(obj instanceof User){
            return this.username.equals(((User) obj).username);
        }
        return false;
    }
}
