package project.library;

public abstract class Project {
    public abstract String generateAssemblyCode(String[] parts);

    public String end() {
        return "@END\n0;JMP";
    }
}
