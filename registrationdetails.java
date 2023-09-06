package com.example.projcopy;

public class registrationdetails {
    private String username,emails,phno;
    public registrationdetails(){
    }


    public registrationdetails(String username, String emails, String phno) {
        this.username=username;
        this.emails=emails;
        this.phno=phno;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getemails() {
        return emails;
    }

    public void setemails(String emails) {
        this.emails = emails;
    }

    public String getphno() {
        return phno;
    }

    public void setphno(String phno) {
        this.phno = phno;
    }

}
