package me.cyperion.ntms.Class;

import me.cyperion.ntms.NewTMSv8;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import static me.cyperion.ntms.Utils.colors;

public class Terminator extends Class implements Listener {

    public double costManaOnShot = 3;
    public Terminator(NewTMSv8 plugin) {
        super(plugin);
    }

    @Override
    public ClassType getClassType() {
        return ClassType.TERMINATOR;
    }

    @Override
    public String getName() {
        return colors("&dTerminator");
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if(!plugin.getPlayerData(player)
                .getClassType().equals(ClassType.TERMINATOR)){
            return;
        }

        if(event.getAction() == Action.LEFT_CLICK_BLOCK
         || event.getAction() == Action.LEFT_CLICK_AIR) {
            //確定點擊左鍵
            if(player.getInventory()
                    .getItemInMainHand().getType()
                    == Material.BOW) {
                //確定是弓了

                boolean success = plugin.getMana().costMana(player,costManaOnShot);
                if (success) {
                    //發射三支箭矢，一支箭矢往前，另外左右15度的地方各射出一支

                    Location location = player.getEyeLocation();
                    Vector direction = location.getDirection();
                    shootArrow(location,direction);
                    shootArrow(location,direction.rotateAroundY(15));
                    shootArrow(location,direction.rotateAroundY(-15));
                    player.playSound(player.getLocation(),
                            Sound.ENTITY_SKELETON_SHOOT, 1, 1);

                }
            }
        }
    }

    private void shootArrow(Location location, Vector direction) {
        Arrow arrow = location.getWorld().spawn(location, Arrow.class);
        arrow.setVelocity(direction);
    }
}
