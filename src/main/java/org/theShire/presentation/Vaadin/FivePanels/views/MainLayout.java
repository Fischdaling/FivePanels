package org.theShire.presentation.Vaadin.FivePanels.views;

import org.theShire.presentation.Vaadin.FivePanels.service.GalerieService;
import org.theShire.presentation.Vaadin.FivePanels.views.about.AboutView;
import org.theShire.presentation.Vaadin.FivePanels.views.helloworld.HelloWorldView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;


/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    public static GalerieService galerieService;

    private H2 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
        initService();
    }

    private void initService() {
        galerieService = GalerieService.getInstance();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setText("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Company");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private Component createNavigation() {
        VerticalLayout nav = new VerticalLayout();
        nav.setSpacing(true);
        nav.add(
                createNavItem("Hello", HelloWorldView.class, VaadinIcon.GLOBE),
                createNavItem("About", AboutView.class, VaadinIcon.FILE)

                // TODO ergaenzen Sie hier Ihre Implementierung

        );

        return nav;
    }

    private RouterLink createNavItem(String label, Class<? extends Component> view,
                                     VaadinIcon icon) {
        RouterLink link = new RouterLink(view);
        link.addClassName("nav-item");
        link.setHighlightCondition(HighlightConditions.locationPrefix());
        link.add(new Icon(icon), new Text(" " + label));
        return link;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
