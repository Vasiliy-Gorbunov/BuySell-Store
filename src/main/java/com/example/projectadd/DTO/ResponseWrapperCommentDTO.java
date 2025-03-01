package com.example.projectadd.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperCommentDTO<T> {
    private int count;
    private List<CommentDTO> results;

    public static ResponseWrapperCommentDTO of(List<CommentDTO> results) {
        ResponseWrapperCommentDTO responseWrapperCommentDTO = new ResponseWrapperCommentDTO();
        if (results == null) {
            return responseWrapperCommentDTO;
        }
        responseWrapperCommentDTO.results = results;
        responseWrapperCommentDTO.count = results.size();
        return responseWrapperCommentDTO;
    }

    public ResponseWrapperCommentDTO(int count, List<CommentDTO> results) {
        this.count = count;
        this.results = results;
    }

    public ResponseWrapperCommentDTO() {
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CommentDTO> getResults() {
        return results;
    }

    public void setResults(List<CommentDTO> results) {
        this.results = results;
    }
}