package com.library.spring.service;

import com.library.spring.models.Blacklist;
import com.library.spring.models.Reader;
import com.library.spring.models.SpecificBook;
import com.library.spring.models.SpecificBookReader;
import com.library.spring.repository.ReaderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class BlacklistService {

    public static void updateBlackList(ReaderRepository readerRepository){

        List<Reader> readers = readerRepository.findAll();
        Set<Blacklist> blacklists;

        for(Reader reader: readers){
            blacklists = reader.getBlacklists();
            if (blacklists == null) blacklists = new HashSet<>();

            var specBooks = reader.getSpecificBooksReader().stream()
                    .filter(SpecificBookReader::getReturn)
                    .map(SpecificBookReader::getSpecificBook)
                    .collect(Collectors.toSet());

            for(SpecificBook book: specBooks){
                for(SpecificBookReader sbr : book.getSpecificBookReaders())
                {
                    if(sbr.getReturn()){
                        continue;
                    }

                    Blacklist bl = new Blacklist();

                    if(sbr.getReturnDate().isBefore(LocalDate.now())){

                        Long price = Math.round( DAYS.between(LocalDate.now(), sbr.getReturnDate()) *
                                (double) book.getBook().getPrice() / 10000);

                        bl.setPrice(price);
                        bl.setPaid(false);
                        bl.setSpecificBook(book);
                        bl.setReader(sbr.getReader());

                        blacklists.add(bl);
                    }
                }
            }

            if(blacklists.size() != 0) reader.setBlacklists(blacklists);
        }
        readerRepository.saveAll(readers);
    }

}
