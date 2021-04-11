package ru.nsu.intership;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        new ContentGenerator(args[0]).generate();
    }
}
