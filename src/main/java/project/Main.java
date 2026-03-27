package project;

import project.library.Assembler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Assembler assembler = new Assembler();
        String content = "";

        if (args.length > 0) {
            String filename = args[0];
            String newfile = filename.replace("vm", "asm");

            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    if(!assembler.isComment(line)) {
                        content =  content + assembler.assemble(line);
                    }
                }

                content = content + assembler.end();
                System.out.println(content);
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
                e.printStackTrace();
            }

            try {
                Path file = Path.of(newfile);
                Files.writeString(file, content);
                System.out.println("Successfully wrote to the file " + file.toString());
            } catch (IOException e) {
                System.err.println("Error writing to the file: " + e.getMessage());
            }
        } else {
            System.out.println("Please enter a file path");
        }
    }
}
