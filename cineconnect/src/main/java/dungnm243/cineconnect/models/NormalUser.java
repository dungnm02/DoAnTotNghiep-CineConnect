package dungnm243.cineconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class NormalUser extends User {
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "biography")
    private String biography;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_list_id")
    private List<FilmList> filmList;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private List<Follow> followers;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private List<Follow> followings;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<FilmList> getFilmList() {
        return filmList;
    }

    public void setFilmList(List<FilmList> filmList) {
        this.filmList = filmList;
    }

    public List<Follow> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Follow> followers) {
        this.followers = followers;
    }

    public List<Follow> getFollowings() {
        return followings;
    }

    public void setFollowings(List<Follow> followings) {
        this.followings = followings;
    }
}
