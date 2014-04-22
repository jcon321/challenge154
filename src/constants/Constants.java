/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package constants;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 *
 * @author jconner
 */
public class Constants {
    private static final String CONSTANTS = "constants.Constants";
    private static final Locale currentLocale = Locale.getDefault();
    private static final ResourceBundle properties = PropertyResourceBundle.getBundle(CONSTANTS, currentLocale);
    public static String getMessage(String code) {
        return properties.getString(code);
    }
}
