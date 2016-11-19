package com.example.tsleeve.swipeswap;

import android.content.Context;

/**
 * Created by victorlai on 11/8/16.
 */

public class Notification {
    private Context mContext;
    private String mUser;
    private Message mMessage;
    private SwipeDataAuth mDb = new SwipeDataAuth();

    public enum Message {
        ACCEPTED_SALE,
        ACK_SALE,
        ACCEPTED_REQUEST,
        ACK_REQUEST,
        REJECTED,
        REVIEW_SELLER,
        REVIEW_BUYER,
        OTHER
    }

    /**
     * Constructs a Notification with a context, target user, and message.
     *
     * @param context    The context of the application
     * @param targetUser The ID of the user to receive the notification
     * @param message    The type of message to be included in the notification
     */
    public Notification(Context context, String targetUser, Message message) {
        this.mContext = context;
        this.mUser = targetUser;
        this.mMessage = message;
    }

    /**
     * Returns the ID of the user to send the notification to
     *
     * @return The user ID
     */
    public String getUserID() {
        return mUser;
    }

    /**
     * Returns the appropriate title to be included in the notification.
     *
     * @return The title to include in the notification.
     */
    public String title() {
        switch (mMessage) {
            case ACCEPTED_SALE:
            case ACCEPTED_REQUEST:
                return "Swipe Accepted";
            case ACK_REQUEST:
            case ACK_SALE:
                return "Confirmed";
            case REJECTED:
                return "Swipe Rejected";
            case REVIEW_BUYER:
            case REVIEW_SELLER:
                return "Review";
            default: // OTHER
                return "Default Message";
        }
    }

    /**
     * Returns the appropriate message to be included in the notification.
     *
     * @return The message to include in the notification.
     */
    public String message() {
        String user = mDb.getUserName(mUser);
        String buyer = user;
        String seller = user;

        // TODO: Add rating of user in message
        switch (mMessage) {
            case ACCEPTED_SALE:
                return buyer + " wants to buy your swipe.";
            case ACK_SALE:
                return seller + "has accepted to sell";
            case ACCEPTED_REQUEST:
                return seller + " has agreed to your swipe sale.";
            case ACK_REQUEST:
                return buyer + "has agreed to your sale";
            case REJECTED:
                return user + " has rejected your swipe.";
            case REVIEW_BUYER:
                return "Rate the buyer (" + buyer + ")";
            case REVIEW_SELLER:
                return "Rate the seller (" + seller + ")";
            default: // OTHER
                return "This is a default notification message.";
        }
    }

    /**
     * Returns the context of the notification.
     *
     * @return The context of the notification.
     */
    public Context getContext() {
        return mContext;
    }
}
