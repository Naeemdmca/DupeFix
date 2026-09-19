package com.elysium.dupeFix.mccr;

import com.elysium.dupeFix.DupeFix;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Server {

    private static final NamespacedKey STARTER_KIT_KEY =
            new NamespacedKey(DupeFix.getInstance(), "received_starter_kit");

    private static final NamespacedKey RANDOM_ITEM_KEY =
            new NamespacedKey(DupeFix.getInstance(), "received_random_item");

    private static final Map<EquipmentSlot, ItemStack> STARTER_ARMOR = Map.of(
            EquipmentSlot.HEAD,  new ItemStack(Material.IRON_HELMET),
            EquipmentSlot.CHEST, new ItemStack(Material.IRON_CHESTPLATE),
            EquipmentSlot.LEGS,  new ItemStack(Material.IRON_LEGGINGS),
            EquipmentSlot.FEET,  new ItemStack(Material.IRON_BOOTS)
    );

    private static final List<ItemStack> STARTER_ITEMS = List.of(
            new ItemStack(Material.IRON_SWORD),
            new ItemStack(Material.IRON_PICKAXE),
            new ItemStack(Material.BREAD, 20)
    );

    private static final Set<Material> CREATIVE_ONLY = Set.of(
            Material.BARRIER,
            Material.BEDROCK,
            Material.COMMAND_BLOCK,
            Material.COMMAND_BLOCK_MINECART,
            Material.CHAIN_COMMAND_BLOCK,
            Material.REPEATING_COMMAND_BLOCK,
            Material.STRUCTURE_BLOCK,
            Material.STRUCTURE_VOID,
            Material.JIGSAW,
            Material.LIGHT,
            Material.DEBUG_STICK,
            Material.KNOWLEDGE_BOOK,
            Material.PETRIFIED_OAK_SLAB,
            Material.SPAWNER,
            Material.END_PORTAL_FRAME
    );

    private static final List<Material> RANDOM_POOL = Arrays.stream(Material.values())
            .filter(Server::isValidForRandom)
            .toList();

    public static void giveStarterKit(Player player) {
        if (hasStarterKit(player)) return;

        PlayerInventory inv = player.getInventory();
        STARTER_ARMOR.forEach((slot, item) -> inv.setItem(slot, item.clone()));
        STARTER_ITEMS.forEach(item -> inv.addItem(item.clone()));

        player.getPersistentDataContainer().set(STARTER_KIT_KEY, PersistentDataType.BYTE, (byte) 1);
    }

    public static List<ItemStack> getStarterKit() {
        List<ItemStack> kit = new ArrayList<>(STARTER_ARMOR.values());
        kit.addAll(STARTER_ITEMS);
        return kit;
    }

    public static boolean hasStarterKit(Player player) {
        return player.getPersistentDataContainer().has(STARTER_KIT_KEY, PersistentDataType.BYTE);
    }

    public static void givePlayerRandomItem(Player player) {
        ItemStack randomItem = ItemStack.of(getRandomMaterial());

        HashMap<Integer, ItemStack> overflow = player.getInventory().addItem(randomItem);
        overflow.values().forEach(leftover ->
                player.getWorld().dropItemNaturally(player.getLocation(), leftover));

        player.sendMessage(Component.text("You got ", NamedTextColor.GREEN)
                .append(randomItem.displayName()));

        player.getPersistentDataContainer().set(RANDOM_ITEM_KEY, PersistentDataType.BYTE, (byte) 1);
    }

    public static boolean hasReceivedRandomItem(Player player) {
        return player.getPersistentDataContainer().has(RANDOM_ITEM_KEY, PersistentDataType.BYTE);
    }

    public static boolean isValidForRandom(Material material) {
        if (!material.isItem()) return false;
        if (material.isAir()) return false;
        if (material.isLegacy()) return false;
        if (material.getMaxStackSize() != 1) return false;
        return !isObtainableOnlyInCreative(material);
    }

    public static Material getRandomMaterial() {
        return RANDOM_POOL.get(ThreadLocalRandom.current().nextInt(RANDOM_POOL.size()));
    }

    public static boolean isObtainableOnlyInCreative(Material material) {
        return CREATIVE_ONLY.contains(material);
    }

    public static boolean isObtainableInSurvival(Material material) {
        return !CREATIVE_ONLY.contains(material);
    }

}