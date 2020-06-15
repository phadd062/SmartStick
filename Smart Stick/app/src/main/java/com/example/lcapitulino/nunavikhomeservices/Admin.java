package com.example.lcapitulino.nunavikhomeservices;

class Admin extends User {
    @Override
    public String toString() {
        return super.toString();
    }

    public Admin(String password) {
        super("admin", password, Role.ADMIN);
    }
}
