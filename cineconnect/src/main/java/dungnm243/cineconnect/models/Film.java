package dungnm243.cineconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "film_name")
    private String filmName;

    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private int releaseYear;

    @Column(name = "film_poster")
    private String filmPoster;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private List<Review> reviewList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private List<Rating> ratingList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private List<FilmGenre> genreList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private List<FilmStudio> studioList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private List<Cast> castList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private List<Crew> crewList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private List<FilmLanguage> languageList;
}
