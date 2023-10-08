package org.example.mybookmarks.services;


import org.example.mybookmarks.model.BadInputParameters;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class UrlTitleFetcher {

    String fetchAndGetTitle(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements tt = doc.select("title");
            if (!tt.hasText()) {
                throw new IllegalArgumentException("empty title");
            }
            return tt.text();
        } catch (Exception e) {
            throw new BadInputParameters(String.format("error occurred %s while sending request to %s", e, url));
        }
    }

}
