package com.lost_n_found.home.placeholder;

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
public class PlaceholderLostContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static  Map<String, PlaceholderItem> ITEM_MAP = new HashMap<String, PlaceholderItem>();


    private static void addItem(PlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.uid, item);
    }




    /**
     * A placeholder item representing a piece of content.
     */
    public static class PlaceholderItem {
        public final String uid;
        public final String title;
        public final String status;
        public final String description;
        public final String date;
        public final String location;
        public final String lostBy;
        public final String imageUrl;
        public final String contact;



        public PlaceholderItem(String uid, String title,String status, String description, String date, String location,String lostBy ,String imageUrl,String contact) {
            this.uid = uid;
            this.title = title;
            this.status = status;
            this.description = description;
            this.date = date;
            this.location = location;
            this.lostBy = lostBy;
            this.imageUrl = imageUrl;
            this.contact = contact;
        }
    }
}