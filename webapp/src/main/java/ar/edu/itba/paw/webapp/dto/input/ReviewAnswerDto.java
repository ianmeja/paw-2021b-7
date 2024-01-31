package ar.edu.itba.paw.webapp.dto.input;

import javax.validation.constraints.Size;

public class ReviewAnswerDto {
    @Size(max = 100)
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
