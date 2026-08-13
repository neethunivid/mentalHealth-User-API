package com.mentalhealthapi.helper;

public class FileUploadResponse {
    private String filename;
    private Long id;
    private String message;

    // Constructors (default and parameterized)

    public FileUploadResponse() {
    }

    public FileUploadResponse(String filename, Long id, String message) {
        this.filename = filename;
        this.id = id;
        this.message = message;
    }

    // Getters and Setters

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
