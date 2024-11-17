package dungnm243.cineconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilmList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "film_list_name")
    private String filmListName;
    @Column(name = "created_date")
    private LocalDate createdDate;
    @Column(name = "description")
    private String description;
    @OneToMany
    @JoinColumn(name = "film_list_id")
    private List<FilmListItem> films;
}
