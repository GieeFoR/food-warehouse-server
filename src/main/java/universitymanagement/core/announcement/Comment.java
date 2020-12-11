package universitymanagement.core.announcement;

import universitymanagement.core.user.User;
import universitymanagement.core.announcement.Announcement;

public record Comment(int commentId, User user, Announcement announcement, String content) {
}
