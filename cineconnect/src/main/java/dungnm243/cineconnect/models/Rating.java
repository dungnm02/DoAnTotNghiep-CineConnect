package dungnm243.cineconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "last_modified_date")
    private LocalDate lastModifiedDate;
    @Column(name = "rating_score")
    private int ratingScore;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private NormalUser user;
}
