package dungnm243.cineconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private NormalUser follower; // Người theo dõi
    @ManyToOne
    @JoinColumn(name = "following_id")
    private NormalUser following; // Người được theo dõi
}
