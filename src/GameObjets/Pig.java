import java.util.ArrayList ;
import javafx.scene.canvas.GraphicsContext ;

class Pig implements GameObject
    {

    private int WIDTH = 56 ;
    private int HEIGHT = 40 ;
    private Asset assets[] = { new Asset( "/images/pig1.png", WIDTH, HEIGHT ),
                               new Asset( "/images/pig2.png", WIDTH, HEIGHT ),
                               new Asset( "/images/pig3.png", WIDTH, HEIGHT ) } ;
    private Sprite sprite ;
    private int currentAssetIndex = 0 ;
    private long prevTime = 0 ;
    private double terminalVel = 8 ;
    private double shiftMax = 10 ;
    private double shiftDelta = 0 ;
    private double screenHeight ;

    public Pig( double screenWidth, double screenHeight, GraphicsContext ctx )
        {
        this.screenHeight = screenHeight ;

        sprite = new Sprite( assets[ currentAssetIndex ] ) ;
        sprite.setPosX( screenWidth / 2 - WIDTH / 2 ) ;
        sprite.setPosY( FlappyPig.gameEnded
            ? screenHeight - 112 - HEIGHT
                : ( screenHeight - 112 ) / 2 ) ;
        sprite.setVel( 0, 0 ) ;
        sprite.setCtx( ctx ) ;
        }


    public void jumpHandler()
        {
        // touch
        sprite.setVelY( -4.5 ) ;
        }


    public void update( long now )
        {
        if ( !FlappyPig.gameStarted && !FlappyPig.gameEnded )
            {
            updatePigHovering() ;
            updateSprite() ;
            }
        else if ( FlappyPig.gameEnded )
            {
            updatePigFalldown() ;
            }
        else if ( FlappyPig.gameStarted )
            {
            if ( now - prevTime > 90000000 )
                {
                updateSprite() ;
                prevTime = now ;
                }

            if ( ( sprite.getPosY() + HEIGHT ) > ( screenHeight - 112 ) ||
                 sprite.intersects( FlappyPig.activePipes[ 0 ] ) ||
                 sprite.intersects( FlappyPig.activePipes[ 1 ] ) )
                {

                FlappyPig.gameStarted = false ;
                FlappyPig.gameEnded = true ;
                }

            updatePigPlaying() ;
            }

        sprite.update() ;
        }


    public void updatePigHovering()
        {
        double vel = sprite.getVelY() ;

        if ( shiftDelta == 0 )
            {
            sprite.setVelY( 0.5 ) ;
            shiftDelta += 0.5 ;
            }
        else if ( shiftDelta > 0 )
            {
            if ( vel > 0.1 )
                {
                if ( shiftDelta < shiftMax / 2 )
                    {
                    double shift = vel * 1.06 ;
                    sprite.setVelY( shift ) ;
                    shiftDelta += shift ;
                    }
                else
                    {
                    double shift = vel * 0.8 ;
                    sprite.setVelY( shift ) ;
                    shiftDelta += shift ;
                    }
                }
            else if ( vel < 0.1 )
                {
                if ( vel > 0 )
                    {
                    sprite.setVelY( -0.5 ) ;
                    }
                else
                    {
                    double shift = vel * 1.06 ;
                    sprite.setVelY( shift ) ;
                    shiftDelta += shift ;
                    }
                }
            }
        else if ( shiftDelta < 0 )
            {
            if ( vel < -0.1 )
                {
                if ( shiftDelta > -shiftMax / 2 )
                    {
                    double shift = vel * 1.06 ;
                    sprite.setVelY( shift ) ;
                    shiftDelta += shift ;
                    }
                else
                    {
                    double shift = vel * 0.8 ;
                    sprite.setVelY( shift ) ;
                    shiftDelta += shift ;
                    }
                }
            else if ( vel > -0.1 )
                {
                if ( vel < 0 )
                    {
                    sprite.setVelY( 0.5 ) ;
                    }
                else
                    {
                    double shift = vel * 1.06 ;
                    sprite.setVelY( shift ) ;
                    shiftDelta += shift ;
                    }
                }
            }
        }


    public void updatePigPlaying()
        {
        double vel = sprite.getVelY() ;

        if ( vel >= terminalVel )
            // touch
            sprite.setVelY( vel + .2 ) ;
        else
            // touch
            sprite.setVelY( vel + .098 ) ;
        }


    public void updatePigFalldown()
        {
        if ( sprite.getPosY() + HEIGHT >= screenHeight - 112 )
            {
            // touch
            sprite.setVel( -0, -0 ) ;
            sprite.setPosY( screenHeight - 112 - HEIGHT ) ;
            }
        else
            {
            updatePigPlaying() ;
            }

        }


    public void updateSprite()
        {
        if ( currentAssetIndex == 3 )
            currentAssetIndex = 0 ;

        sprite.changeImage( assets[ currentAssetIndex ] ) ;

        currentAssetIndex++ ;
        }


    public void render()
        {
        sprite.render() ;
        }
    }