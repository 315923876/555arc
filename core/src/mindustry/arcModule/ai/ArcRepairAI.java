package mindustry.arcModule.ai;

import arc.util.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.world.blocks.ConstructBlock.*;
import arc.math.Angles;
import arc.math.geom.Vec2;

public class ArcRepairAI extends AIController{
    public static float  fleeRange = 310f;
    private final Vec2 vec = new Vec2();
    @Nullable
    Teamc avoid;

    Building damagedTarget;

    @Override
    public void updateMovement(){
        if(target instanceof Building){
            boolean shoot = false;

            if(target.within(unit, unit.type.range)){
                unit.aim(target);
                shoot = true;
            }

            unit.controlWeapons(shoot);
        }else if(target == null){
            unit.controlWeapons(false);
        }

        if(target != null){
            if(!target.within(unit, unit.type.range * 0.65f) && target instanceof Building b && b.team == unit.team){
                moveTo(target, unit.type.range * 0.65f);
            }

            unit.lookAt(target);
        }

        //not repairing
        if(!(target instanceof Building)){
            if(timer.get(timerTarget4, 40)){
                avoid = target(unit.x, unit.y, fleeRange, true, true);
            }
        }
        maintainSafeDistance();
    }

    @Override
    public void updateTargeting(){
        if(timer.get(timerTarget, 15)){
            damagedTarget = Units.findDamagedTile(unit.team, unit.x, unit.y);
            if(damagedTarget instanceof ConstructBuild) damagedTarget = null;
        }

        if(damagedTarget == null){
            super.updateTargeting();
        }else{
            this.target = damagedTarget;
        }
    }

    private void maintainSafeDistance() {
    if(target != null && unit.within(target, unit.type.range * 0.6f)) {
        // 计算从目标到单位的方向向量
        float angle = Angles.angle(target.x(), target.y(), unit.x, unit.y);
        // 沿此方向移动以远离目标
        vec.trns(angle, unit.speed() * Time.delta);
        unit.move(vec.x, vec.y);
    }

}
}