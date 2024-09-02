package com.hr.springboot.examples.demo.filereaderexample.readers;

import org.springframework.stereotype.Component;

@Component
public class JSONReader implements FileReader{
    
    @Override
    public String readFile(String filePath) {
        return "JSON-Content";
    }

    @Override
    public String getType() {
        return "json";
    }
}
