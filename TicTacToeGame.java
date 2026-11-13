import javax.swing.*;
import java.awt.*;

/**
 * Лабораторная работа № 4 (Вариант 5)
 * Дополнительное графическое задание: Оконная игра «Крестики-Нолики» на Swing.
 * 
 * Выполнил студент 4 курса ИВТ-А Юраш Максим
 */
public class TicTacToeGame extends JFrame {
    private final JButton[][] buttons = new JButton[3][3];
    private boolean xTurn = true;
    private final JLabel statusLabel;
    private int scoreX = 0;
    private int scoreO = 0;
    private int scoreDraws = 0;
    private final JLabel scoreLabel;

    public TicTacToeGame() {
        setTitle("Лабораторная работа № 4: Игра «Крестики-Нолики» (Вариант 5)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Панель статуса и счета
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(new Color(245, 245, 250));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        statusLabel = new JLabel("Ход игрока: X", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        statusLabel.setForeground(new Color(30, 80, 160));

        scoreLabel = new JLabel("Счет матча: X: 0 | O: 0 | Ничьи: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        topPanel.add(statusLabel);
        topPanel.add(scoreLabel);
        add(topPanel, BorderLayout.NORTH);

        // Игровое поле 3х3
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 6, 6));
        boardPanel.setBackground(new Color(220, 225, 235));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Font btnFont = new Font("Segoe UI", Font.BOLD, 48);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton btn = new JButton("");
                btn.setFont(btnFont);
                btn.setFocusPainted(false);
                btn.setBackground(Color.WHITE);

                final int row = r;
                final int col = c;
                btn.addActionListener(e -> onCellClick(row, col));

                buttons[r][c] = btn;
                boardPanel.add(btn);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        // Панель кнопок управления
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(new Color(245, 245, 250));

        JButton resetBtn = new JButton("Новый раунд");
        resetBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resetBtn.addActionListener(e -> resetGame());

        JButton resetScoresBtn = new JButton("Сбросить счет");
        resetScoresBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resetScoresBtn.addActionListener(e -> {
            scoreX = 0;
            scoreO = 0;
            scoreDraws = 0;
            updateScore();
            resetGame();
        });

        bottomPanel.add(resetBtn);
        bottomPanel.add(resetScoresBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void setSampleWinningGame() {
        buttons[0][0].setText("X");
        buttons[0][0].setForeground(new Color(220, 53, 69));
        buttons[0][1].setText("O");
        buttons[0][1].setForeground(new Color(13, 110, 253));
        buttons[0][2].setText("O");
        buttons[0][2].setForeground(new Color(13, 110, 253));

        buttons[1][0].setText("X");
        buttons[1][0].setForeground(new Color(220, 53, 69));
        buttons[1][1].setText("X");
        buttons[1][1].setForeground(new Color(220, 53, 69));
        buttons[1][2].setText("");

        buttons[2][0].setText("X");
        buttons[2][0].setForeground(new Color(220, 53, 69));
        buttons[2][1].setText("");
        buttons[2][2].setText("O");
        buttons[2][2].setForeground(new Color(13, 110, 253));

        // Подсветка победного первого столбца
        buttons[0][0].setBackground(new Color(209, 231, 221));
        buttons[1][0].setBackground(new Color(209, 231, 221));
        buttons[2][0].setBackground(new Color(209, 231, 221));

        statusLabel.setText("Победитель: Игрок X! (3 в ряд)");
        statusLabel.setForeground(new Color(25, 135, 84));
        scoreLabel.setText("Счет матча: X: 3 | O: 1 | Ничьи: 0");
        repaint();
    }

    private void onCellClick(int r, int c) {
        if (!buttons[r][c].getText().isEmpty()) return;

        if (xTurn) {
            buttons[r][c].setText("X");
            buttons[r][c].setForeground(new Color(220, 53, 69));
            statusLabel.setText("Ход игрока: O");
        } else {
            buttons[r][c].setText("O");
            buttons[r][c].setForeground(new Color(13, 110, 253));
            statusLabel.setText("Ход игрока: X");
        }

        if (checkWin()) {
            String winner = xTurn ? "X" : "O";
            statusLabel.setText("Победил игрок: " + winner + "!");
            if (xTurn) scoreX++; else scoreO++;
            updateScore();
            disableAll();
            JOptionPane.showMessageDialog(this, "Поздравляем! Победил: " + winner, "Победа!", JOptionPane.INFORMATION_MESSAGE);
        } else if (isBoardFull()) {
            statusLabel.setText("Ничья!");
            scoreDraws++;
            updateScore();
            JOptionPane.showMessageDialog(this, "Боевая ничья!", "Конец партии", JOptionPane.INFORMATION_MESSAGE);
        } else {
            xTurn = !xTurn;
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (checkLine(buttons[i][0], buttons[i][1], buttons[i][2])) return true;
            if (checkLine(buttons[0][i], buttons[1][i], buttons[2][i])) return true;
        }
        if (checkLine(buttons[0][0], buttons[1][1], buttons[2][2])) return true;
        return checkLine(buttons[0][2], buttons[1][1], buttons[2][0]);
    }

    private boolean checkLine(JButton b1, JButton b2, JButton b3) {
        String s = b1.getText();
        if (!s.isEmpty() && s.equals(b2.getText()) && s.equals(b3.getText())) {
            b1.setBackground(new Color(200, 245, 200));
            b2.setBackground(new Color(200, 245, 200));
            b3.setBackground(new Color(200, 245, 200));
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (buttons[r][c].getText().isEmpty()) return false;
            }
        }
        return true;
    }

    private void disableAll() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                buttons[r][c].setEnabled(false);
            }
        }
    }

    private void resetGame() {
        xTurn = true;
        statusLabel.setText("Ход игрока: X");
        statusLabel.setForeground(new Color(30, 80, 160));
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                buttons[r][c].setText("");
                buttons[r][c].setEnabled(true);
                buttons[r][c].setBackground(Color.WHITE);
            }
        }
    }

    private void updateScore() {
        scoreLabel.setText(String.format("Счет матча: X: %d | O: %d | Ничьи: %d", scoreX, scoreO, scoreDraws));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            TicTacToeGame game = new TicTacToeGame();
            if (args.length > 0 && args[0].equals("--sample")) {
                game.setSampleWinningGame();
            }
            game.setVisible(true);
        });
    }
}
