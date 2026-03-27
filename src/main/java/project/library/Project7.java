package project.library;

public final class Project7 extends Project {
    private static Project7 instance;
    private String[] commands;

    public static Project7 getInstance() {
        if(instance == null) {
            instance = new Project7();
        }

        return instance;
    }

    public String generateAssemblyCode(String[] parts) {
        this.commands = parts;

        if(this.commands.length > 0) {
            switch (this.commands[0].trim()) {
                case "push":
                    return this.push();
                case "pop":
                    return this.pop();
                case "add":
                    return this.add();
                case "sub":
                    return this.sub();
            }
        }

        return "";
    }

    private String push() {
        String type = "";
        String location = this.commands[2];

        switch (this.commands[1]) {
            case "constant":
                return String.format("@%s\n" +
                    "D=A\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n", location);
            case "local":
                type = "LCL";
                break;
            case "that":
                type = "THAT";
                break;
            case "this":
                type = "THIS";
                break;
        }

        return String.format("@%s\n" +
            "D=M\n" +
            "@%s\n" +
            "A=D+A\n" +
            "D=M\n" +
            "@SP\n" +
            "A=M\n" +
            "M=D\n" +
            "@SP\n" +
            "M=M+1\n", type, location);
    }

    private String pop() {
        String type = "";
        String location = this.commands[2];

        switch (this.commands[1]) {
            case "local":
                type = "LCL";
                location = "0";
                break;
            case "argument":
                type = "ARG";
                break;
            case "this":
                type = "THIS";
                break;
            case "that":
                type = "THAT";
                break;
            case "temp":
                Integer segment = 5 + Integer.parseInt(location);
                return String.format("@SP\n" +
                    "AM=M-1\n" +
                    "D=M\n" +
                    "@R%s\n" +
                    "M=D\n", segment);
        }

        return String.format("@%s\n" +
            "D=M\n" +
            "@%s\n" +
            "D=D*A\n" +
            "@R13\n" +
            "M=D\n" +
            "@SP\n" +
            "AM=M-1\n" +
            "D=M\n" +
            "@R13\n" +
            "A=M\n" +
            "M=D\n", type, location);
    }

    private String add() {
        return "@SP\n" +
            "AM=M-1\n" +
            "D=M\n" +
            "A=A-1\n" +
            "M=D+M\n";
    }

    private String sub() {
        return "@SP\n" +
            "AM=M-1\n" +
            "D=M\n" +
            "A=A-1\n" +
            "M=D-M\n";
    }
}
