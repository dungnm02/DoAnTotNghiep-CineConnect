package dungnm243.cineconnect.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PeopleRequest extends Request {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "people_id")
    private People people;
}
