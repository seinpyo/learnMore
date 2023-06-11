package hellojpa.type;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.time.LocalDateTime;

@Embeddable
public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    public Period() {
    }

    public Period(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //setter를 private으로 설정

    public LocalDateTime getStartDate() {
        return startDate;
    }

    private void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    private void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
