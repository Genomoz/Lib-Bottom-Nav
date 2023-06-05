package io.genemoz.custombottomnav;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.annotation.XmlRes;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

class BottomBarParser {

    private static final String ITEM_TAG = "item";
    private static final String ICON_ATTRIBUTE = "icon";
    private static final String TITLE_ATTRIBUTE = "title";
    private static final String CONTENT_DESCRIPTION_ATTRIBUTE = "contentDescription";

    private final Context context;
    private final XmlResourceParser parser;

    BottomBarParser(Context context, @XmlRes int res) {
        this.context = context;
        this.parser = context.getResources().getXml(res);
    }

    List<BottomBarItem> parse() throws Exception {
        List<BottomBarItem> items = new ArrayList<>();
        int eventType;

        do {
            eventType = parser.next();
            if (eventType == XmlResourceParser.START_TAG && ITEM_TAG.equals(parser.getName())) {
                items.add(getTabConfig(parser));
            }
        } while (eventType != XmlResourceParser.END_DOCUMENT);

        return items;
    }

    private BottomBarItem getTabConfig(XmlResourceParser parser) throws Exception {
        int attributeCount = parser.getAttributeCount();
        String itemText = null;
        Drawable itemDrawable = null;
        String contentDescription = null;

        for (int index = 0; index < attributeCount; index++) {
            switch (parser.getAttributeName(index)) {
                case ICON_ATTRIBUTE:
                    itemDrawable = ContextCompat.getDrawable(context, parser.getAttributeResourceValue(index, 0));
                    break;
                case TITLE_ATTRIBUTE:
                    try {
                        itemText = context.getString(parser.getAttributeResourceValue(index, 0));
                    } catch (Resources.NotFoundException notFoundException) {
                        itemText = parser.getAttributeValue(index);
                    }
                    break;
                case CONTENT_DESCRIPTION_ATTRIBUTE:
                    try {
                        contentDescription = context.getString(parser.getAttributeResourceValue(index, 0));
                    } catch (Resources.NotFoundException notFoundException) {
                        contentDescription = parser.getAttributeValue(index);
                    }
                    break;
            }
        }

        if (itemDrawable == null) {
            try {
                throw new Throwable("Item icon can not be null!");
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        return new BottomBarItem(itemText, contentDescription != null ? contentDescription : itemText, itemDrawable, new RectF(), 0);
    }
}
