package dao;

import dto.Notifications;

public interface INotificationDao {
    public Notifications getNotificationByOrderId(int order_id);
    public int addNewNotification(Notifications a);
}
