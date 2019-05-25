package app.ux;

import app.entities.ETF;
import app.repository.EtfRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A simple example to introduce building forms. As your real application is probably much
 * more complicated than this example, you could re-use this form in multiple places. This
 * example component is only used in MainView.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX.
 */
@SpringComponent
@UIScope
public class EtfEditor extends VerticalLayout implements KeyNotifier {

    private final EtfRepository repository;

    /**
     * The currently edited etf
     */
    private ETF etf;

    /* Fields to edit properties in Customer entity */
    TextArea description = new TextArea("ETF short description");

    TextField exchange = new TextField("Exchange");
    TextField isim = new TextField("ISIM code");
    TextField distributionPolicy = new TextField("Dividend distribution");
    TextField totalExpenseRatio = new TextField("T.E.R.");
    HorizontalLayout basicInfo = new HorizontalLayout(exchange,
            isim, distributionPolicy, totalExpenseRatio);


    TextField distributionFrequency = new TextField("Distribution freq");
    TextField performanceInclDividend = new TextField("Performance");
    TextField weeksLowhigh = new TextField("Low-High");
    TextField volatilityYearInEur = new TextField("Volatility");
    HorizontalLayout performance = new HorizontalLayout(distributionFrequency,
            performanceInclDividend, weeksLowhigh, volatilityYearInEur);


    TextField fundDomicile = new TextField("Country");
    TextField fundSize = new TextField("Size");
    TextField replication = new TextField("Replication");
    TextField fundCurrency = new TextField("Currency");
    TextField currencyRisk = new TextField("Currency risk");
    TextField fundProvider = new TextField("Provider");
    HorizontalLayout otherInfo = new HorizontalLayout(fundDomicile,
            fundSize, replication, fundCurrency, currencyRisk, fundProvider);


    /* Action buttons */
    // TODO why more code?
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<ETF> binder = new Binder<>(ETF.class);
    private ChangeHandler changeHandler;

    @Autowired
    public EtfEditor(EtfRepository repository) {
        this.repository = repository;
        description.setWidthFull();
        add(description, basicInfo, performance, otherInfo, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editETF(etf));
        setVisible(false);
    }

    void delete() {
        repository.delete(etf);
        changeHandler.onChange();
    }

    void save() {
        repository.save(etf);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editETF(ETF c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            etf = repository.findById(c.getId()).get();
        } else {
            etf = c;
        }
        cancel.setVisible(persisted);

        // Bind etf properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(etf);

        setVisible(true);

        // Focus first name initially
        exchange.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }

}
