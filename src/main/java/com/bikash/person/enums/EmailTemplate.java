package com.bikash.person.enums;

public enum EmailTemplate {

    WELCOME("welcome_template.html"),
    POSTCREATION("post_template.html");

    private  final  String fileName;


    EmailTemplate( String fileName) {
        this.fileName=fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
