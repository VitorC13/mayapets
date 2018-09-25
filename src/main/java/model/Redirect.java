/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * @author S0074009
 */
public class Redirect {

    private String controller;
    private String module;
    private String program;
    private String actions;

    public Redirect() {
        this.controller = "";
        this.module = "";
        this.program = "";
        this.actions = "";
    }

    public String getController() {
        return controller;
    }

    public void setController(String logic) {
        this.controller = logic;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

}
