package dungnm243.cineconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "create_date")
    private LocalDate createDate;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private NormalUser reporter;
}
