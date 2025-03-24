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

        add(new H1("Mæthed, cravings med mere:"));

        add(new Span("Mæthed er forsinket. Hvis man lige har spist et måltid kan der gå 20 minutter før man mærker mæthed"));
        add(new Span("Cravings - går ofte væk efter ca. 20 minutter."));
        add(new Span("Styring af mæthed og cravings under kalorie underskud, kan opnås ved at sprede kalorierne ud over dagen. " +
                "Dvs med mellem-måltider og feks fem måltider istedet for to eller tre. (Feks tre hovedmåltider á 500 kcal og to mellemmåltider á 250 kcal - hvis man altså skal have 2000 kcal dagligt)"));
        add(new Span("Husk fibre i kosten - fibre er ikke et makronæringsstof, men giver en fantastisk mæthed. Bønner og lign er godt til både fibre og  protein. Derudover kan man bruge psyllium frøskaller mm. " +
                "Vær opmærksom på at en del plante proteinkilder, ikke er fuldt dækkende mht til protein/essentielle aminosyrer - men tænker kun det er et potentielt problem ved vegetarisk kost (dog har feks sojabønner fuld dækning).   "));

        add(new Span("Supervåben: prepping !!! Hav mad liggende portionsafmålt i fryseren - et supervåben der er hurtigt at finde frem når man er i nærkamp med cravings. " +
                "I det hele taget SUPERVÅBNET til vægttab"));


        add(new Span("Bemærk at appen beregner et mæthedstal. Tallet udtrykker \"opnået mæthed pr indtaget kalorie\". Tallet ligger mellem 0 og 5. Jo højere tal, jo mere mæthed. Tallet er som regel retvisende, " +
                "men det er klogt lige at prøve en ny ret af, inden man kaster sig ud i at preppe til en måneds aftensmad :-)  "));

        add(new Span("Termisk effekt. Denne app er skræddersyet til udnyttelse af den såkaldte \"termiske effekt\". " +
                "Termisk effekt går ud på at kroppen bruger forskellig energi på at forbrænde forskellige fødevarer." +
                "Når man spiser fedt bruges 0-3 procent af den indtagne energi til forbrænding. For kulhydrat er det mellem 5 og 10 procent. " +
                "For protein er det mellem 20 og 30 procent, dvs man opnår en ekstra forbrændings/slankeeffekt ved indtagelse af den samme mængde kalorier, på forskellige fødevarer" +
                "Dog er det vigtigt ikke at gå for lavt på fedtprocenten. Fedt bruges til mange vigtige ting i kroppen. " +
                "Så vidt vides må man ikke komme under 20 procent. Og et normalt sundt indtag kan ligge mellem 20 og 30 procent. Det er også vigtigt at få fedt fra forskellige kilder. " +
                "Ifølge nogle kilder kan man få for meget protein, ifølge andre kan man ikke. Tænker at hvis man undgår ekstremer er man \"in the green\"" +
                "Jeg har gode erfaringer med vægttab i en blanding af fedt/protein/kulhydrat på 20/30/50 procent. " ));


        add(new Span("NEAT - \"Non-exercise activity thermogenesis\". Et fint udtryk for ikke at ligge i sofaen - men heller ikke være igang med at træne. " +
                "Kort sagt alle mulige småting kan have en kæmpe effekt for ens forbrænding - havearbejde, male bryggerset, cykle til brugsen istedet for bil - osv. " +
                "Eksempel: at stå op at arbejde for en udvikler der ellers ville sidde hele dagen: " +
                "Sidder i 7 timer: 100 kcal/time × 7 timer = 700 kcal\n" +
                "\n" +
                "Står i 7 timer: 120-140 kcal/time × 7 timer = 840-980 kcal - altså procentvis en pæn merforbrænding, som i sig selv kan give en effekt. " ));

        add(new Span("Husk at få nok vand - det ser ud til der sker mindre vægttab hvis man ikke drikker nok"));









    }
}
