package com.library.spring.controllers;

import com.library.spring.repository.GenreRepository;
import com.library.spring.repository.ReaderRepository;
import com.library.spring.repository.SpecificBookRepository;
import com.library.spring.service.BlacklistService;
import com.library.spring.service.ReportService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller
@RequestMapping("/report")
public class ReportController {
    final GenreRepository genreRepository;
    final SpecificBookRepository specificBookRepository;
    final ReaderRepository readerRepository;

    public ReportController(SpecificBookRepository specificBookRepository, GenreRepository genreRepository,
                            ReaderRepository readerRepository) {
        this.specificBookRepository = specificBookRepository;
        this.genreRepository = genreRepository;
        this.readerRepository = readerRepository;
    }


    @GetMapping("/blacklist")
    public ResponseEntity<StreamingResponseBody> reportByGenre(){
        BlacklistService.updateBlackList(readerRepository);

        XSSFWorkbook workbook = ReportService.createReportBlacklist("Blacklist");

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"blacklistReport.xlsx\"")
                .body(workbook::write);
    }


}
