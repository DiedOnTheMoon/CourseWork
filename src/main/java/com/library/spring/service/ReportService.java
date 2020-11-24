package com.library.spring.service;

import com.library.spring.models.Genre;
import com.library.spring.models.SpecificBook;
import com.library.spring.repository.GenreRepository;
import com.library.spring.repository.SpecificBookRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportService {
    private static GenreRepository genreRepository;
    private static SpecificBookRepository specificBookRepository;

    public ReportService(SpecificBookRepository specificBookRepository, GenreRepository genreRepository) {
        ReportService.specificBookRepository = specificBookRepository;
        ReportService.genreRepository = genreRepository;
    }

    public static XSSFWorkbook createReport(String name){
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(name);

        sheet.setColumnWidth(0, 2560);
        sheet.setColumnWidth(1, 2560);

        List<Map.Entry<Genre, Integer>> list = createSortedGenreList();

        int i = 0;

        for(var item : list){
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(item.getKey().getGenreName());
            row.createCell(1).setCellValue(item.getValue());
            i++;
        }

        return wb;
    }

    public static List<Map.Entry<Genre, Integer>> createSortedGenreList(){
        List<SpecificBook> specificBooks  = specificBookRepository.findAll();

        specificBooks.sort(Comparator.comparing(SpecificBook::getSizeSpecificBooksReader));

        HashMap<Genre, Integer> dictionary = new HashMap<>();

        for(var item : specificBooks){

            if(dictionary.containsKey(item.getBook().getGenre())){
                dictionary.put(item.getBook().getGenre(), dictionary.get(item.getBook().getGenre()) + item.getSizeSpecificBooksReader());
            }else{
                dictionary.put(item.getBook().getGenre(), item.getSizeSpecificBooksReader());
            }

        }

        List<Map.Entry<Genre, Integer>> list = new ArrayList<>(dictionary.entrySet());
        list.sort(Map.Entry.comparingByValue());

        return list;
    }

}
