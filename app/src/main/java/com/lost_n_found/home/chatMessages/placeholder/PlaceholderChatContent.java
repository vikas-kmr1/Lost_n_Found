package com.lost_n_found.home.chatMessages.placeholder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlaceholderChatContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, PlaceholderItem> ITEM_MAP = new HashMap<String, PlaceholderItem>();





    public static class PlaceholderItem {
        public final String uid;
        public final String name;
        public final String avatar;

        public PlaceholderItem(String uid, String name, String avatar) {
            this.uid = uid;
            this.name = name;
            this.avatar = avatar;
        }


    }
}