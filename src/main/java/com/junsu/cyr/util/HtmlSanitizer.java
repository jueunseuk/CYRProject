package com.junsu.cyr.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public class HtmlSanitizer {
    public static String sanitize(String html) {
        if(html == null) return null;
        return Jsoup.clean(html, Safelist.basicWithImages());
    }
}
