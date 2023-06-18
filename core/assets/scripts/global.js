//Generated class. Do not modify.

"use strict";

let scriptName = "base.js"
let modName = "none"

const log = (context, obj) => Vars.mods.scripts.log(context, String(obj))
const print = text => log(modName + "/" + scriptName, text)

const newFloats = cap => Vars.mods.getScripts().newFloats(cap);

//these are not strictly necessary, but are kept for edge cases
const run = method => new java.lang.Runnable(){run: method}
const boolf = method => new Boolf(){get: method}
const boolp = method => new Boolp(){get: method}
const floatf = method => new Floatf(){get: method}
const floatp = method => new Floatp(){get: method}
const cons = method => new Cons(){get: method}
const prov = method => new Prov(){get: method}
const func = method => new Func(){get: method}

const newEffect = (lifetime, renderer) => new Effect.Effect(lifetime, new Effect.EffectRenderer({render: renderer}))
Call = Packages.mindustry.gen.Call

//js 'extend(Base, ..., {})' = java 'new Base(...) {}'
function extend(/*Base, ..., def*/){
    const Base = arguments[0]
    const def = arguments[arguments.length - 1]
    //swap order from Base, def, ... to Base, ..., def
    const args = [Base, def].concat(Array.from(arguments).splice(1, arguments.length - 2))

    //forward constructor arguments to new JavaAdapter
    const instance = JavaAdapter.apply(null, args)
    //JavaAdapter only overrides functions; set fields too
    for(var i in def){
        if(typeof(def[i]) != "function"){
            instance[i] = def[i]
        }
    }
    return instance
}

