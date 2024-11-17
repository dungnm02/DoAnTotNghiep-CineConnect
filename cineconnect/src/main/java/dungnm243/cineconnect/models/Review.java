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
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private NormalUser user;
    @Column(name = "last_modified_date", nullable = false)
    private LocalDate lastModifiedDate;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "has_spoiler", nullable = false)
    private boolean hasSpoiler;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private List<ReviewReaction> reactionList;
}
