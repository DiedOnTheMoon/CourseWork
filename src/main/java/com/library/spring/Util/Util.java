package com.library.spring.Util;

import com.library.spring.models.Blacklist;
import com.library.spring.models.Reader;
import com.library.spring.models.SpecificBook;
import com.library.spring.repository.ReaderRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;

public class Util {

    public static void updateBlackList(ReaderRepository readerRepository){

        List<Reader> readers = readerRepository.findAll();
        LocalDate today = LocalDate.now();
        Set<Blacklist> blacklists;

        for(Reader reader: readers){
            blacklists = reader.getBlacklists();
            if (blacklists == null) blacklists = new HashSet<>();

            for(SpecificBook book: reader.getSpecificBooks()){
                if (book.getReturnDate().isBefore(LocalDate.now())){

                    Long allPrice = Math.round( DAYS.between(today, book.getReturnDate()) *
                            (double) book.getBook().getPrice() / 10000);

                    blacklists.add(new Blacklist(allPrice, reader, book ));
                }
            }

            if(blacklists.size() != 0) reader.setBlacklists(blacklists);
        }

        readerRepository.saveAll(readers);
    }

}
