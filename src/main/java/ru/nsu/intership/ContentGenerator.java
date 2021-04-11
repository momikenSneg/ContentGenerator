package ru.nsu.intership;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentGenerator {
    private String fileName;

    public ContentGenerator(String fileName){
        this.fileName = fileName;
    }

    public void generate() throws IOException {
        int inner;
        int prev = 0;
        int count = 0;
        BufferedWriter writer = new BufferedWriter(new FileWriter("tmp.markdown"));
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            while (line != null) {
                inner = matcher(line);
                if (inner == 0){
                    line = reader.readLine();
                    continue;
                } else {
                    if (inner == prev)
                        count++;
                    else
                        count = 1;
                    String title = line.replaceAll("#+ ", "");
                    String name = getName(line);
                    String output = makeOutput(inner, count, title, name);
                    writer.write(output);
                    prev = inner;
                }
                line = reader.readLine();
            }
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(
                fileName))) {
            String line = reader.readLine();
            while (line != null) {
                writer.write(line + "\n");
                line = reader.readLine();
            }
        }
        writer.close();
        File input = new File(fileName);
        input.delete();
        File file = new File("tmp.markdown");

        File rename = new File(fileName);
        file.renameTo(rename);
    }

    public static int matcher (String inputString){
        String pattern = "#+ ";

        Pattern ptrn = Pattern.compile(pattern);
        Matcher matcher = ptrn.matcher(inputString);

        if(matcher.find()){
            return matcher.group(0).length();
        }else {
            return 0;
        }
    }

    public static String getName(String line){
        String[] arr = line.split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < arr.length - 1; i++){
            builder.append(arr[i]);
            builder.append("-");
        }
        builder.append(arr[arr.length - 1]);
        return builder.toString();
    }

    public static String makeOutput(int inner, int count, String line, String name){
        String output = "\t".repeat(inner - 2) +
                count +
                ". [" +
                line +
                "](#" +
                name +
                ")\n";
        return output;
    }
}
