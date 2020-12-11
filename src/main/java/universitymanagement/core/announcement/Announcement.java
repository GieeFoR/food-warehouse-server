package universitymanagement.core.announcement;

import universitymanagement.core.user.User;

public record Announcement(int announcementId, User user, String title, String content) {
}
