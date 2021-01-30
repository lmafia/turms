import TurmsClient from '../turms-client';
import {ParsedRelayedRequest} from '../model/parsed-notification';

export default class NotificationService {
    private _turmsClient: TurmsClient;
    private _notificationListeners: ((notification: ParsedRelayedRequest) => void)[] = [];

    addNotificationListener(listener: (notification: ParsedRelayedRequest) => void): void {
        this._notificationListeners.push(listener);
    }

    removeNotificationListener(listener: (notification: ParsedRelayedRequest) => void): void {
        this._notificationListeners = this._notificationListeners
            .filter(cur => cur !== listener);
    }

    constructor(turmsClient: TurmsClient) {
        this._turmsClient = turmsClient;
        this._turmsClient.driver
            .addNotificationListener(notification => {
                const isBusinessNotification = notification.relayedRequest
                    && !notification.relayedRequest['createMessageRequest']
                    && !notification.closeStatus;
                if (isBusinessNotification) {
                    this._notificationListeners.forEach(listener => listener(notification.relayedRequest));
                }
            });
    }
}