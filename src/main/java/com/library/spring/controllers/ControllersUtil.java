package com.library.spring.controllers;

import com.library.spring.Constant;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllersUtil {

    static Map<String, String> getErrors(BindingResult bindingResult){
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError :: getDefaultMessage
        );
        return  bindingResult.getFieldErrors().stream().collect(collector);
    }

    static Map<String, String> isCorrect(String authors, String genres){
        Map<String, String> errors = new HashMap<>();
        String[] authorsArr = authors.split(",");
        String[] genresArr = genres.split(",");
        fillErrorsInAuthorN(errors, authorsArr);
        fillErrorsInGenresN(errors, genresArr);

        return errors;
    }

    private static void fillErrorsInGenresN(Map<String, String> errors, String[] genresArr){
        for(var item: genresArr){
            item.replaceAll("\\s+", "");
            if(!Constant.PATTERN_FOR_N.matcher(item).matches()){
                if(!errors.containsKey("genresName")){
                    errors.put("genresName", "Wrong genres name:" + item);
                }else{
                    errors.put("genresName", errors.get("genresName") + "," + item);
                }
            }
        }
    }

    private static void fillErrorsInAuthorN(Map<String, String> errors, String[] authorsArr){

        for(var item : authorsArr){
            item.replaceAll("\\s+", "");
            var author = item.split("\\.");
            if(author.length > 2) {
                if(!errors.containsKey("authorsName"))
                    errors.put("authorsName", "Wrong name Author:" + item);
                else
                    errors.put("authorName", errors.get("authorsName") + "," + item);
                continue;
            }
            if(author.length == 1) {
                if (!Constant.PATTERN_FOR_N.matcher(author[0]).matches()) {
                    errors.put("authorName", errors.get("authorsName") + "," + item);
                }
            }else if(author.length == 2)
                if (!Constant.PATTERN_FOR_N.matcher(author[0]).matches()) {
                    errors.put("authorName", errors.get("authorsName") + "," + item);
                }
        }
    }

}
