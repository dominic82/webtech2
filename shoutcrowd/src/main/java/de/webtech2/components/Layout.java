package de.webtech2.components;

import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.BindingConstants;

/**
 * Layout component for pages of application shoutcrowd.
 */
@Import(stylesheet = "context:layout/layout.css")
public class Layout
{
    /**
     * The page title, for the <title> element and the <h1> element.
     */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;
}