importPackage(Packages.arc)
importPackage(Packages.arc.graphics)
importPackage(Packages.arc.graphics.g2d)
importPackage(Packages.arc.graphics.gl)
importPackage(Packages.arc.math)
importPackage(Packages.arc.util)
importPackage(Packages.mindustry.gen)
importPackage(Packages.mindustry.io)
importPackage(Packages.mindustry.net)
const AdminRequestEvent = Packages.mindustry.game.EventType.AdminRequestEvent
const PlayerIpUnbanEvent = Packages.mindustry.game.EventType.PlayerIpUnbanEvent
const PlayerIpBanEvent = Packages.mindustry.game.EventType.PlayerIpBanEvent
const PlayerUnbanEvent = Packages.mindustry.game.EventType.PlayerUnbanEvent
const PlayerBanEvent = Packages.mindustry.game.EventType.PlayerBanEvent
const PlayerLeave = Packages.mindustry.game.EventType.PlayerLeave
const PlayerConnect = Packages.mindustry.game.EventType.PlayerConnect
const PlayerJoin = Packages.mindustry.game.EventType.PlayerJoin
const PlayerConnectionConfirmed = Packages.mindustry.game.EventType.PlayerConnectionConfirmed
const ConnectPacketEvent = Packages.mindustry.game.EventType.ConnectPacketEvent
const ConnectionEvent = Packages.mindustry.game.EventType.ConnectionEvent
const UnitChangeEvent = Packages.mindustry.game.EventType.UnitChangeEvent
const UnitUnloadEvent = Packages.mindustry.game.EventType.UnitUnloadEvent
const UnitSpawnEvent = Packages.mindustry.game.EventType.UnitSpawnEvent
const UnitCreateEvent = Packages.mindustry.game.EventType.UnitCreateEvent
const UnitDrownEvent = Packages.mindustry.game.EventType.UnitDrownEvent
const UnitDamageEvent = Packages.mindustry.game.EventType.UnitDamageEvent
const UnitBulletDestroyEvent = Packages.mindustry.game.EventType.UnitBulletDestroyEvent
const UnitDestroyEvent = Packages.mindustry.game.EventType.UnitDestroyEvent
const BuildingBulletDestroyEvent = Packages.mindustry.game.EventType.BuildingBulletDestroyEvent
const GeneratorPressureExplodeEvent = Packages.mindustry.game.EventType.GeneratorPressureExplodeEvent
const BlockDestroyEvent = Packages.mindustry.game.EventType.BlockDestroyEvent
const BuildSelectEvent = Packages.mindustry.game.EventType.BuildSelectEvent
const BuildRotateEvent = Packages.mindustry.game.EventType.BuildRotateEvent
const BlockBuildEndEvent = Packages.mindustry.game.EventType.BlockBuildEndEvent
const BlockBuildBeginEvent = Packages.mindustry.game.EventType.BlockBuildBeginEvent
const ResearchEvent = Packages.mindustry.game.EventType.ResearchEvent
const UnlockEvent = Packages.mindustry.game.EventType.UnlockEvent
const StateChangeEvent = Packages.mindustry.game.EventType.StateChangeEvent
const CoreChangeEvent = Packages.mindustry.game.EventType.CoreChangeEvent
const BuildTeamChangeEvent = Packages.mindustry.game.EventType.BuildTeamChangeEvent
const TileChangeEvent = Packages.mindustry.game.EventType.TileChangeEvent
const TilePreChangeEvent = Packages.mindustry.game.EventType.TilePreChangeEvent
const BuildDamageEvent = Packages.mindustry.game.EventType.BuildDamageEvent
const GameOverEvent = Packages.mindustry.game.EventType.GameOverEvent
const BuildingCommandEvent = Packages.mindustry.game.EventType.BuildingCommandEvent
const UnitControlEvent = Packages.mindustry.game.EventType.UnitControlEvent
const PayloadDropEvent = Packages.mindustry.game.EventType.PayloadDropEvent
const PickupEvent = Packages.mindustry.game.EventType.PickupEvent
const TapEvent = Packages.mindustry.game.EventType.TapEvent
const ConfigEvent = Packages.mindustry.game.EventType.ConfigEvent
const DepositEvent = Packages.mindustry.game.EventType.DepositEvent
const WithdrawEvent = Packages.mindustry.game.EventType.WithdrawEvent
const SectorCaptureEvent = Packages.mindustry.game.EventType.SectorCaptureEvent
const ClientChatEvent = Packages.mindustry.game.EventType.ClientChatEvent
const PlayerChatEvent = Packages.mindustry.game.EventType.PlayerChatEvent
const TextInputEvent = Packages.mindustry.game.EventType.TextInputEvent
const MenuOptionChooseEvent = Packages.mindustry.game.EventType.MenuOptionChooseEvent
const ClientServerConnectEvent = Packages.mindustry.game.EventType.ClientServerConnectEvent
const ClientPreConnectEvent = Packages.mindustry.game.EventType.ClientPreConnectEvent
const SchematicCreateEvent = Packages.mindustry.game.EventType.SchematicCreateEvent
const SectorLaunchLoadoutEvent = Packages.mindustry.game.EventType.SectorLaunchLoadoutEvent
const SectorLaunchEvent = Packages.mindustry.game.EventType.SectorLaunchEvent
const LaunchItemEvent = Packages.mindustry.game.EventType.LaunchItemEvent
const SectorInvasionEvent = Packages.mindustry.game.EventType.SectorInvasionEvent
const SectorLoseEvent = Packages.mindustry.game.EventType.SectorLoseEvent
const SaveLoadEvent = Packages.mindustry.game.EventType.SaveLoadEvent
const WorldLoadEndEvent = Packages.mindustry.game.EventType.WorldLoadEndEvent
const WorldLoadBeginEvent = Packages.mindustry.game.EventType.WorldLoadBeginEvent
const WorldLoadEvent = Packages.mindustry.game.EventType.WorldLoadEvent
const FileTreeInitEvent = Packages.mindustry.game.EventType.FileTreeInitEvent
const MusicRegisterEvent = Packages.mindustry.game.EventType.MusicRegisterEvent
const ClientLoadEvent = Packages.mindustry.game.EventType.ClientLoadEvent
const ContentInitEvent = Packages.mindustry.game.EventType.ContentInitEvent
const BlockInfoEvent = Packages.mindustry.game.EventType.BlockInfoEvent
const CoreItemDeliverEvent = Packages.mindustry.game.EventType.CoreItemDeliverEvent
const TurretAmmoDeliverEvent = Packages.mindustry.game.EventType.TurretAmmoDeliverEvent
const LineConfirmEvent = Packages.mindustry.game.EventType.LineConfirmEvent
const TurnEvent = Packages.mindustry.game.EventType.TurnEvent
const WaveEvent = Packages.mindustry.game.EventType.WaveEvent
const HostEvent = Packages.mindustry.game.EventType.HostEvent
const ResetEvent = Packages.mindustry.game.EventType.ResetEvent
const PlayEvent = Packages.mindustry.game.EventType.PlayEvent
const DisposeEvent = Packages.mindustry.game.EventType.DisposeEvent
const ServerLoadEvent = Packages.mindustry.game.EventType.ServerLoadEvent
const ClientCreateEvent = Packages.mindustry.game.EventType.ClientCreateEvent
const SaveWriteEvent = Packages.mindustry.game.EventType.SaveWriteEvent
const MapPublishEvent = Packages.mindustry.game.EventType.MapPublishEvent
const MapMakeEvent = Packages.mindustry.game.EventType.MapMakeEvent
const ResizeEvent = Packages.mindustry.game.EventType.ResizeEvent
const LoseEvent = Packages.mindustry.game.EventType.LoseEvent
const WinEvent = Packages.mindustry.game.EventType.WinEvent
const Trigger = Packages.mindustry.game.EventType.Trigger
