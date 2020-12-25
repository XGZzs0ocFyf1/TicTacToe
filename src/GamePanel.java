import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, MouseListener {

    private final int FIELD_SIZE = 400;
    private final int CELL_SIDE = 100;
    private final int CELLS_IN_LINE = FIELD_SIZE / CELL_SIDE;
    private final Color BG_COLOR = new Color(67, 145, 37, 151);
    private final Color FG_COLOR = new Color(233, 129, 10);
    private final Color BORDER_COLOR = new Color(10, 129, 233);
    private boolean isGameStopped = false;
    private String winner = "";

    private final GameCore game;


    public GamePanel() {
        setSize(FIELD_SIZE, FIELD_SIZE);
        addMouseListener(this);
        game = new GameCore(CELLS_IN_LINE);

        for (int rowIdx = 0; rowIdx < CELLS_IN_LINE; rowIdx++) {
            for (int colIdx = 0; colIdx < CELLS_IN_LINE; colIdx++) {
                game.getField()[rowIdx][colIdx] = new Cell(rowIdx * CELL_SIDE, colIdx * CELL_SIDE,
                        GameCore.DOT_EMPTY + "");
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isGameStopped) {
            Font font = new Font("Verdana", Font.BOLD, 90);
            g.setFont(font);
            g.setColor(new Color(168, 59, 52));
            g.drawString(winner, 30, 100);
            if (!winner.equals("draw")) g.drawString("wins ", 30, 200);
        } else {
            drawGrid(g);
            g.setColor(BG_COLOR);

            //draw all cells one by one
            for (int rowIdx = 0; rowIdx < CELLS_IN_LINE; rowIdx++) {
                for (int colIdx = 0; colIdx < CELLS_IN_LINE; colIdx++) {
                    drawCell(game.getField()[rowIdx][colIdx], g);
                }
            }
        }


    }

    //draws cell grid on the panel. spend a lot of CPU time
    private void drawGrid(Graphics g) {
        g.setColor(BORDER_COLOR); //
        for (int i = 0; i < CELLS_IN_LINE; i++) {
            g.drawLine(0, i * CELL_SIDE, FIELD_SIZE, i * CELL_SIDE); //draw horizontal line
            g.drawLine(i * CELL_SIDE, 0, i * CELL_SIDE, FIELD_SIZE); //draw vertical line
        }
    }

    private void drawCell(Cell cell, Graphics g) {

        cell.setX(cell.getX() / 100 == 0 ? cell.getX() * 100 : cell.getX());
        cell.setY(cell.getY() / 100 == 0 ? cell.getY() * 100 : cell.getY());

        //draw background
        g.setColor(BG_COLOR);
        g.fillRect(cell.getX(), cell.getY(), cell.getSide(), cell.getSide());
        String content = cell.getCellValue().contains(GameCore.DOT_EMPTY + "") ? "" : cell.getCellValue();

        //draw text
        Font font = new Font("Verdana", Font.BOLD, 50);
        g.setFont(font);
        g.setColor(FG_COLOR);

        g.drawString(content, cell.getX() + 30, cell.getY() + cell.getSide() / 2 + 20);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        makeOneStep(e);

    }

    //one game step = user turn and then computer turn
    public void makeOneStep(MouseEvent e) {

        if (isGameStopped) {
            return;
        }


        int currentX = e.getX();
        int currentY = e.getY();


        if (!isGameStopped) userTurn(currentX, currentY); // its normally :D
        if (shouldStop(game.checkWin(GameCore.DOT_X), "user")) return;
        if (!isGameStopped) shouldStop(game.isMapFull(), "draw"); //check overflow


        if (!isGameStopped) computerTurn();
        if (shouldStop(game.checkWin(GameCore.DOT_O), "robot")) return;
        if (!isGameStopped) shouldStop(game.isMapFull(), "draw"); //check overflow

    }

    /**
     *
     * @param mapFull boolean, check that gamefield Cell[][] has no elements with cell.isIncludeChar(TicTacToeGame
     *                .DOT_EMPY). i.e. in one hand we have DOT_X in other hand we have DOT_O
     * @param winnersName the name of game winner
     */
    private boolean shouldStop(boolean mapFull, String winnersName) {
        if (mapFull) {
            isGameStopped = true;
            winner = winnersName;
        }
        return isGameStopped;
    }


    //user turn
    private void userTurn(int currentY, int currentX) {
        for (int rowIdx = 0; rowIdx < game.getField()[0].length; rowIdx++) {
            for (int colIdx = 0; colIdx < game.getField().length; colIdx++) {
                Cell cell = game.getField()[rowIdx][colIdx];
                if (cell.isItYou(currentY, currentX)) game.getField()[rowIdx][colIdx].setCellValue("X");
            }
        }
        repaint();
    }

    private void computerTurn() {
        //computer turn
        Random rand = new Random(CELLS_IN_LINE);

        int rowIdx;
        int colIdx;
        do {
            rowIdx = rand.nextInt(CELLS_IN_LINE);
            colIdx = rand.nextInt(CELLS_IN_LINE);
        } while (!game.isCellValid(rowIdx, colIdx, GameCore.DOT_O));
        game.getField()[rowIdx][colIdx] = new Cell(rowIdx, colIdx, GameCore.DOT_O + "");
        repaint();

    }


    //default interface methods do not override
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


    @Override
    public void actionPerformed(ActionEvent e) {
    }


}
