package jcaster_vaadin;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class Gui2 extends CustomComponent {

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private TabSheet tabSheet_1;
	@AutoGenerated
	private Panel panel_3;
	@AutoGenerated
	private VerticalLayout verticalLayout_4;
	@AutoGenerated
	private GridLayout gridLayout_5;
	@AutoGenerated
	private NativeSelect nativeSelect_6;
	@AutoGenerated
	private Label label_11;
	@AutoGenerated
	private NativeSelect nativeSelect_5;
	@AutoGenerated
	private Label label_10;
	@AutoGenerated
	private NativeSelect nativeSelect_4;
	@AutoGenerated
	private Label label_9;
	@AutoGenerated
	private Panel panel_2;
	@AutoGenerated
	private VerticalLayout verticalLayout_3;
	@AutoGenerated
	private GridLayout gridLayout_4;
	@AutoGenerated
	private NativeSelect nativeSelect_3;
	@AutoGenerated
	private Label label_8;
	@AutoGenerated
	private NativeSelect nativeSelect_2;
	@AutoGenerated
	private Label label_7;
	@AutoGenerated
	private Panel panel_1;
	@AutoGenerated
	private VerticalLayout verticalLayout_2;
	@AutoGenerated
	private GridLayout gridLayout_3;
	@AutoGenerated
	private TextField textField_4;
	@AutoGenerated
	private Label label_4;
	@AutoGenerated
	private TextField textField_3;
	@AutoGenerated
	private Label label_3;
	@AutoGenerated
	private NativeSelect nativeSelect_1;
	@AutoGenerated
	private Label label_5;
	@AutoGenerated
	private NativeButton nativeButton_6;
	@AutoGenerated
	private TextField textField_2;
	@AutoGenerated
	private Label label_2;
	@AutoGenerated
	private TextField textField_5;
	@AutoGenerated
	private Label label_1;
	@AutoGenerated
	private HorizontalLayout horizontalLayout_1;
	@AutoGenerated
	private NativeButton nativeButton_3;
	@AutoGenerated
	private NativeButton nativeButton_4;
	@AutoGenerated
	private NativeButton nativeButton_5;
	@AutoGenerated
	private MenuBar menuBar_1;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public Gui2() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		
		// top-level component properties
		setWidth("400px");
		setHeight("340px");
		
		// menuBar_1
		menuBar_1 = new MenuBar();
		menuBar_1.setWidth("100.0%");
		menuBar_1.setHeight("-1px");
		menuBar_1.setImmediate(false);
		mainLayout.addComponent(menuBar_1);
		
		// horizontalLayout_1
		horizontalLayout_1 = buildHorizontalLayout_1();
		mainLayout.addComponent(horizontalLayout_1);
		
		// tabSheet_1
		tabSheet_1 = buildTabSheet_1();
		mainLayout.addComponent(tabSheet_1);
		mainLayout.setExpandRatio(tabSheet_1, 1.0f);
		mainLayout.setComponentAlignment(tabSheet_1, new Alignment(48));
		
		return mainLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalLayout_1() {
		// common part: create layout
		horizontalLayout_1 = new HorizontalLayout();
		horizontalLayout_1.setWidth("100.0%");
		horizontalLayout_1.setHeight("60px");
		horizontalLayout_1.setImmediate(false);
		horizontalLayout_1.setMargin(false);
		horizontalLayout_1.setSpacing(true);
		
		// nativeButton_5
		nativeButton_5 = new NativeButton();
		nativeButton_5.setWidth("100.0%");
		nativeButton_5.setHeight("40px");
		nativeButton_5.setCaption("Record");
		nativeButton_5.setImmediate(true);
		horizontalLayout_1.addComponent(nativeButton_5);
		horizontalLayout_1.setExpandRatio(nativeButton_5, 1.0f);
		horizontalLayout_1.setComponentAlignment(nativeButton_5, new Alignment(
				33));
		
		// nativeButton_4
		nativeButton_4 = new NativeButton();
		nativeButton_4.setWidth("100.0%");
		nativeButton_4.setHeight("40px");
		nativeButton_4.setCaption("Pause");
		nativeButton_4.setImmediate(true);
		horizontalLayout_1.addComponent(nativeButton_4);
		horizontalLayout_1.setExpandRatio(nativeButton_4, 1.0f);
		horizontalLayout_1.setComponentAlignment(nativeButton_4, new Alignment(
				48));
		
		// nativeButton_3
		nativeButton_3 = new NativeButton();
		nativeButton_3.setWidth("100.0%");
		nativeButton_3.setHeight("40px");
		nativeButton_3.setCaption("Stop");
		nativeButton_3.setImmediate(true);
		horizontalLayout_1.addComponent(nativeButton_3);
		horizontalLayout_1.setExpandRatio(nativeButton_3, 1.0f);
		horizontalLayout_1.setComponentAlignment(nativeButton_3, new Alignment(
				34));
		
		return horizontalLayout_1;
	}

	@AutoGenerated
	private TabSheet buildTabSheet_1() {
		// common part: create layout
		tabSheet_1 = new TabSheet();
		tabSheet_1.setWidth("100.0%");
		tabSheet_1.setHeight("100.0%");
		tabSheet_1.setImmediate(true);
		
		// panel_1
		panel_1 = buildPanel_1();
		tabSheet_1.addTab(panel_1, "General settings", null);
		
		// panel_2
		panel_2 = buildPanel_2();
		tabSheet_1.addTab(panel_2, "Video settings", null);
		
		// panel_3
		panel_3 = buildPanel_3();
		tabSheet_1.addTab(panel_3, "Audio settings", null);
		
		return tabSheet_1;
	}

	@AutoGenerated
	private Panel buildPanel_1() {
		// common part: create layout
		panel_1 = new Panel();
		panel_1.setWidth("100.0%");
		panel_1.setHeight("100.0%");
		panel_1.setImmediate(false);
		
		// verticalLayout_2
		verticalLayout_2 = buildVerticalLayout_2();
		panel_1.setContent(verticalLayout_2);
		
		return panel_1;
	}

	@AutoGenerated
	private VerticalLayout buildVerticalLayout_2() {
		// common part: create layout
		verticalLayout_2 = new VerticalLayout();
		verticalLayout_2.setWidth("100.0%");
		verticalLayout_2.setHeight("100.0%");
		verticalLayout_2.setImmediate(false);
		verticalLayout_2.setMargin(false);
		
		// gridLayout_3
		gridLayout_3 = buildGridLayout_3();
		verticalLayout_2.addComponent(gridLayout_3);
		verticalLayout_2.setComponentAlignment(gridLayout_3, new Alignment(48));
		
		return verticalLayout_2;
	}

	@AutoGenerated
	private GridLayout buildGridLayout_3() {
		// common part: create layout
		gridLayout_3 = new GridLayout();
		gridLayout_3.setWidth("100.0%");
		gridLayout_3.setHeight("100.0%");
		gridLayout_3.setImmediate(false);
		gridLayout_3.setMargin(false);
		gridLayout_3.setSpacing(true);
		gridLayout_3.setColumns(4);
		gridLayout_3.setRows(7);
		
		// label_1
		label_1 = new Label();
		label_1.setWidth("-1px");
		label_1.setHeight("-1px");
		label_1.setValue("Filename");
		label_1.setImmediate(false);
		gridLayout_3.addComponent(label_1, 0, 1);
		
		// textField_5
		textField_5 = new TextField();
		textField_5.setWidth("-1px");
		textField_5.setHeight("-1px");
		textField_5.setImmediate(false);
		gridLayout_3.addComponent(textField_5, 1, 1);
		
		// label_2
		label_2 = new Label();
		label_2.setWidth("-1px");
		label_2.setHeight("-1px");
		label_2.setValue("Save location");
		label_2.setImmediate(false);
		gridLayout_3.addComponent(label_2, 0, 2);
		
		// textField_2
		textField_2 = new TextField();
		textField_2.setWidth("-1px");
		textField_2.setHeight("-1px");
		textField_2.setImmediate(false);
		gridLayout_3.addComponent(textField_2, 1, 2);
		
		// nativeButton_6
		nativeButton_6 = new NativeButton();
		nativeButton_6.setWidth("-1px");
		nativeButton_6.setHeight("-1px");
		nativeButton_6.setCaption("Browse...");
		nativeButton_6.setImmediate(true);
		gridLayout_3.addComponent(nativeButton_6, 2, 2);
		
		// label_5
		label_5 = new Label();
		label_5.setWidth("-1px");
		label_5.setHeight("-1px");
		label_5.setValue("Record type");
		label_5.setImmediate(false);
		gridLayout_3.addComponent(label_5, 0, 3);
		
		// nativeSelect_1
		nativeSelect_1 = new NativeSelect();
		nativeSelect_1.setWidth("-1px");
		nativeSelect_1.setHeight("-1px");
		nativeSelect_1.setImmediate(false);
		gridLayout_3.addComponent(nativeSelect_1, 1, 3);
		
		// label_3
		label_3 = new Label();
		label_3.setWidth("-1px");
		label_3.setHeight("-1px");
		label_3.setValue("Recording countdown");
		label_3.setImmediate(false);
		gridLayout_3.addComponent(label_3, 0, 4);
		
		// textField_3
		textField_3 = new TextField();
		textField_3.setWidth("-1px");
		textField_3.setHeight("-1px");
		textField_3.setImmediate(false);
		gridLayout_3.addComponent(textField_3, 1, 4);
		
		// label_4
		label_4 = new Label();
		label_4.setWidth("-1px");
		label_4.setHeight("-1px");
		label_4.setValue("Duration of record");
		label_4.setImmediate(false);
		gridLayout_3.addComponent(label_4, 0, 5);
		
		// textField_4
		textField_4 = new TextField();
		textField_4.setWidth("-1px");
		textField_4.setHeight("-1px");
		textField_4.setImmediate(false);
		gridLayout_3.addComponent(textField_4, 1, 5);
		
		return gridLayout_3;
	}

	@AutoGenerated
	private Panel buildPanel_2() {
		// common part: create layout
		panel_2 = new Panel();
		panel_2.setWidth("100.0%");
		panel_2.setHeight("100.0%");
		panel_2.setImmediate(false);
		
		// verticalLayout_3
		verticalLayout_3 = buildVerticalLayout_3();
		panel_2.setContent(verticalLayout_3);
		
		return panel_2;
	}

	@AutoGenerated
	private VerticalLayout buildVerticalLayout_3() {
		// common part: create layout
		verticalLayout_3 = new VerticalLayout();
		verticalLayout_3.setWidth("100.0%");
		verticalLayout_3.setHeight("100.0%");
		verticalLayout_3.setImmediate(false);
		verticalLayout_3.setMargin(false);
		
		// gridLayout_4
		gridLayout_4 = buildGridLayout_4();
		verticalLayout_3.addComponent(gridLayout_4);
		
		return verticalLayout_3;
	}

	@AutoGenerated
	private GridLayout buildGridLayout_4() {
		// common part: create layout
		gridLayout_4 = new GridLayout();
		gridLayout_4.setWidth("100.0%");
		gridLayout_4.setHeight("100.0%");
		gridLayout_4.setImmediate(false);
		gridLayout_4.setMargin(false);
		gridLayout_4.setSpacing(true);
		gridLayout_4.setColumns(5);
		gridLayout_4.setRows(7);
		
		// label_7
		label_7 = new Label();
		label_7.setWidth("-1px");
		label_7.setHeight("-1px");
		label_7.setValue("Format");
		label_7.setImmediate(false);
		gridLayout_4.addComponent(label_7, 0, 1);
		
		// nativeSelect_2
		nativeSelect_2 = new NativeSelect();
		nativeSelect_2.setWidth("-1px");
		nativeSelect_2.setHeight("-1px");
		nativeSelect_2.setImmediate(false);
		gridLayout_4.addComponent(nativeSelect_2, 1, 1);
		
		// label_8
		label_8 = new Label();
		label_8.setWidth("-1px");
		label_8.setHeight("-1px");
		label_8.setValue("Codec");
		label_8.setImmediate(false);
		gridLayout_4.addComponent(label_8, 0, 2);
		
		// nativeSelect_3
		nativeSelect_3 = new NativeSelect();
		nativeSelect_3.setWidth("-1px");
		nativeSelect_3.setHeight("-1px");
		nativeSelect_3.setImmediate(false);
		gridLayout_4.addComponent(nativeSelect_3, 1, 2);
		
		return gridLayout_4;
	}

	@AutoGenerated
	private Panel buildPanel_3() {
		// common part: create layout
		panel_3 = new Panel();
		panel_3.setWidth("100.0%");
		panel_3.setHeight("100.0%");
		panel_3.setImmediate(false);
		
		// verticalLayout_4
		verticalLayout_4 = buildVerticalLayout_4();
		panel_3.setContent(verticalLayout_4);
		
		return panel_3;
	}

	@AutoGenerated
	private VerticalLayout buildVerticalLayout_4() {
		// common part: create layout
		verticalLayout_4 = new VerticalLayout();
		verticalLayout_4.setWidth("100.0%");
		verticalLayout_4.setHeight("100.0%");
		verticalLayout_4.setImmediate(false);
		verticalLayout_4.setMargin(false);
		
		// gridLayout_5
		gridLayout_5 = buildGridLayout_5();
		verticalLayout_4.addComponent(gridLayout_5);
		
		return verticalLayout_4;
	}

	@AutoGenerated
	private GridLayout buildGridLayout_5() {
		// common part: create layout
		gridLayout_5 = new GridLayout();
		gridLayout_5.setWidth("100.0%");
		gridLayout_5.setHeight("100.0%");
		gridLayout_5.setImmediate(false);
		gridLayout_5.setMargin(false);
		gridLayout_5.setSpacing(true);
		gridLayout_5.setColumns(4);
		gridLayout_5.setRows(7);
		
		// label_9
		label_9 = new Label();
		label_9.setWidth("-1px");
		label_9.setHeight("-1px");
		label_9.setValue("Channels");
		label_9.setImmediate(false);
		gridLayout_5.addComponent(label_9, 0, 1);
		
		// nativeSelect_4
		nativeSelect_4 = new NativeSelect();
		nativeSelect_4.setWidth("-1px");
		nativeSelect_4.setHeight("-1px");
		nativeSelect_4.setImmediate(false);
		gridLayout_5.addComponent(nativeSelect_4, 1, 1);
		
		// label_10
		label_10 = new Label();
		label_10.setWidth("-1px");
		label_10.setHeight("-1px");
		label_10.setValue("Sample size");
		label_10.setImmediate(false);
		gridLayout_5.addComponent(label_10, 0, 2);
		
		// nativeSelect_5
		nativeSelect_5 = new NativeSelect();
		nativeSelect_5.setWidth("-1px");
		nativeSelect_5.setHeight("-1px");
		nativeSelect_5.setImmediate(false);
		gridLayout_5.addComponent(nativeSelect_5, 1, 2);
		
		// label_11
		label_11 = new Label();
		label_11.setWidth("-1px");
		label_11.setHeight("-1px");
		label_11.setValue("Sample rate");
		label_11.setImmediate(false);
		gridLayout_5.addComponent(label_11, 0, 3);
		
		// nativeSelect_6
		nativeSelect_6 = new NativeSelect();
		nativeSelect_6.setWidth("-1px");
		nativeSelect_6.setHeight("-1px");
		nativeSelect_6.setImmediate(false);
		gridLayout_5.addComponent(nativeSelect_6, 1, 3);
		
		return gridLayout_5;
	}

}
