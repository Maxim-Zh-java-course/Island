package model;

public class Island {
    private final int width;
    private final int height;
    private final Cell[][] cells; // Массив клеток

    // Конструктор острова
    public Island(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(x, y, this);
            }
        }
    }

    // Получаем клетку по координатам
    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    //Проверка на корректность координат
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    // Получаем ширину острова
    public int getWidth() {
        return width;
    }

    // Получаем высоту острова
    public int getHeight() {
        return height;
    }
}