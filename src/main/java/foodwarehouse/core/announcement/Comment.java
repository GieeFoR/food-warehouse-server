package foodwarehouse.core.announcement;

import foodwarehouse.core.user.User;

public record Comment(int commentId, User user, Announcement announcement, String content) {
}
