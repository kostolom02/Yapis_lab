package com.set.compiler;

import com.set.compiler.core.SetLanguageParserV1;
import com.set.compiler.core.SetLanguageVisitorImplV1;
import com.set.compiler.gen.SetLanguageLexer;
import com.set.compiler.gen.SetLanguageVisitor;

import com.set.compiler.result.Result;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.swing.*;
import java.io.*;
import java.nio.file.Paths;

public class Main {

    private static final File DESTINATION = Paths.get("src", "main", "java", "com.set.compiler", "result", "Result.java").toFile();

    public static void main(String args[]) {
        try {
            ANTLRInputStream input = new ANTLRInputStream(
                    new FileInputStream("resources/test.set"));
            SetLanguageLexer lexer = new SetLanguageLexer(input);
            SetLanguageParserV1 parser = new SetLanguageParserV1(new CommonTokenStream(lexer));

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ParseTree tree = parser.global_program();
            if (!byteArrayOutputStream.toString().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(null, "Lexer error: " + byteArrayOutputStream.toString());
                return;
            }
            SetLanguageVisitor<String> listVisitor = new SetLanguageVisitorImplV1(parser, "Result");
            String output = listVisitor.visit(tree);
            writeDestination(String.format(
                    "package com.set.compiler.result;\n\n%s",
                    output
            ));
            executeDestination();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeDestination(String str) throws IOException {
        deleteDestinationFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(DESTINATION));
        writer.write(str);
        writer.close();
    }

    private static void executeDestination() {
        System.out.println("Result.java execution output:");
        Result.main(null);
    }

    private static void deleteDestinationFile() {
        try {
            if (DESTINATION.delete()) {
                System.out.println(DESTINATION.getName() + " deleted...");
            } else {
                System.out.println(DESTINATION.getName() + "delete failed...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
