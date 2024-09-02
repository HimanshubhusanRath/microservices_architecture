package com.hr.springboot.examples.demo.filereaderexample.readers;

public interface FileReader {

    String readFile(final String filePath);
    String getType();
}
