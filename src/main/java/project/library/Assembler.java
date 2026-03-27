package project.library;

import java.util.ArrayList;

public class Assembler {
    String[] parts;
    ArrayList<String> project7Commands;
    ArrayList<String> project8Commands;

    public Assembler() {
        this.project7Commands = new ArrayList<String>();
        project7Commands.add("push");
        project7Commands.add("pop");
        project7Commands.add("add");
        project7Commands.add("sub");

        project8Commands = new ArrayList<>();
        project8Commands.add("label");
        project8Commands.add("if-goto");
        project8Commands.add("goto");
        project8Commands.add("function");
        project8Commands.add("call");
    }

    public String assemble(String line) {
        this.parts = line.split(" ");
        Project project7 = Project7.getInstance();
        Project project8 = Project8.getInstance();

        if(this.project7Commands.contains(this.parts[0].trim())) {
            return project7.generateAssemblyCode(this.parts);
        } else if(this.project8Commands.contains(this.parts[0].trim())) {
            return project8.generateAssemblyCode(this.parts);
        }

        return "";
    }

    public boolean isComment(String line) {
        return line.startsWith("//");
    }

    public String end() {
        Project project = Project7.getInstance();
        return project.end();
    }
}
