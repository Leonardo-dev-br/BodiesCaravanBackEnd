package bodies_caravan.model;

import java.time.LocalDate;

public class SearchHistory {

    private Long idSearchHistory;
    private LocalDate surveyDay;

    public Long getIdSearchHistory() {
        return idSearchHistory;
    }
    public void setIdSearchHistory(Long idSearchHistory) {
        this.idSearchHistory = idSearchHistory;
    }
    public LocalDate getSurveyDay() {
        return surveyDay;
    }
    public void setSurveyDay(LocalDate surveyDay) {
        this.surveyDay = surveyDay;
    }

    @Override
    public String toString() {
        return "SearchHistory{" +
                "idSearchHistory=" + idSearchHistory +
                ", surveyDay=" + surveyDay +
                '}';
    }
}
