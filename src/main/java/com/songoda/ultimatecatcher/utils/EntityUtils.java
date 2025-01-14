package com.songoda.ultimatecatcher.utils;

import com.songoda.core.hooks.EntityStackerManager;
import com.songoda.core.nms.NmsManager;
import com.songoda.core.nms.nbt.NBTEntity;
import com.songoda.core.third_party.de.tr7zw.nbtapi.NBTItem;
import com.songoda.ultimatecatcher.UltimateCatcher;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class EntityUtils {

    public static String getFormattedEntityType(EntityType type) {
        return UltimateCatcher.getInstance().getMobConfig().getString("Mobs." + type.name() + ".Display Name");
    }

    public static ItemStack serializeEntity(ItemStack item, LivingEntity entity) {
        NBTItem nbtItem = new NBTItem(item);
        NBTEntity nbtEntity = NmsManager.getNbt().of(entity);
        if (EntityStackerManager.isStacked(entity))
            nbtEntity.set("wasStacked", true);
        nbtItem.setBoolean("UC", true);
        try {
            nbtItem.setString("serialized_entity", new String(nbtEntity.serialize(), "ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return nbtItem.getItem();
    }


    public static LivingEntity spawnEntity(Location location, ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        NBTEntity nbtEntity = NmsManager.getNbt().newEntity();

        byte[] encoded = new byte[0];
        try {
            encoded = nbtItem.getString("serialized_entity").getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        nbtEntity.deSerialize(encoded);
        nbtEntity.set("UUID", UUID.randomUUID());
        LivingEntity entity = (LivingEntity) nbtEntity.spawn(location);
        if (nbtEntity.has("wasStacked") && nbtEntity.getNBTObject("wasStacked").asBoolean()) {
            entity.setCustomName("");
            entity.setCustomNameVisible(false);
        }
        return entity;
    }
}
