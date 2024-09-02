package com.hr.springboot.examples.demo.filereaderexample.controller;


import com.hr.springboot.examples.demo.filereaderexample.registry.FileReaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file-reader")
public class FileReadController {

    @Autowired
    private FileReaderRegistry fileReaderRegistry;

    @GetMapping("/read-file")
    public String readFile(@RequestParam("file-type") final String fileType) {
        return fileReaderRegistry.getReader(fileType).readFile("--FILE-PATH--");
    }
}
