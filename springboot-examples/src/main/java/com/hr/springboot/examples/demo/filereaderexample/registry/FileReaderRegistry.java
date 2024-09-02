package com.hr.springboot.examples.demo.filereaderexample.registry;

import com.hr.springboot.examples.demo.filereaderexample.readers.FileReader;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FileReaderRegistry {

    private Map<String, FileReader> readersMap = new HashMap<>();

    @Autowired
    private List<FileReader> fileReaders;

    @PostConstruct
    public void populateMap(){
        for(FileReader reader : fileReaders){
            readersMap.put(reader.getType(), reader);
        }
    }

    public FileReader getReader(final String fileType){
        return readersMap.get(fileType);
    }

}
