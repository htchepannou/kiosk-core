package com.tchepannou.kiosk.core.filter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

import java.util.ArrayList;
import java.util.List;

/**
 * Remove polluting tags like SCRIPT, IMAGES, etc.
 */
public class SanitizeFilter implements TextFilter {
    //-- Static
    public static final String[] TAG_WHITELIST =
            "a,b,blockquote,body,br,caption,cite,code,col,colgroup,dd,div,dl,dt,em,h1,h2,h3,h4,h5,h6,i,li,ol,p,pre,q,small,span,strike,strong,sub,sup,table,tbody,td,tfoot,th,thead,tr,u,ul"
                            .split(",");


    private final Whitelist whitelist;

    public SanitizeFilter() {
        this.whitelist = createWhitelist(TAG_WHITELIST);
    }

    //-- TextFilter overrides
    @Override
    public String filter(final String str) {
        /* keep only whitelist */
        final String xhtml = Jsoup.clean(str, this.whitelist);

        /* remove empty */
        final Document doc = Jsoup.parse(xhtml);
        final List<Element> items = new ArrayList<>();
        collectEmpty(doc.body(), items);
        for (final Element item : items) {
            item.remove();
        }
        return doc.html();
    }

    private Whitelist createWhitelist(final String[] tags) {
        final Whitelist wl = new Whitelist();
        wl.addTags(tags);
        return wl;
    }

    private void collectEmpty(final Element node, final List<Element> items) {
        if (node.children().isEmpty() && node.isBlock() && !node.hasText()) {
            items.add(node);
        } else {
            for (final Element child : node.children()) {
                collectEmpty(child, items);
            }
        }
    }
}
