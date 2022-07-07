package com.lost_n_found.home.placeholder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlaceholderFoundContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static  List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();

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
        public final String dateStr;
        public final Date date;
        public final String location;
        public final String foundBy;
        public final String imageUrl;
        public final String contact;
        public final String avatar;



        public PlaceholderItem(String uid, String title,String status, String description, Date date, String location,String foundBy , String imageUrl,String contact,String avatar,String dateStr) {
            this.uid = uid;
            this.title = title;
            this.status = status;
            this.description = description;
            this.date = date;
            this.dateStr = dateStr;
            this.location = location;
            this.foundBy = foundBy;
            this.imageUrl = imageUrl;
            this.contact = contact;
            this.avatar = avatar;
        }


    }
}