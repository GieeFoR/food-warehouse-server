package foodwarehouse.core.announcement;

import foodwarehouse.core.user.User;

public record Announcement(int announcementId, User user, String title, String content) {
}
