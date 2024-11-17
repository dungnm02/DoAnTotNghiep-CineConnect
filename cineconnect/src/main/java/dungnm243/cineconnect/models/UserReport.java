package dungnm243.cineconnect.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserReport extends Report {
    @ManyToOne
    @JoinColumn(name = "reported_user_id")
    private NormalUser reportedUser;
}
