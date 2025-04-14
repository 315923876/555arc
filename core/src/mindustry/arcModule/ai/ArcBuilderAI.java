package mindustry.arcModule.ai;

import arc.struct.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.ai.types.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.game.Teams.*;
import mindustry.gen.*;
import mindustry.input.DesktopInput;
import mindustry.world.*;
import mindustry.world.blocks.ConstructBlock.*;
import mindustry.world.blocks.logic.LogicBlock;
import mindustry.world.blocks.logic.CanvasBlock;

import static mindustry.Vars.*;

public class ArcBuilderAI extends AIController{
    public static float buildRadius = 1500;
    public static float rebuildTime = 120f;

    public @Nullable Unit following;

    public @Nullable BlockPlan lastPlan;

    public float fleeRange = 370f;
    public boolean alwaysFlee;

    boolean found = false;


    public ArcBuilderAI(boolean alwaysFlee, float fleeRange){
        this.alwaysFlee = alwaysFlee;
        this.fleeRange = fleeRange;
    }

    public ArcBuilderAI(){
    }

    @Override
    public void updateMovement(){

        unit.updateBuilding = true;

        if(following != null){

            //try to follow and mimic someone

            //validate follower
            if(!following.isValid() || !following.activelyBuilding()){
                following = null;
                unit.plans.clear();
                return;
            }

            //set to follower's first build plan, whatever that is
            BuildPlan plan = following.buildPlan();
            
            // 检查是否是逻辑方块，如果是则不帮助建造
            if(plan != null && !plan.breaking) {
                Block block = plan.block;
                if(block instanceof LogicBlock) {
                    following = null;
                    unit.plans.clear();
                    return;
                }
            }
            
            unit.plans.clear();
            unit.plans.addFirst(plan);
            lastPlan = null;
        }

        if(unit.buildPlan() != null){
            if(unit.controller() == Vars.player && control.input instanceof DesktopInput di) di.isBuilding = true;

            //approach plan if building
            BuildPlan req = unit.buildPlan();

            //clear break plan if another player is breaking something
            if(!req.breaking && timer.get(timerTarget2, 40f)){
                for(Player player : Groups.player){
                    if(player.isBuilder() && player.unit().activelyBuilding() && player.unit().buildPlan().samePos(req) && player.unit().buildPlan().breaking){
                        unit.plans.removeFirst();
                        //remove from list of plans
                        unit.team.data().plans.remove(p -> p.x == req.x && p.y == req.y);
                        return;
                    }
                }
            }

            boolean valid =
            ((req.tile() != null && req.tile().build instanceof ConstructBuild cons && cons.current == req.block) ||
            (req.breaking ?
            Build.validBreak(unit.team(), req.x, req.y) :
            Build.validPlace(req.block, unit.team(), req.x, req.y, req.rotation)));

            if(valid){
                //move toward the plan
                moveTo(req.tile(), unit.type.buildRange - 20f);
            }else{
                //discard invalid plan
                unit.plans.removeFirst();
                lastPlan = null;
            }
        }else{
            //follow someone and help them build
            if(timer.get(timerTarget2, 60f)){
                found = false;

                Units.nearby(unit.team, unit.x, unit.y, buildRadius, u -> {
                    if(found) return;

                    if(u.canBuild() && u != unit && u.activelyBuilding()){
                        BuildPlan plan = u.buildPlan();
                        
                        // 检查是否是玩家单位和是否是逻辑方块
                        if(plan != null && !plan.breaking && plan.block instanceof LogicBlock){
                            return; // 跳过逻辑方块
                        }
                         if(plan != null && !plan.breaking && plan.block instanceof CanvasBlock){
                            return; // 跳过画板方块
                        }
                        
                        
                        // 只跟随玩家单位
                        if(!(u.controller() instanceof Player)){
                            return;
                        }

                        Building build = world.build(plan.x, plan.y);
                        if(build instanceof ConstructBuild cons){
                            float dist = Math.min(cons.dst(unit) - unit.type.buildRange, 0);

                            //make sure you can reach the plan in time
                            if(dist / unit.speed() < cons.buildCost * 0.9f){
                                following = u;
                                found = true;
                            }
                        }
                    }
                });
            }

            // 移除了自动重建被摧毁的建筑的代码
            // 不再从 team.data().plans 获取建筑计划
        }
    }

    protected boolean nearEnemy(int x, int y){
        return Units.nearEnemy(unit.team, x * tilesize - fleeRange / 2f, y * tilesize - fleeRange / 2f, fleeRange, fleeRange);
    }

    @Override
    public AIController fallback(){
        return unit.type.flying ? new FlyingAI() : new GroundAI();
    }

    @Override
    public boolean useFallback(){
        return state.rules.waves && unit.team == state.rules.waveTeam && !unit.team.rules().rtsAi;
    }
}