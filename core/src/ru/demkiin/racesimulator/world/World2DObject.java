package ru.demkiin.racesimulator.world;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//Инкапсулирует просто "блок" - который может быть стеной, полом, потолком и т.д.
// по сути - просто кирпичик для построения уровня
public class World2DObject {
    private Vector2 center;
    protected float width;
    protected float height;
    protected Rectangle bounds;

    public World2DObject(float centrePosX, float centrePosY, float width, float height)    {
        center.x = centrePosX;
        center.y = centrePosY;
        this.width = width;
        this.height = height;

        bounds = new Rectangle(centrePosX - width/2, centrePosY - height/2, width, height);
    }

    public World2DObject(Vector2 center, float width, float height)    {
        this.center = center;
        this.width = width;
        this.height = height;

        bounds = new Rectangle(center.x - width/2, center.y - height/2, width, height);
    }



    //Above only access methods...
    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
