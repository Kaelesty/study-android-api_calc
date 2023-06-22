package com.kaelesty.api_calc;

public class EvaluationResult {
    private String status;
    private String content;

    public EvaluationResult(String status, String content) {
        this.status = status;
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }
}
