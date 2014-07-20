package ru.demkiin.racesimulator.world;

/**
 * Created by evgen1000end on 20.07.2014.
 */
public class World3DObject extends World2DObject {

    private float centrePosZ;
    private float depth;

    public  World3DObject(float centrePosX, float centrePosY, float centrePosZ, float width, float height, float depth){
        super(centrePosX, centrePosY, width, height);

        this.centrePosZ = centrePosZ;
        this.depth = depth;
    }

    public  World3DObject(float centrePosX, float centrePosY, float width, float height){
        super(centrePosX, centrePosY, width, height);

        this.centrePosZ = 0f;
        this.depth = 0f;
    }





}
