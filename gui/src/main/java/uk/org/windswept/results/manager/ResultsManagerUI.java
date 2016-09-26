package uk.org.windswept.results.manager;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class ResultsManagerUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        final Label heading = new Label("<h1>Results Manager</h1>", ContentMode.HTML);
        
        layout.addComponents(heading);
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "ResultsManagerUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = ResultsManagerUI.class, productionMode = false)
    public static class ResultsManagerUIServlet extends VaadinServlet {
    }
}
