package cn.yapeteam.ClickUI.component.impl;

import cn.cedo.render.RenderUtil;
import cn.cedo.shader.RoundedUtil;
import cn.langya.font.FontManager;
import cn.yapeteam.ClickUI.ClickUIScreen;
import cn.yapeteam.ClickUI.component.Component;
import cn.yapeteam.ClickUI.component.impl.values.BooleanValueComponent;
import cn.yapeteam.ClickUI.component.impl.values.ModeValueComponent;
import cn.yapeteam.ClickUI.component.impl.values.NumberValueComponent;
import cn.yapeteam.util.AnimationUtils;
import org.union4dev.base.Access;
import org.union4dev.base.value.AbstractValue;
import org.union4dev.base.value.impl.BooleanValue;
import org.union4dev.base.value.impl.ComboValue;
import org.union4dev.base.value.impl.NumberValue;

import java.awt.*;
import java.util.ArrayList;

public class ModuleComponent implements Component {
    public final Class<?> module;
    ArrayList<Component> subComponents = new ArrayList<>();
    float x, y;

    public ModuleComponent(Class<?> mod) {
        this.module = mod;
        for (AbstractValue<?> value : Access.getInstance().getModuleManager().getValues(module)) {
            if (value instanceof BooleanValue) {
                subComponents.add(new BooleanValueComponent((BooleanValue) value));
            } else if (value instanceof NumberValue) {
                subComponents.add(new NumberValueComponent((NumberValue) value));
            } else if (value instanceof ComboValue) {
                subComponents.add(new ModeValueComponent((ComboValue) value));
            }
        }
    }

    private float process = 0;
    private final AnimationUtils ani = new AnimationUtils();

    private Color setAlpha(Color color) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), ClickUIScreen.globalAlpha);
    }

    @Override
    public void draw(float x, float y, float mouseX, float mouseY) {
        this.x = x;
        this.y = y;
        process = ani.animate((Access.getInstance().getModuleManager().isEnabled(module) ? 6 : 0), process, 0.1f);
        float boxwidth = ClickUIScreen.width - ClickUIScreen.leftWidth - ClickUIScreen.rightWidth - 10;
        RoundedUtil.drawRound(x, y, boxwidth, 26, 3, ClickUIScreen.boxColor);
        FontManager.M18.drawString(module.getName(), x + 10, y + 10, Access.getInstance().getModuleManager().isEnabled(module) ? setAlpha(new Color(0)).getRGB() : setAlpha(new Color(131, 131, 131)).getRGB());

        RoundedUtil.drawRound(x + boxwidth - 20, y + 9, 14, 8, 3, Access.getInstance().getModuleManager().isEnabled(module) ? setAlpha(new Color(24, 144, 255)) : setAlpha(new Color(191, 191, 191)));

        RenderUtil.circle(x + boxwidth - 16 + process, y + 13, 3, new Color(255, 255, 255, ClickUIScreen.globalAlpha).getRGB());
    }

    public void drawSubComponents(float x, float y, float mouseX, float mouseY) {
        for (Component component : subComponents) {
            component.draw(x, y, mouseX, mouseY);
            y += 22;
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseX >= x && mouseX <= x + ClickUIScreen.width - ClickUIScreen.leftWidth - ClickUIScreen.rightWidth - 10 && mouseY >= y && mouseY <= y + 27) {
            if (mouseButton == 0 && ClickUIScreen.y + ClickUIScreen.topHeight < mouseY) {
                Access.getInstance().getModuleManager().setEnable(module,!Access.getInstance().getModuleManager().isEnabled(module));
            } else if (mouseButton == 1) {
                if (ClickUIScreen.currentModule == this)
                    ClickUIScreen.currentModule = null;
                else
                    ClickUIScreen.currentModule = this;
            }
        }
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        for (Component component : subComponents) {
            component.mouseReleased(mouseX, mouseY, state);
        }
    }

    public void mouseClickedSubComponents(int mouseX, int mouseY, int mouseButton) {
        for (Component component : subComponents) {
            component.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
}