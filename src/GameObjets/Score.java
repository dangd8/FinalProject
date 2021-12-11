import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.text.Font;
//touch if want but dont if you dont want break
class Score implements GameObject {
    private int WIDTH = 108;
    private int HEIGHT = 146;
    private Asset asset = new Asset("/images/score.png", WIDTH, HEIGHT);
    private Sprite sprite;
    private GraphicsContext ctx;
    
    private int posX = 10;
    private int posY = 50;
    private int tablePosX, tablePosY;

    private double prevActivePipePosY = FlappyPig.activePipes[0].getPosY();

    public Score(double screenWidth, double screenHeight, GraphicsContext ctx) {
        sprite = new Sprite(asset);
        tablePosX = (int) screenWidth / 2 - WIDTH / 2;
        tablePosY = ((int) screenHeight - 112 ) / 2 - HEIGHT / 2;
        sprite.setPosX(tablePosX);
        sprite.setPosY(tablePosY);
        sprite.setVel(0, 0);
        sprite.setCtx(ctx);

        posX = (int) screenWidth / 2 - 10;
        posY = 80;

        this.ctx = ctx;
        ctx.setFont(FlappyPig.appFont);
        ctx.setStroke(FlappyPig.appColor);
    }

    public void update(long now) {
        if (FlappyPig.activePipes[0].getPosY() != prevActivePipePosY) {
            FlappyPig.score++;
            prevActivePipePosY = FlappyPig.activePipes[0].getPosY();

            if (FlappyPig.score > FlappyPig.highscore)
                FlappyPig.highscore = FlappyPig.score;
        }
    }

    public void render() {
        if (FlappyPig.gameEnded) {
            sprite.render();
            ctx.setFill(FlappyPig.appColor);
            ctx.setFont(new Font("04b_19", 32));
            ctx.fillText(FlappyPig.score + "", posX + 2, tablePosY + 70);
            ctx.fillText(FlappyPig.highscore + "", posX + 2, tablePosY + 126);
        }

        if (FlappyPig.gameStarted && !FlappyPig.gameEnded) {
            ctx.setFill(Color.WHITE);
            ctx.fillText(FlappyPig.score + "", posX, posY);
            ctx.strokeText(FlappyPig.score + "", posX, posY);
        }
    }
}