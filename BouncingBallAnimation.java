import javax.swing.*;
import java.awt.*;

/**
 * Лабораторная работа № 4 (Вариант 5)
 * Простое задание: Анимация движения фигуры (движущийся и отскакивающий шар).
 * 
 * Выполнил студент 4 курса ИВТ-А Юраш Максим
 */
public class BouncingBallAnimation extends JPanel {
    private int x = 50;
    private int y = 50;
    private final int diameter = 40;
    private int dx = 4;
    private int dy = 3;
    private final Timer timer;

    public BouncingBallAnimation() {
        setBackground(new Color(25, 25, 35));

        // Таймер анимации (~60 кадров в секунду: 16 мс)
        timer = new Timer(16, e -> {
            x += dx;
            y += dy;

            // Отскок от горизонтальных границ
            if (x <= 0) {
                x = 0;
                dx = -dx;
            } else if (x + diameter >= getWidth()) {
                x = getWidth() - diameter;
                dx = -dx;
            }

            // Отскок от вертикальных границ
            if (y <= 0) {
                y = 0;
                dy = -dy;
            } else if (y + diameter >= getHeight()) {
                y = getHeight() - diameter;
                dy = -dy;
            }

            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Отрисовка красивого градиентного шара
        RadialGradientPaint gradient = new RadialGradientPaint(
                x + diameter / 3f, y + diameter / 3f, diameter / 2f,
                new float[]{0.0f, 0.8f, 1.0f},
                new Color[]{new Color(255, 120, 120), new Color(220, 40, 40), new Color(130, 10, 10)}
        );
        g2d.setPaint(gradient);
        g2d.fillOval(x, y, diameter, diameter);

        // Информационная подсказка
        g2d.setColor(new Color(200, 200, 200));
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        g2d.drawString(String.format("Координаты шара: X=%d, Y=%d (Скорость: dx=%d, dy=%d)", x, y, dx, dy), 15, 25);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Лабораторная работа № 4: Движущийся шар (Вариант 5)");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.add(new BouncingBallAnimation());
            frame.setVisible(true);
        });
    }
}
