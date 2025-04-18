package mindustry.arcModule;

import arc.Core;
import arc.func.*;
import arc.graphics.Color;
import arc.input.KeyCode;
import arc.scene.*;
import arc.scene.event.ChangeListener;
import arc.scene.event.ClickListener;
import arc.scene.event.Touchable;
import arc.scene.ui.*;
import arc.scene.ui.Tooltip.*;
import arc.scene.ui.layout.*;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.input.DesktopInput;
import mindustry.ui.Fonts;
import mindustry.ui.Styles;

import static mindustry.gen.Tex.flatDownBase;
import static mindustry.gen.Tex.pane;
import static mindustry.ui.Styles.flatDown;
import static mindustry.ui.Styles.flatOver;

public class ElementUtils {

    public static TextButton.TextButtonStyle textStyle = new TextButton.TextButtonStyle() {{
        down = flatOver;
        up = pane;
        over = flatDownBase;
        font = Fonts.def;
        fontColor = Color.white;
        disabledFontColor = Color.gray;
        checked = flatDown;
    }}, NCtextStyle = new TextButton.TextButtonStyle() {{
                down = flatOver;
                up = pane;
                over = flatDownBase;
                font = Fonts.def;
                fontColor = Color.white;
                disabledFontColor = Color.gray;
            }};

    public static <T extends Element> T tooltip(T element, String text) {
        return tooltip(element, text, true);
    }

    public static <T extends Element> T tooltip(T element, String text, boolean allowMobile) {
        Tooltip tooltip = Tooltips.getInstance().create(text);
        tooltip.allowMobile = allowMobile;

        element.addListener(tooltip);
        addKey(element);
        return element;
    }

    public static <T extends Element> T tooltip(T element, Cons<Table> builder) {
        return tooltip(element, builder, true);
    }

    public static <T extends Element> T tooltip(T element, Cons<Table> builder, boolean allowMobile) {
        Tooltip tooltip = new Tooltip(builder);
        tooltip.allowMobile = allowMobile;

        element.addListener(tooltip);
        return element;
    }

    public static <T extends Element> void addKey(T element) {
        if (Vars.control.input instanceof DesktopInput && element instanceof Button button) {
            button.getListeners().each(tooltip -> {
                if (tooltip instanceof Tooltip tooltip1) {
                    tooltip1.container.getCells().each(cell -> {
                        if (cell.get() instanceof Label label) {

                            String string = label.getText().toString();
                            //初始化按钮快捷键配置
                            button.getListeners().each(eventListener -> {
                                Runnable run = null;

                                if (eventListener instanceof ChangeListener changeListener) {
                                    run = () -> changeListener.changed(null, null);
                                }
                                if (eventListener instanceof ClickListener clickListener) {
                                    run = () -> clickListener.clicked(null, 0, 0);
                                }
                                if (run != null) {
                                    String s = Core.settings.getString(string, "[]");
                                    Seq<KeyCode> keyCodes = SimpleKeystrokes.string2KeyCode(s);
                                    SimpleKeystrokes.INSTANCE.initKey(string, keyCodes, run);
                                }

                            });
                            //右键打开快捷键设置
                            ClickListener clicked = element.clicked(KeyCode.mouseRight, () -> {
                                SimpleKeystrokes.SetKeyGui.INSTANCE.openDialog(string);
                            });
                            button.addListener(clicked);
                        }
                    });

                }
            });
        }
    }

    public static Table arcSliderTable(Table table, String name, float def, float min, float max, float step, StringProFloat s, Cons<Float> cons) {
        Slider slider = new Slider(min, max, step, false);

        slider.setValue(def);

        Label value = new Label("", Styles.outlineLabel);
        Table content = new Table();
        content.add(name, Styles.outlineLabel).left().growX().wrap();
        content.add(value).padLeft(10f).right();
        content.margin(3f, 33f, 3f, 33f);
        content.touchable = Touchable.disabled;

        slider.changed(() -> {
            cons.get(slider.getValue());
            value.setText(s.get(slider.getValue()));
        });

        slider.change();

        table.stack(slider, content).width(Math.min(Core.graphics.getWidth() / 1.2f, 460f)).left().padTop(4f).get();
        table.row();
        return table;
    }

    public static Table arcSliderTableInt(Table table, String name, int def, int min, int max, int step, StringProInt s, Cons<Integer> cons) {
        Slider slider = new Slider(min, max, step, false);

        slider.setValue(def);

        Label value = new Label("", Styles.outlineLabel);
        Table content = new Table();
        content.add(name, Styles.outlineLabel).left().growX().wrap();
        content.add(value).padLeft(10f).right();
        content.margin(3f, 33f, 3f, 33f);
        content.touchable = Touchable.disabled;

        slider.changed(() -> {
            cons.get((int) slider.getValue());
            value.setText(s.get((int) slider.getValue()));
        });

        slider.change();

        table.stack(slider, content).width(Math.min(Core.graphics.getWidth() / 1.2f, 460f)).left().padTop(4f).get();
        table.row();
        return table;
    }

    public interface StringProFloat {
        String get(float i);
    }

    public interface StringProInt {
        String get(int i);
    }

    public static abstract class ToolTable extends Table {
        public String icon = "";
        public boolean expand = false;



        public void rebuild(){
            clear();
            table().growX().left();
            if (expand) {
                buildTable();
            }
            button((expand ? "":"[lightgray]") + icon, textStyle, () -> {
                expand = !expand;
                rebuild();
            }).right().width(40f).minHeight(40f).fillY();
        }

        protected abstract void buildTable();
    }

}
