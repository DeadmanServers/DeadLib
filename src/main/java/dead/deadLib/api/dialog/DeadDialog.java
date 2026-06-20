package dead.deadLib.api.dialog;


import dead.deadLib.api.text.MyMini;
import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.body.PlainMessageDialogBody;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.input.TextDialogInput;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.kyori.adventure.text.event.ClickCallback;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SuppressWarnings("UnstableApiUsage")
public class DeadDialog {
    
    private final String title;
    private final List<String> bodyLines = new ArrayList<>();
    private final List<TextInput> inputs = new ArrayList<>();
    private String confirmLabel;
    private Consumer<DialogContext> confirmHandler;
    private String cancelLabel = "Cancel";

    private record TextInput(String key, String label) {}

    private DeadDialog(String title) { this.title = title; }
    public static DeadDialog create(String title) {
        return new DeadDialog(title);
    }

    public DeadDialog body(String line) {
        bodyLines.add(line);
        return this;
    }
    public DeadDialog text(String key) {
        inputs.add(new TextInput(key, key));
        return this;
    }
    public DeadDialog text(String key, String label) {
        inputs.add(new TextInput(key, label));
        return this;
    }
    public DeadDialog confirm(String label, Consumer<DialogContext> confirmHandler) {
        this.confirmHandler = confirmHandler;
        this.confirmLabel = label;
        return this;
    }
    public DeadDialog cancel(String label) {
        this.cancelLabel = label;
        return this;
    }

    public void show(Player player) {
        List<PlainMessageDialogBody> body = bodyLines.stream()
                .map(line -> DialogBody.plainMessage(MyMini.normalizeComp(line)))
                .toList();

        List<TextDialogInput> dialogInputs = inputs.stream()
                .map(in -> DialogInput.text(in.key(), MyMini.normalizeComp(in.label())).build())
                .toList();

        ActionButton confirmBtn = ActionButton.builder(MyMini.normalizeComp(confirmLabel))
                .action(DialogAction.customClick(
                        (response, audience) -> confirmHandler.accept(new DialogContext(player, response)),
                        ClickCallback.Options.builder().build()))
                .build();

        ActionButton cancelBtn = ActionButton.builder(MyMini.normalizeComp(cancelLabel)).build();

        DialogBase base = DialogBase.builder(MyMini.normalizeComp(title))
                .body(body)
                .inputs(dialogInputs)
                .build();

        Dialog dialog = Dialog.create(b -> b.empty()
                .base(base)
                .type(DialogType.confirmation(confirmBtn, cancelBtn)));

        player.showDialog(dialog);
    }
}
