package com.example.lcapitulino.nunavikhomeservices;

import java.util.ArrayList;

/* class User is a superclass representing any user on the system.
   The user types, like admin, homeowner and service provider should
   be subclasses of this class. This makes sense, because homeowner
   and service provider will have their own associations.
 */
class User {
    public enum Role { ADMIN, OWNER, PROVIDER, NONE };
    private String fullName;
    private String userName;
    private String password;
    private Role role;

    @Override
    public String toString() {
        return "userName=" + userName + " password=" + password;
    }

    /* Returns user's role as an enum Role */
    public Role getRole() {
        return role;
    }

    /* Helper method, converts a enum Role to a string */
    public String roleToString(Role role) {
        switch (role) {
            case ADMIN:
                return "administrator";
            case OWNER:
                return "HomeOwner";
            case PROVIDER:
                return "Service Provider";
            default:
                return "undefined";
        }
    }

    /* Check if password matches this user's password */
    public boolean passEquals(String password) {
        return getPassword().equals(password);
    }

    public boolean userEquals(String username) { return getUserName().equals(username); }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public User(String userName, String password, Role role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    /* Main constructor. Parameters:

       @param fullName: user's full name
       @param userName: username used for login
       @param password: password used for login
       @param role: user's role as an enum Role
     */
    public User(String fullName, String userName, String password, Role role) {
        this.fullName = fullName;
        this.password = password;
        this.userName = userName;
        this.role = role;
    }
}

