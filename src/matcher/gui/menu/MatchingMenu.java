package matcher.gui.menu;

import java.util.EnumSet;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import matcher.Matcher.MatchingStatus;
import matcher.gui.Gui;
import matcher.type.MatchType;

public class MatchingMenu extends Menu {
	MatchingMenu(Gui gui) {
		super("Matching");

		this.gui = gui;

		init();
	}

	private void init() {
		MenuItem menuItem = new MenuItem("Auto match all");
		getItems().add(menuItem);
		menuItem.setOnAction(event -> autoMatchAll());

		getItems().add(new SeparatorMenuItem());

		menuItem = new MenuItem("Auto class match");
		getItems().add(menuItem);
		menuItem.setOnAction(event -> autoMatchClasses());

		menuItem = new MenuItem("Auto method match");
		getItems().add(menuItem);
		menuItem.setOnAction(event -> autoMatchMethods());

		menuItem = new MenuItem("Auto field match");
		getItems().add(menuItem);
		menuItem.setOnAction(event -> autoMatchFields());

		menuItem = new MenuItem("Auto method arg match");
		getItems().add(menuItem);
		menuItem.setOnAction(event -> autoMatchArgs());

		menuItem = new MenuItem("Auto method var match");
		getItems().add(menuItem);
		menuItem.setOnAction(event -> autoMatchVars());

		getItems().add(new SeparatorMenuItem());

		menuItem = new MenuItem("Status (A)");
		getItems().add(menuItem);
		menuItem.setOnAction(event -> showMatchingStatus("A", gui.getMatcher().getStatusA(true)));
		
		menuItem = new MenuItem("Status (B)");
		getItems().add(menuItem);
		menuItem.setOnAction(event -> showMatchingStatus("B", gui.getMatcher().getStatusB(true)));
	}

	public void autoMatchAll() {
		gui.runProgressTask(
				"Auto matching...",
				gui.getMatcher()::autoMatchAll,
				() -> gui.onMatchChange(EnumSet.allOf(MatchType.class)),
				Throwable::printStackTrace);
	}

	public void autoMatchClasses() {
		gui.runProgressTask(
				"Auto matching classes...",
				gui.getMatcher()::autoMatchClasses,
				() -> gui.onMatchChange(EnumSet.allOf(MatchType.class)),
				Throwable::printStackTrace);
	}

	public void autoMatchMethods() {
		gui.runProgressTask(
				"Auto matching methods...",
				gui.getMatcher()::autoMatchMethods,
				() -> gui.onMatchChange(EnumSet.of(MatchType.Method)),
				Throwable::printStackTrace);
	}

	public void autoMatchFields() {
		gui.runProgressTask(
				"Auto matching fields...",
				gui.getMatcher()::autoMatchFields,
				() -> gui.onMatchChange(EnumSet.of(MatchType.Field)),
				Throwable::printStackTrace);
	}

	public void autoMatchArgs() {
		gui.runProgressTask(
				"Auto matching method args...",
				gui.getMatcher()::autoMatchMethodArgs,
				() -> gui.onMatchChange(EnumSet.of(MatchType.MethodVar)),
				Throwable::printStackTrace);
	}

	public void autoMatchVars() {
		gui.runProgressTask(
				"Auto matching method vars...",
				gui.getMatcher()::autoMatchMethodVars,
				() -> gui.onMatchChange(EnumSet.of(MatchType.MethodVar)),
				Throwable::printStackTrace);
	}

	public void showMatchingStatus(String name, MatchingStatus status) {
		gui.showAlert(AlertType.INFORMATION, "Matching status", "Current matching status (" + name + ")",
				String.format("Classes: %d / %d (%.2f%%)%nMethods: %d / %d (%.2f%%)%nFields: %d / %d (%.2f%%)%nMethod args: %d / %d (%.2f%%)%nMethod vars: %d / %d (%.2f%%)",
						status.matchedClassCount, status.totalClassCount, (status.totalClassCount == 0 ? 0 : 100. * status.matchedClassCount / status.totalClassCount),
						status.matchedMethodCount, status.totalMethodCount, (status.totalMethodCount == 0 ? 0 : 100. * status.matchedMethodCount / status.totalMethodCount),
						status.matchedFieldCount, status.totalFieldCount, (status.totalFieldCount == 0 ? 0 : 100. * status.matchedFieldCount / status.totalFieldCount),
						status.matchedMethodArgCount, status.totalMethodArgCount, (status.totalMethodArgCount == 0 ? 0 : 100. * status.matchedMethodArgCount / status.totalMethodArgCount),
						status.matchedMethodVarCount, status.totalMethodVarCount, (status.totalMethodVarCount == 0 ? 0 : 100. * status.matchedMethodVarCount / status.totalMethodVarCount)
						));
	}

	private final Gui gui;
}
