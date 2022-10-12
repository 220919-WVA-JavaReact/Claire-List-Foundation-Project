package com.revature.models;
import java.util.Objects;
public class User {
    //...fields
    //... prevent mutatability here by using getters and setters!

    private int user_id;

    private String first_name;
    private String last_name;
    private String user_name;
    private String email;
    private String password;
    private int role_num; //1 for employee, 2 for manager ie

    //NOW we want to be able to call with both arguments and no-arg constructor! This way, on user create we are not calling something that does not exist yet!

    public User(int user_id, String first_name, String last_name, String email, String password, String user_name, int role_num){
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.role_num = role_num;
        this.user_name = user_name;
    }

    public User(){} //no args, in case we need it :-) as a treat :-)

    public String setUser_name() {
        this.user_name = user_name;
        return this.user_name;
    }
    public int getUser_id(){
        return user_id;
    }
    public int setUser_id(){
        this.user_id = user_id;
        return this.user_id;
    }

    public int getRole_num() {
        return role_num;
    }
    public int setRole_num(){
        this.user_id = user_id;
        return this.user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", first='" + first_name + '\'' +
                ", last='" + last_name + '\'' +
                ", username='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role_num + '\'' +
                '}';
    }
    //use for when get user after registration

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        // Explicit Type Casting
        User user = (User) o;
        return user_id == user.user_id && first_name.equals(user.first_name) && last_name.equals(user.last_name) && user_name.equals(user.user_name) && password.equals(user.password) && email.equals(user.email) && role_num == user.role_num;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, first_name, last_name, user_name, password, email, role_num);
    }
}

