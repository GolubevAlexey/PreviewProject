package ru.heywake.previewproject;

import java.util.ArrayList;

public class News {

        public String title;
        public String description;
        public String url;
        public String id;


        public News() {
                //
        }


        public News(String title, String description, String url, String id) {
                this.title = title;
                this.description = description;
                this.url = url;
                this.id = id;
        }

        public ArrayList<String> toList() {

                ArrayList list = new ArrayList<String>();

                list.add(title);
                list.add(description);
                list.add(url);
                list.add(id);

                return list;
        }

        public void toNews(ArrayList<String> list) throws NewsFromArrayException {

                if(list == null || list.size() < 4) {

                        new NewsFromArrayException("ArrayList cannot is a null or size of less four");
                        return;
                }

                title = list.get(0);
                description = list.get(1);
                url = list.get(2);
                id = list.get(3);

        }
}

class NewsFromArrayException extends Exception {

        public NewsFromArrayException(String message) {
                super(message);
        }

}
