package app.ux;

import app.entities.ETF;
import app.repository.EtfRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

@Route
public class MainView extends VerticalLayout {

    private final EtfRepository repository;
    private final EtfEditor editor;
    final Grid<ETF> grid;
    final TextField filter;


    public MainView(EtfRepository repository, EtfEditor editor) {
        this.repository = repository;
        this.editor = editor;
        this.grid = new Grid<>(ETF.class);
        this.filter = new TextField();

        // build layout
        add(filter, grid, editor);

        filter.setPlaceholder("Search...");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listCustomers(e.getValue()));

//        grid.setHeight("500px");
        grid.setHeightByRows(true);
        grid.setColumns("description", "exchange", "totalExpenseRatio");
        grid.getColumnByKey("description").setWidth("1000px").setFlexGrow(0);
        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editETF(e.getValue());
        });


        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listCustomers(filter.getValue());
        });

        // Initialize listing
        listCustomers(null);
    }

    // tag::listCustomers[]
    void listCustomers(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repository.findAllByOrderByTotalExpenseRatioAsc());
        } else {
            grid.setItems(repository.findByDescriptionContainsIgnoreCaseOrExchangeContainsIgnoreCase(filterText, filterText));
        }
    }
    // end::listCustomers[]

}
