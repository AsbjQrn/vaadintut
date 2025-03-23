package dk.brokso.vaadintut.view;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("tips") // Defines the URL path
@PageTitle("Slanketips")
public class Tips extends VerticalLayout {

    public Tips() {

        UI.getCurrent().getElement().getThemeList().add(Lumo.DARK);

        add(new H1("Forskellige tips!"));
        add(new H1("Mæthed:"));

        add(new Span("Mæthed er forsinket. Hvis man har spist et måltid kan der gå 20 minutter før man mærker mæthed"));
        add(new Span("Cravings - er lidt som mæthed - de går ofte væk efter ca. 20 minutter -  "));
        add(new Span("Styring af mæthed og cravings under kalorie underskud, kan opnås ved at sprede kalorierne ud over dagen. Dvs med mellem-måltider og feks fem målteder istedet for to eller tre."));
        add(new Span("Supervåben: prepping !!! Hav mad liggende portionsafmålt i fryseren - et supervåben til nærkamp med cravings"));
        add(new Span("Denne app er skrevet til at kunne udnytte såkaldt \"termisk effekt\". Termisk effekt går ud på at kroppen bruger energi på at forbrænde forskellige fødevarer." +
                "Når man spiser fedt bruges 0-3 procent af energien til forbrænding. For kulhydrat er det mellem 5 og 10 procent. For protein er det mellem 20 og 30 procent" +
                "Dog er det vigtigt ikke at gå for lavt på fedtprocenten. Fedt bruges til mange vigtige ting i kroppen. " +
                "Så vidt vides må man ikke komme under 20 procent. Og et normalt sundt indtag kan ligge mellem 20 og 30 procent. Det er også vigtigt at få fedt fra forskellige kilder. " +
                "Det kan vist også lade sig gøre at få for meget protein så kroppen har svært ved at forarbejde det." +
                "Jeg har gode erfaringer med en blanding af fedt/protein/kulhydrat på 20/30/50 procent"
        ));
        add(new Span("Påstand: Der er ikke noget der feder mere end andet. Det eneste der feder er kalorier."));




    }
}
