package com.library.spring.Util;

import com.library.spring.models.Blacklist;
import com.library.spring.models.Reader;
import com.library.spring.models.SpecificBook;
import com.library.spring.repository.ReaderRepository;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static void updateBlackList(ReaderRepository readerRepository){

        List<Reader> readers = readerRepository.findAll();
        DateTime today = new DateTime();
        List<Blacklist> blacklists = new ArrayList<>();

        for(Reader reader: readers){

            for(SpecificBook book: reader.getSpecificBooks()){
                if (book.getReturnDate().isBeforeNow()){

                    Long allPrice = Math.round( Days.daysBetween(today, book.getReturnDate()).getDays() *
                            (double) book.getBook().getPrice() / 10000);

                    blacklists.add(new Blacklist(allPrice, reader, book ));
                }
            }
        }

    }

}
