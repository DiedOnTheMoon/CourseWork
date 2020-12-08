package com.library.spring.controllers;

import com.library.spring.repository.GenreRepository;
import com.library.spring.repository.SpecificBookRepository;
import com.library.spring.service.ReportService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller()
@RequestMapping("/chart")
public class ChartController {
    final GenreRepository genreRepository;
    final SpecificBookRepository specificBookRepository;

    public ChartController(SpecificBookRepository specificBookRepository, GenreRepository genreRepository) {
        this.specificBookRepository = specificBookRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping("/genre")
    public ResponseEntity<StreamingResponseBody> chartByGenre(){

        XSSFWorkbook workbook = ReportService.createReportByGenre("Genre Report");

        var list  = ReportService.createSortedGenreList();

        XSSFSheet sheet = workbook.getSheet("Genre Report");
        XSSFSheet sheet1 = workbook.createSheet("Genre Chart Pie");


        XSSFDrawing drawing = sheet1.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 4, 7, 20);


        XDDFChart chart = drawing.createChart(anchor);
        chart.setTitleText("Genres");
        chart.setTitleOverlay(false);

        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);

        XDDFDataSource<String> genres = XDDFDataSourcesFactory.fromStringCellRange(sheet,
                new CellRangeAddress(1, list.size(), 0, 0));

        XDDFNumericalDataSource<Double> values = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
                new CellRangeAddress(1, list.size(), 1, 1));

        XDDFChartData data = chart.createData(ChartTypes.PIE, null, null);

        data.setVaryColors(true);

        XDDFChartData.Series series = data.addSeries(genres, values);
        series.setTitle("Series", null);

        chart.plot(data);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"genreChart.xlsx\"")
                .body(workbook::write);
    }

}
