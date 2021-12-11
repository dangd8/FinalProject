import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import java.util.concurrent.ThreadLocalRandom;
//try reduce number of pipes
class Pipes implements GameObject {
    private int WIDTH = 62;
    private int HEIGHT = 2000;
    private Asset assetUp = new Asset("/images/up_pipe.png", WIDTH, HEIGHT);
    private Asset assetDown = new Asset("/images/down_pipe.png", WIDTH, HEIGHT);
    private ArrayList<Sprite> spritesUp = new ArrayList<>();
    private ArrayList<Sprite> spritesDown = new ArrayList<>();

    private double screenHeight, screenWidth;
    private GraphicsContext ctx;

    public Pipes(double screenWidth, double screenHeight, GraphicsContext ctx) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.ctx = ctx;

        Sprite pipes[] = createPipes(screenWidth + 200);
        FlappyPig.activePipes = pipes;
        spritesUp.add(pipes[0]);
        spritesDown.add(pipes[1]);
    }

    public void update(long now) {
        if (FlappyPig.gameStarted) {
            for (int i = 0; i < spritesUp.size(); i++) {
                spritesUp.get(i).update();
                spritesDown.get(i).update();

                if (FlappyPig.activePipes[0].getPosX() + WIDTH < screenWidth / 2 - 100) {
                    FlappyPig.activePipes = new Sprite[] { spritesUp.get(i), spritesDown.get(i) };
                }
            }
        }
    }

    public void render() {
        for (Sprite pipe : spritesUp)
            pipe.render();

        for (Sprite pipe : spritesDown)
            pipe.render();

        if (spritesUp.get( spritesUp.size() - 1 ).getPosX() < screenWidth) {
            Sprite pipes[] = createPipes( spritesUp.get( spritesUp.size() - 1 ).getPosX() + 400 );
      
            spritesUp.add(pipes[0]);
            spritesDown.add(pipes[1]);
        }
    
        if (spritesUp.get(0).getPosX() < -WIDTH) {
            spritesUp.remove(0);
            spritesDown.remove(0);
        }
    }

    private Sprite[] createPipes(double posX) {
        double usableHeight = screenHeight - 364;
        int randomNum = ThreadLocalRandom.current().nextInt(0, (int) usableHeight + 1);

        Sprite pipeUp = new Sprite(assetUp);
        pipeUp.setPos(posX, 246 + randomNum);
        pipeUp.setVel(-2.5, 0);
        pipeUp.setCtx(ctx);

        Sprite pipeDown = new Sprite(assetDown);
        pipeDown.setPos(posX, -1994 + randomNum);
        pipeDown.setVel(-2.5, 0);
        pipeDown.setCtx(ctx);

        return new Sprite[] { pipeUp, pipeDown };
    }
}