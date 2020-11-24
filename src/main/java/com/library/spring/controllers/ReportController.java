package com.library.spring.controllers;

import com.library.spring.models.Genre;
import com.library.spring.models.SpecificBook;
import com.library.spring.repository.GenreRepository;
import com.library.spring.repository.SpecificBookRepository;
import com.library.spring.service.ReportService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/report")
public class ReportController {
    final GenreRepository genreRepository;
    final SpecificBookRepository specificBookRepository;

    public ReportController(SpecificBookRepository specificBookRepository, GenreRepository genreRepository) {
        this.specificBookRepository = specificBookRepository;
        this.genreRepository = genreRepository;
    }


    @GetMapping("/genre")
    public ResponseEntity<StreamingResponseBody> reportByGenre(){

        XSSFWorkbook workbook = ReportService.createReport("Genre Report");

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"genreReport.xlsx\"")
                .body(workbook::write);
    }


}
