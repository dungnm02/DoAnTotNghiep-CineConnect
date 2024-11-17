package dungnm243.cineconnect.controllers;

import dungnm243.cineconnect.models.Follow;
import dungnm243.cineconnect.models.NormalUser;
import dungnm243.cineconnect.models.Report;
import dungnm243.cineconnect.models.User;
import dungnm243.cineconnect.repositories.FollowRepository;
import dungnm243.cineconnect.repositories.ReportRepository;
import dungnm243.cineconnect.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/normal-users")
public class NormalUserController {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final ReportRepository reportRepository;

    public NormalUserController(UserRepository userRepository, FollowRepository followRepository, ReportRepository reportRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.reportRepository = reportRepository;
    }

    /**
     * Xem thông tin của người dùng
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Follow hoặc unfollow người dùng
     *
     * @param userId id của người dùng cần follow
     * @return ResponseEntity<String>
     */
    @PostMapping("/{userId}/follow")
    public ResponseEntity<String> followUser(@PathVariable long userId) {
        NormalUser followingUser = (NormalUser) userRepository.findById(userId).orElse(null);
        if (followingUser == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        NormalUser currentUser = (NormalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Kiểm tra xem người dùng đã follow chưa => nếu đã follow thì unfollow
        for (Follow following : currentUser.getFollowings()) {
            if (following.getFollower().getId() == followingUser.getId()) {
                currentUser.getFollowings().remove(following);
                userRepository.save(currentUser);
                return new ResponseEntity<>("Unfollowed successfully", HttpStatus.OK);
            }
        }

        // Nếu chưa follow thì follow
        Follow follow = new Follow();
        follow.setFollower(currentUser);
        follow.setFollowing(followingUser);
        followRepository.save(follow);
        return new ResponseEntity<>("Followed successfully", HttpStatus.OK);
    }

    @PostMapping("/{userId}/report")
    public ResponseEntity<String> reportUser(@PathVariable long userId, @RequestBody Report report) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        report.setReporter((NormalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        reportRepository.save(report);
        return new ResponseEntity<>("Reported successfully", HttpStatus.OK);
    }
}
