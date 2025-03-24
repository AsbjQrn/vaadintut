package dk.brokso.vaadintut.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
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

//        add(new H1("Mæthed, cravings med mere:"));

        add(new H2("En lille slankehistorie"));


        add(new Span("Nedenstående er et forsøg på at videreformidle de tanker og teknikker jeg har samlet op og brugt til selv at tabe mig. Jeg gik fra 111 kilo til 95-96 kilo på 7-8 måneder." +
                "Jeg er ikke blevet super slim - så skulle jeg tabe mig noget mere. Men har dog fået fedtprocenten ud af det røde felt og ind i toppen af det grønne felt. " +
                "Og jeg har tabt så meget at knæ og fødder føles helt anderledes og jeg føler mig meget bedre tilpas. " +
                "De sidste kilo er meget, meget svære at tabe - og for mig ikke så vigtige - derfor er jeg nu gået ind i en fase hvor jeg arbejder med at holde min vægt. Det er indtil videre lykkedes fornuftigt.  " +
                "Jeg brugte cykling og vægttræning som træningsmetoder til at få forbrændingen op. " +
                "Cykling 1-2 gange om ugen til og fra arbejde. Vægttræning meget svingende mellem to og fire gange pr uge. Nedenstående er mit forsøg på at samle op på forløbet så andre måske kan bruge det. " +
                "Jeg synes vægttræning er en suveræn træningsform til vægttab. Det føles som om man nærmest er mindre sulten efter træning - i modsætning til cykling hvor man bliver sulten. " +
                "Jeg har brugt en kostplan med tre hovedmåltider og to mellemmåltider og selvfølgelig et indbygget kalorieunderskud i denne plan. " +
                "Min erfaring er at det er den gyldne, stille og rolige mellemvej, som i virkeligheden er motorvejen til vægttab"));

        add(new Span("For at gøre det nemt har jeg i lang tid spist meget det samme. Havregryn til morgenmad, banan og proteinbar til mellemmåltid. Rugbrødsmadder til frokost. Forskellige nedfrosne retter til aftensmad"));
        add(new Span("Dette bliver nemt kedeligt - derfor denne app så man nemt selv kan designe sine retter og få lækre variationer som kun er et spørgsmål om opfindsomhed"));
        add(new Span("Tænker hvis der er bare er nogle få mennesker der bruger appen - så kan man hurtigt samle en masse opskrifter der sparker numse mht mæthed og lækkerhed og samtidig ikke indeholder en masse kalorier."));


        add(new H2("Tips til mæthed, cravings med mere:"));

        add(new Span("Mæthed er forsinket. Hvis man lige har spist et måltid kan der gå 20 minutter før man mærker mæthed"));
        add(new Span("Cravings - går ofte væk efter ca. 20 minutter."));
        add(new Span("Styring af mæthed og cravings under kalorie underskud, kan opnås ved at sprede kalorierne ud over dagen. " +
                "Dvs med mellem-måltider og feks fem måltider istedet for to eller tre. (Feks tre hovedmåltider á 500 kcal og to mellemmåltider á 250 kcal - hvis man altså skal have 2000 kcal dagligt) - " +
                "Effektiviteten i denne metode er jeg selv meget overrasket over."));

        add(new Span("Husk fibre i kosten - fibre er ikke et makronæringsstof, men giver en fantastisk mæthed. Bønner og lign er godt til både fibre og  protein. Derudover kan man bruge psyllium frøskaller mm. " +
                "Vær opmærksom på at en del plante proteinkilder, ikke er fuldt dækkende mht til protein/essentielle aminosyrer - men tænker kun det er et potentielt problem ved vegetarisk kost " +
                "(dog har feks sojabønner fuld dækning).   "));

        add(new Span("Supervåben: prepping !!! Hav mad liggende portionsafmålt i fryseren - et supervåben der er hurtigt at finde frem når man er i nærkamp med cravings. " +
                "I det hele taget SUPERVÅBNET til vægttab"));


        add(new Span("Bemærk at appen beregner et mæthedstal. Tallet udtrykker \"opnået mæthed pr indtaget kalorie\". Tallet ligger mellem 0 og 5. Jo højere tal, jo mere mæthed. Tallet er som regel retvisende, " +
                "men det er klogt lige at prøve en ny ret af, inden man kaster sig ud i at preppe til en måneds aftensmad :-)  "));

        add(new Span("Termisk effekt. Denne app er skrevet, så man kan designe retter, der udnytter den såkaldte \"termiske effekt\". " +
                "Termisk effekt går ud på at kroppen bruger forskellig mængde energi på at forbrænde forskellige fødevarer." +
                "Når man spiser fedt bruges 0-3 procent af den indtagne energi til forbrænding. For kulhydrat er det mellem 5 og 10 procent. " +
                "For protein er det mellem 20 og 30 procent, dvs man opnår en ekstra forbrændings/slankeeffekt ved indtagelse af den samme mængde kalorier, på forskellige fødevarer " +
                "Dog er det vigtigt ikke at gå for lavt på fedtprocenten. Fedt bruges til mange vigtige ting i kroppen og hjernen. " +
                "Så vidt vides må man ikke komme under 20 procent. Og et normalt sundt indtag kan ligge mellem 20 og 30 procent. Det er også vigtigt at få forskellige typer fedt. " +
                "Ifølge nogle kilder kan man få for meget protein, ifølge andre kan man ikke. Tænker at hvis man undgår ekstremer er man \"in the green\" " +
                "Mit vægttab er sket med en brændstofblanding af fedt/protein/kulhydrat på 20/30/50 procent. Se evt: \n" +
                "        https://www.bodylab.dk/shop/en-kalorie-er-1888c1.html\n" +
                "         "));



        add(new Span("NEAT - \"Non-exercise activity thermogenesis\". Et fint udtryk for ikke at ligge i sofaen - men heller ikke være igang med at træne. " +
                "Kort sagt alle mulige småting kan have en kæmpe effekt for ens forbrænding - havearbejde, male bryggerset, cykle til brugsen istedet for bil - osv. " +
                "Eksempel: at stå op at arbejde for en udvikler der ellers ville sidde hele dagen: " +
                "Sidder i 7 timer: 100 kcal/time × 7 timer = 700 kcal" +
                "Står i 7 timer: 120-140 kcal/time × 7 timer = 840-980 kcal - altså procentvis en pæn merforbrænding. " +
                "Så hvis man ændrer en sidde-vane og et par andre småting har man hurtigt et par hundrede kalorier om dagen - \"gratis\" ;-)  "));

        add(new Span("Husk at få nok vand - det ser ud til der sker mindre vægttab hvis man ikke drikker nok"));


        add(new Span("At falde i. Det er en del af gamet. Gå tilbage til plan og sig pyt. Lad være at kompensere, altså træne ekstra eller spise mindre. De mange, mange gange jeg selv er faldet i " +
                "har det handlet om at være ude af balance med kosten og mætheden. Og hvis man kompenserer kommer man nemt til at opretholde en ubalance, hvor man er for sulten. "));




    }
}
