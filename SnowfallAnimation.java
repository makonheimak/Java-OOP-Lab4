import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Лабораторная работа № 4 (Вариант 5)
 * Сложное задание: Анимация падающих снежинок (Snowfall).
 * 
 * Выполнил студент 4 курса ИВТ-А Юраш Максим
 */
public class SnowfallAnimation extends JPanel {

    static class Snowflake {
        float x, y;
        float radius;
        float speed;
        float swaySpeed;
        float swayOffset;
        int alpha;

        public Snowflake(int width, int height, Random rand) {
            reset(width, height, rand, true);
        }

        public void reset(int width, int height, Random rand, boolean initial) {
            x = rand.nextInt(Math.max(1, width));
            y = initial ? rand.nextInt(Math.max(1, height)) : -10;
            radius = rand.nextFloat() * 4 + 2; // от 2 до 6 px
            speed = rand.nextFloat() * 2.5f + 1.0f; // скорость падения
            swaySpeed = rand.nextFloat() * 0.05f + 0.02f;
            swayOffset = rand.nextFloat() * 100;
            alpha = rand.nextInt(120) + 135; // прозрачность от 135 до 255
        }

        public void update(int width, int height, Random rand) {
            y += speed;
            x += Math.sin(y * swaySpeed + swayOffset) * 0.8;

            if (y > height + 10 || x < -10 || x > width + 10) {
                reset(width, height, rand, false);
            }
        }
    }

    private final List<Snowflake> snowflakes = new ArrayList<>();
    private final Random random = new Random();
    private final Timer timer;

    public SnowfallAnimation() {
        setBackground(new Color(15, 20, 35));

        // Инициализируем 90 снежинок
        for (int i = 0; i < 90; i++) {
            snowflakes.add(new Snowflake(700, 500, random));
        }

        // Клик мыши добавляет горсть новых снежинок
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addBurst(15, e.getX(), e.getY());
            }
        });

        // 60 FPS таймер
        timer = new Timer(16, e -> {
            int w = getWidth();
            int h = getHeight();
            for (Snowflake s : snowflakes) {
                s.update(w, h, random);
            }
            repaint();
        });
        timer.start();
    }

    public void addBurst(int count, int originX, int originY) {
        int w = getWidth() > 0 ? getWidth() : 750;
        int h = getHeight() > 0 ? getHeight() : 500;
        for (int i = 0; i < count; i++) {
            Snowflake s = new Snowflake(w, h, random);
            s.x = originX + random.nextInt(60) - 30;
            s.y = originY + random.nextInt(60) - 30;
            snowflakes.add(s);
        }
        repaint();
    }

    public void addRandomBurst(int count) {
        int w = getWidth() > 0 ? getWidth() : 750;
        int h = getHeight() > 0 ? getHeight() : 500;
        for (int i = 0; i < count; i++) {
            Snowflake s = new Snowflake(w, h, random);
            s.x = random.nextInt(w);
            s.y = random.nextInt(h);
            snowflakes.add(s);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Отрисовка снежинок
        for (Snowflake s : snowflakes) {
            g2d.setColor(new Color(255, 255, 255, s.alpha));
            g2d.fillOval((int) (s.x - s.radius), (int) (s.y - s.radius), (int) (s.radius * 2), (int) (s.radius * 2));
        }

        // Информационная плашка
        g2d.setColor(new Color(220, 230, 255));
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
        g2d.drawString(String.format("Снегопад: %d снежинок (кликните мышью для добавления)", snowflakes.size()), 20, 30);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Лабораторная работа № 4: Падающие снежинки (Вариант 5)");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(750, 500);
            frame.setLocationRelativeTo(null);
            SnowfallAnimation panel = new SnowfallAnimation();
            if (args.length > 0 && args[0].equals("--burst")) {
                panel.addRandomBurst(45); // Симуляция добавления снежинок (итог: 135 снежинок)
            }
            frame.add(panel);
            frame.setVisible(true);
        });
    }
}
