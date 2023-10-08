package org.example.mybookmarks.controllers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mybookmarks.model.BadInputParameters;
import org.example.mybookmarks.model.ConflictDataException;
import org.example.mybookmarks.model.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {
    @ExceptionHandler(BadInputParameters.class)
    void handle(HttpServletResponse response, Exception exception) throws IOException {
        sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ConflictDataException.class)
    void handleConflictData(HttpServletResponse response, Exception exception) throws IOException {
        sendResponse(response, HttpServletResponse.SC_CONFLICT, exception.getMessage());
    }

    void sendResponse(HttpServletResponse response, int status, String errorMsg) throws IOException {
        response.setStatus(status);
        ObjectMapper mapper = new ObjectMapper();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()))) {
            bw.write(mapper.writeValueAsString(new ErrorResponse(errorMsg)));
        }
    }
}
