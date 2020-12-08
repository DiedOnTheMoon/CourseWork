package com.library.spring.service;

import com.library.spring.models.Blacklist;
import com.library.spring.models.Genre;
import com.library.spring.models.Reader;
import com.library.spring.models.SpecificBook;
import com.library.spring.repository.GenreRepository;
import com.library.spring.repository.ReaderRepository;
import com.library.spring.repository.SpecificBookRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private static ReaderRepository readerRepository;
    private static GenreRepository genreRepository;
    private static SpecificBookRepository specificBookRepository;

    public ReportService(SpecificBookRepository specificBookRepository, GenreRepository genreRepository,
                         ReaderRepository readerRepository) {
        ReportService.specificBookRepository = specificBookRepository;
        ReportService.genreRepository = genreRepository;
        ReportService.readerRepository = readerRepository;
    }

    public static XSSFWorkbook createReportByGenre(String name){
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(name);

        sheet.setColumnWidth(0, 2560);
        sheet.setColumnWidth(1, 2560);

        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Genre Name");
        row.createCell(1).setCellValue("Count");

        List<Map.Entry<Genre, Integer>> list = createSortedGenreList();

        int i = 1;

        for(var item : list){
            row = sheet.createRow(i);
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

    public static XSSFWorkbook createReportBlacklist(String name){
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(name);

        for( int index = 0; index <= 13; index++) {
            sheet.setColumnWidth(index, 2560);
        }
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("First Name");
        row.createCell(1).setCellValue("LastName");
        row.createCell(2).setCellValue("Address");
        row.createCell(3).setCellValue("Phone");
        row.createCell(4).setCellValue("Book Name");
        row.createCell(5).setCellValue("Year");
        row.createCell(6).setCellValue("Language Name");
        row.createCell(7).setCellValue("Author Name");
        row.createCell(8).setCellValue("Genre Name");
        row.createCell(9).setCellValue("Publisher Name");
        row.createCell(10).setCellValue("Book Price");
        row.createCell(11).setCellValue("Unique Code");
        row.createCell(12).setCellValue("Debt");
        row.createCell(13).setCellValue("Return Date");

        int i = 1;

        List<Reader> readers = readerRepository.findAll();
        readers = readers.stream().sorted((o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName())
                + o1.getLastName().compareTo(o2.getLastName())).collect(Collectors.toList());

        for(Reader reader : readers){

            Set<Blacklist> blacklists = reader.getBlacklists();

            for(Blacklist bl : blacklists ){
                if(!bl.getPaid()){
                    row = sheet.createRow(i);
                    row.createCell(0).setCellValue(reader.getFirstName());
                    row.createCell(1).setCellValue(reader.getLastName());
                    row.createCell(2).setCellValue(reader.getAddress());
                    row.createCell(3).setCellValue(reader.getPhone());
                    row.createCell(4).setCellValue(bl.getSpecificBook().getBook().getBookName());
                    row.createCell(5).setCellValue(bl.getSpecificBook().getBook().getYear());
                    row.createCell(6).setCellValue(bl.getSpecificBook().getBook().getLanguage().getLanguageName());
                    row.createCell(7).setCellValue(bl.getSpecificBook().getBook().getAuthor().getAuthorName());
                    row.createCell(8).setCellValue(bl.getSpecificBook().getBook().getGenre().getGenreName());
                    row.createCell(9).setCellValue(bl.getSpecificBook().getBook().getPublisher().getPublisherName());
                    row.createCell(10).setCellValue(bl.getSpecificBook().getBook().getPrice().doubleValue());
                    row.createCell(11).setCellValue(bl.getSpecificBook().getUniqueCode());
                    row.createCell(12).setCellValue(bl.getPrice());
                    row.createCell(13).setCellValue(bl.getSpecificBook().getSpecificBookReaders().stream().sorted()
                            .filter(b -> !b.getReturn())
                            .findFirst().get().getReturnDate());
                    i++;
                }
            }
        }
        return  wb;
    }


}
