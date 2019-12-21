package converters;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

/**
 * @author Yuliia Shcherbakova ON 06.12.2019
 * @project repair_system
 */
public class StringConverter {
    private static final Logger logger = Logger.getLogger(StringConverter.class);

    private StringConverter() {
    }

    public static String convertToUTF8(String text) {
        try {
            return new String(text.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Could not convert the string" + text);
            return text;
        }
    }
}
