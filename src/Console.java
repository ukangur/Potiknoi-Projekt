import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class Console extends BorderPane {
    protected final TextArea textArea = new TextArea();
    protected final TextField textField = new TextField();

    protected final List<String> history = new ArrayList<>();
    protected int historyPointer = 0;

    private Consumer<String> onMessageReceivedHandler;

    public Console() {
        textArea.setEditable(false);
        setCenter(textArea);

        textField.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case ENTER:
                    String text = textField.getText();
                    textArea.appendText(text + System.lineSeparator());
                    history.add(text);
                    historyPointer++;
                    if (onMessageReceivedHandler != null) {
                        onMessageReceivedHandler.accept(text);
                    }
                    textField.clear();
                    break;
                case UP:
                    if (historyPointer == 0) {
                        break;
                    }
                    historyPointer--;
                    GUIUtils.runSafe(() -> {
                        textField.setText(history.get(historyPointer));
                        textField.selectAll();
                    });
                    break;
                case DOWN:
                    if (historyPointer == history.size() - 1) {
                        break;
                    }
                    historyPointer++;
                    GUIUtils.runSafe(() -> {
                        textField.setText(history.get(historyPointer));
                        textField.selectAll();
                    });
                    break;
                default:
                    break;
            }
        });
        setBottom(textField);
    }

    @Override
    public void requestFocus() {
        super.requestFocus();
        textField.requestFocus();
    }

    public void setOnMessageReceivedHandler(final Consumer<String> onMessageReceivedHandler) {
        this.onMessageReceivedHandler = onMessageReceivedHandler;
    }

    public void clear() {
        GUIUtils.runSafe(() -> textArea.clear());
    }

    public void print(final String text) {
        Objects.requireNonNull(text, "text");
        GUIUtils.runSafe(() -> textArea.appendText(text));
    }

    public void println(final String text) {
        Objects.requireNonNull(text, "text");
        GUIUtils.runSafe(() -> textArea.appendText(text + System.lineSeparator()));
    }

    public void println() {
        GUIUtils.runSafe(() -> textArea.appendText(System.lineSeparator()));
    }
}