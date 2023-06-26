package com.example.projectadd.DTO;

import lombok.Data;

import java.util.Collection;

@Data
public class ResponseWrapperCommentDTO<T> {
    private int count;
    private Collection<T> results;

    public ResponseWrapperCommentDTO(int count, Collection<T> results) {
        this.count = count;
        this.results = results;
    }

    public ResponseWrapperCommentDTO() {
    }

    public static <T> ResponseWrapperCommentDTO<T> of(Collection<T> results) {
        ResponseWrapperCommentDTO<T> responseWrapperCommentDTO = new ResponseWrapperCommentDTO<>();
        if (results == null) {
            return responseWrapperCommentDTO;
        }
        responseWrapperCommentDTO.results = results;
        responseWrapperCommentDTO.count = results.size();
        return responseWrapperCommentDTO;
    }

}