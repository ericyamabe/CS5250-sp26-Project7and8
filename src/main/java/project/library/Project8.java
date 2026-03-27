package project.library;

import java.util.HashMap;

public class Project8 extends Project {
    private static Project8 instance;
    private String[] commands;
    private HashMap<String, Integer> functionCalls = new HashMap<String, Integer>();

    public static Project8 getInstance() {
        if(instance == null) {
            instance = new Project8();
        }

        return instance;
    }

    @Override
    public String generateAssemblyCode(String[] parts) {
        this.commands = parts;

        if(this.commands.length > 0) {
            switch (this.commands[0].trim()) {
                case "label":
                    return this.label();
                case "if-goto":
                    return this.ifGoto();
                case "goto":
                    return this.goTo();
                case "function":
                    return this.function();
                case "call":
                    return this.call();
            }
        }

        return "";
    }

    private String label() {
        return String.format("(%s)\n", this.commands[1]);
    }

    private String ifGoto() {
        return String.format("@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "@%s\n" +
                "D;JNE\n", this.commands[1]);
    }

    private String goTo() {
        return String.format("@%s\n0;JMP\n", this.commands[1]);
    }

    private String function() {
        Integer count = Integer.valueOf(this.commands[2]);
        String content = "";

        if(count == 0) {
            return this.label();
        }

        for(Integer i = 0; i < count; i++) {
            content = content + "@0\n" +
                "D=A\n" +
                "@SP\n" +
                "A=M\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M+1\n";
        }

        return content;
    }

    private String call() {
        Integer labelCounter = 0;

        if(this.functionCalls.containsKey(this.commands[1])) {
            labelCounter = this.functionCalls.get(this.commands[1]);
        } else {
            this.functionCalls.put(this.commands[1], labelCounter++);
        }

        return String.format("@%s$ret.%s\n" +
            "D=A\n" +
            "@SP\n" +
            "A=M\n" +
            "M=D\n" +
            "@SP\n" +
            "M=M+1\n" +
            "@LCL\n" +
            "D=M\n" +
            "@SP\n" +
            "A=M\n" +
            "M=D\n" +
            "@SP\n" +
            "M=M+1\n" +
            "@ARG\n" +
            "D=M\n" +
            "@SP\n" +
            "A=M\n" +
            "M=D\n" +
            "@SP\n" +
            "M=M+1\n" +
            "@THIS\n" +
            "D=M\n" +
            "@SP\n" +
            "A=M\n" +
            "M=D\n" +
            "@SP\n" +
            "M=M+1\n" +
            "@THAT\n" +
            "D=M\n" +
            "@SP\n" +
            "A=M\n" +
            "M=D\n" +
            "@SP\n" +
            "M=M+1\n" +
            "@SP\n" +
            "D=M\n" +
            "@6\n" +
            "D=D-A\n" +
            "@ARG\n" +
            "M=D\n" +
            "@SP\n" +
            "D=M\n" +
            "@LCL\n" +
            "M=D\n" +
            "@%s\n" +
            "0;JMP\n" +
            "(%s$ret.%s)\n", this.commands[1], labelCounter, this.commands[1], this.commands[1], labelCounter);
    }
}
