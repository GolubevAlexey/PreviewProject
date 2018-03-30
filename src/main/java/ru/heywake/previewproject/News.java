package ru.heywake.previewproject;

import java.net.URL;

/**
 * Created by golub on 29.03.2018.
 */

public class News {

        public String title;
        public String description;
        public String url;
        public String id;

        public News(String title, String description, String url, String id) {
                this.title = title;
                this.description = description;
                this.url = url;
                this.id = id;
        }
}
