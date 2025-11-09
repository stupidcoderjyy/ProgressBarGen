package pbg;

import io.qt.core.Qt;
import io.qt.gui.*;
import stupidcoder.util.cmd.ArgNode;
import stupidcoder.util.cmd.Programme;
import stupidcoder.util.cmd.ReturnActions;

import java.util.ArrayList;
import java.util.List;

public class Generator extends Programme {
    private final List<Area> areas =  new ArrayList<>();
    private Config config;

    public Generator() throws Exception {
        super("pbg");
        config = Config.load("config.json");
    }

    @Override
    protected void registerNode(ArgNode root) {
        root.addChild("exit", (var it) -> ReturnActions.EXIT);
        root.addChild("gen", (var it) -> {
            gen(it.hasNext() ? it.next() : "");
            System.out.println("success");
            return ReturnActions.END;
        });
        root.addChild("add", (var it) -> {
            addArea(Integer.parseInt(it.next()), it.next());
            return ReturnActions.END;
        });
        root.addChild("clear", (var it) -> {
            areas.clear();
            return ReturnActions.END;
        });
        root.addChild("fonts", (var it) -> {
            QFontDatabase.families().forEach(System.out::println);
            return ReturnActions.END;
        });
        ArgNode nodeCfg = root.addChild("config", (var it) -> ReturnActions.CONTINUE);
        nodeCfg.addChild("reload", (var it) -> {
            try {
                config = Config.load("config.json");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return ReturnActions.END;
        });
        nodeCfg.addChild("show", (var it) -> {
            System.out.println(config.toString());
            return ReturnActions.END;
        });
    }

    public void addArea(int frame, String title) {
        areas.add(new Area(frame, title));
    }

    public void gen(String filePath) {
        if (areas.isEmpty()) {
            throw new RuntimeException("Empty");
        }
        QPixmap map = new QPixmap(config.getBarWidth(), config.getBarHeight());
        map.fill(QColor.fromRgba(0));
        QPainter painter = new QPainter(map);
        painter.setRenderHint(QPainter.RenderHint.Antialiasing);
        painter.setRenderHint(QPainter.RenderHint.TextAntialiasing);
        painter.setRenderHint(QPainter.RenderHint.SmoothPixmapTransform);

        int max_frame = areas.get(areas.size() - 1).frame();
        int x1 = 0;
        for (Area area : areas) {
            int x2 = Math.round((float) area.frame() / max_frame * config.getBarWidth());
            paintArea(painter, area, config, x1, x2);
            x1 = x2;
        }
        map.save((filePath.isEmpty() ? config.getOutPath() : filePath) + "\\bar.png");
    }

    private void paintArea(QPainter painter, Area area, Config config, int x1, int x2) {
        int dx = 1, dy = 10;
        painter.fillRect(Math.max(0, x1 - dx), dy, dx << 1, config.getBarHeight() - (dy << 1), config.getLineColor());
        painter.fillRect(Math.min(config.getBarWidth() - (dx << 1), x2 - dx), dy, dx << 1, config.getBarHeight() - (dy << 1), config.getLineColor());
        QPen pen = new QPen();
        pen.setColor(config.getTextColor());
        painter.setFont(new QFont(config.getFontFamily(), config.getFontSize()));
        painter.setPen(pen);
        painter.drawText(x1, 0, x2 - x1, config.getBarHeight(), Qt.AlignmentFlag.AlignCenter.value(), area.name());
    }
}
