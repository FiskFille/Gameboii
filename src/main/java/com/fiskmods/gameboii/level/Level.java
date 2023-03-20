package com.fiskmods.gameboii.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.fiskmods.gameboii.engine.BoundingBox;
import com.fiskmods.gameboii.engine.Point2f;

public abstract class Level
{
    public final List<LevelObject> objects = new ArrayList<>();
    private final List<LevelObject> newObjects = new ArrayList<>();

    public final Random rand;

    public Level(Random random)
    {
        rand = random;
    }

    public Level(long seed)
    {
        this(new Random(seed));
    }

    public Level()
    {
        this(new Random());
    }

    public Level reset()
    {
        objects.forEach(LevelObject::destroy);
        newObjects.clear();
        return this;
    }

    public void addObject(LevelObject obj)
    {
        obj.level = this;
        newObjects.add(obj);
    }

    public void onUpdate()
    {
        objects.removeIf(LevelObject::tick);
        objects.addAll(newObjects);
        newObjects.clear();
    }

    public List<BoundingBox> getCollidingBoundingBoxes(LevelObject obj, BoundingBox box)
    {
        List<BoundingBox> list = new ArrayList<>();
        objects.stream().filter(t -> LevelObject.canObjectsCollide(t, obj)).forEach(t -> t.addCollisionBoxes(list, box));
        return list;
//        return objects.stream().filter(t -> t != obj && t.canCollideWith(obj) && obj.canCollideWith(t)).map(t -> t.boundingBox).filter(t -> t.intersectsWith(box)).collect(Collectors.toList());
    }

//    public List<LevelObject> getCollidingObjects(LevelObject obj, BoundingBox box)
//    {
//        List<BoundingBox> list = new ArrayList<>();
//        objects.stream().filter(t -> LevelObject.canObjectsCollide(t, obj)).filter(t ->
//        {
////            for ()
//            
//            return false;
//        });
//        
//        return null;
////        return objects.stream().filter(t -> LevelObject.canObjectsCollide(t, obj) && t.boundingBox.intersectsWith(box)).collect(Collectors.toList());
//    }

    public List<LevelObject> getIntersectingObjects(LevelObject obj, BoundingBox box)
    {
        List<LevelObject> list = new ArrayList<>();
        List<BoundingBox> boxes = new ArrayList<>();
//        objects.stream().filter(t -> t != obj).forEach(t -> t.addCollisionBoxes(list, box));
//        list.removeIf(t -> );
        
        for (LevelObject other : objects)
        {
            if (obj != other)
            {
                boxes.clear();
                other.addCollisionBoxes(boxes, box);

                if (!boxes.isEmpty())
                {
                    list.add(other);
                }
            }
        }
        
        return list;
//        return objects.stream().filter(t -> t != obj && t.boundingBox.intersectsWith(box)).collect(Collectors.toList());
    }
    
    public LevelObject raytrace(LevelObject exclude, BoundingBox box)
    {
        LevelObject selected = null;
        Point2f src = box.center();

        float x = (box.minX + box.maxX) / 2;
        float y = (box.minY + box.maxY) / 2;
        float dist = Float.MAX_VALUE;

        for (LevelObject obj : getIntersectingObjects(exclude, box))
        {
            if (obj.boundingBox.isPointInside(src))
            {
                selected = obj;
                break;
            }

            Point2f p = obj.boundingBox.calculateIntercept(src, obj.boundingBox.center());

            if (p != null)
            {
                float f = src.squareDistanceTo(p);

                if (f < dist)
                {
                    selected = obj;
                    dist = f;
                }
            }
        }
        
        return selected;
    }
}
