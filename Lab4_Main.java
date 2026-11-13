import javax.swing.*;
import java.awt.*;

/**
 * Главный запускаемый класс для Лабораторной работы № 4 (Вариант 5).
 * Выполнил студент 4 курса ИВТ-А Юраш Максим
 */
public class Lab4_Main extends JFrame {

    public Lab4_Main() {
        setTitle("Лабораторная работа № 4: Графика в Java (Вариант 5)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(440, 330);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel title = new JLabel("Выберите графическое приложение:", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JButton btn1 = new JButton("1. Движущийся шар (Простая анимация)");
        JButton btn2 = new JButton("2. Падающие снежинки (Сложная анимация)");
        JButton btn3 = new JButton("3. Игра «Крестики-нолики» (GUI-игра)");

        btn1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn3.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        btn1.addActionListener(e -> BouncingBallAnimation.main(new String[0]));
        btn2.addActionListener(e -> SnowfallAnimation.main(new String[0]));
        btn3.addActionListener(e -> TicTacToeGame.main(new String[0]));

        panel.add(title);
        panel.add(btn1);
        panel.add(btn2);
        panel.add(btn3);

        add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new Lab4_Main().setVisible(true);
        });
    }
}
