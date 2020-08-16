package com.smore_d.rms.util;

import com.smore_d.rms.RefinedMetalSmelting;
import com.sun.javafx.geom.Vec3d;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static net.minecraft.util.Direction.*;

public class RMSUtils {
    private static final List<Item> inventoryItemBlacklist = new ArrayList<>();

    // this may return to Direction.HORIZONTALS one day (like in 1.12.2) but for now...
    public static final Direction[] HORIZONTALS = new Direction[] {
            Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST
    };

    /**
     * Returns the EnumFacing of the given entity.
     *
     * @param entity the entity
     * @param includeUpAndDown false when UP/DOWN should not be included.
     * @return the entity's facing direction
     */
    public static Direction getDirectionFacing(LivingEntity entity, boolean includeUpAndDown) {
        double yaw = entity.rotationYaw;
        while (yaw < 0)
            yaw += 360;
        yaw = yaw % 360;
        if (includeUpAndDown) {
            if (entity.rotationPitch > 45) return DOWN;
            else if (entity.rotationPitch < -45) return UP;
        }
        if (yaw < 45) return Direction.SOUTH;
        else if (yaw < 135) return Direction.WEST;
        else if (yaw < 225) return Direction.NORTH;
        else if (yaw < 315) return Direction.EAST;
        else return Direction.SOUTH;
    }

    public static Direction rotateAround(Direction dir, Direction.Axis axis) {
        switch (axis) {
            case X:
                return dir.getAxis() == Direction.Axis.X ? dir : rotateX(dir);
            case Y:
                return dir.getAxis() == Direction.Axis.Y ? dir : dir.rotateY();
            case Z:
                return dir.getAxis() == Direction.Axis.Z ? dir : rotateZ(dir);
            default:
                throw new IllegalStateException("Unable to get CW facing for axis " + axis);
        }
    }

    private static Direction rotateX(Direction dir) {
        switch (dir) {
            case NORTH:
                return DOWN;
            case SOUTH:
                return UP;
            case UP:
                return NORTH;
            case DOWN:
                return SOUTH;
            case EAST:
            case WEST:
            default:
                throw new IllegalStateException("Unable to get X-rotated facing of " + dir);
        }
    }

    private static Direction rotateZ(Direction dir) {
        switch (dir) {
            case EAST:
                return DOWN;
            case WEST:
                return UP;
            case UP:
                return EAST;
            case DOWN:
                return WEST;
            case NORTH:
            case SOUTH:
            default:
                throw new IllegalStateException("Unable to get Z-rotated facing of " + dir);
        }
    }

    /**
     * Get a yaw angle from an EnumFacing
     *
     * @param facing the facing direction
     * @return the yaw angle
     */
    public static int getYawFromFacing(Direction facing) {
        switch (facing) {
            case NORTH:
                return 180;
            case SOUTH:
                return 0;
            case WEST:
                return 90;
            case EAST:
                return -90;
            default:
                return 0;
        }
    }
    /*
    public static final double[] sin;
    public static final double[] cos;
    public static final int CIRCLE_POINTS = 500;

    /*
     * Initializes the sin,cos and tan variables, so that they can be used without having to calculate them every time (render tick).
     */
    /*static {
        sin = new double[CIRCLE_POINTS];
        cos = new double[CIRCLE_POINTS];

        for (int i = 0; i < CIRCLE_POINTS; i++) {
            double angle = 2 * Math.PI * i / CIRCLE_POINTS;
            sin[i] = Math.sin(angle);
            cos[i] = Math.cos(angle);
        }
    }
    */
    /**
     * This method takes one long string, and cuts it into lines which have
     * a maxCharPerLine and returns it in a String list.
     * It also preserves color formats. '\n' can be used to force a carriage
     * return.
     */
    public static List<String> splitString(String text, int maxCharPerLine) {
        StringTokenizer tok = new StringTokenizer(text, " ");
        StringBuilder output = new StringBuilder(text.length());
        List<String> textList = new ArrayList<>();
        String color = "";
        int lineLen = 0;
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();
            if (word.contains("\u00a7")) { // if there is a text formatter present.
                for (int i = 0; i < word.length() - 1; i++) {
                    if (word.substring(i, i + 2).contains("\u00a7"))
                        color = word.substring(i, i + 2); // retrieve the color format
                }
                lineLen -= 2;// don't count a color formatter with the line length.
            }
            if (lineLen + word.length() > maxCharPerLine || word.contains("\\n")) {
                word = word.replace("\\n", "");
                textList.add(output.toString());
                output.delete(0, output.length());
                output.append(color);
                lineLen = 0;
            } else if (lineLen > 0) {
                output.append(" ");
                lineLen++;
            }
            output.append(word);
            lineLen += word.length();
        }
        textList.add(output.toString());
        return textList;
    }

    public static List<ITextComponent> asStringComponent(List<String> l) {
        return l.stream().map(StringTextComponent::new).collect(Collectors.toList());
    }

    /**
     * Takes in the amount of ticks, and converts it into a time notation. 40 ticks will become "2s", while 2400 will result in "2m".
     *
     * @param ticks number of ticks
     * @param fraction When true, 30 ticks will show as '1.5s' instead of '1s'.
     * @return a formatted time
     */
    public static String convertTicksToMinutesAndSeconds(long ticks, boolean fraction) {
        String part = ticks % 20 * 5 + "";
        if (part.length() < 2) part = "0" + part;
        ticks /= 20;// first convert to seconds.
        if (ticks < 60) {
            return ticks + (fraction ? "." + part : "") + "s";
        } else {
            return ticks / 60 + "m " + ticks % 60 + "s";
        }
    }

    /**
     * Takes in any integer, and converts it into a string with a additional postfix if needed. 2300 will convert into 2k for instance.
     *
     * @param amount an integer quantity
     * @return a formatted string representation
     */
    public static String convertAmountToString(int amount) {
        if (amount < 1000) {
            return amount + "";
        } else {
            return amount / 1000 + "k";
        }
    }

    /**
     * Rounds numbers down at the given decimal. 1.234 with decimal 1 will result in a string holding "1.2"
     *
     * @param value a double-precision quantity
     * @param decimals number of digits to the right of the decimal point
     * @return a formatted string representation
     */
    public static String roundNumberTo(double value, int decimals) {
        return new BigDecimal(value).setScale(decimals, BigDecimal.ROUND_HALF_DOWN).toPlainString();
    }

    /**
     * Rounds numbers down at the given decimal. 1.234 with decimal 1 will result in a string holding "1.2"
     *
     * @param value a double-precision quantity
     * @param decimals number of digits to the right of the decimal point
     * @return the rounded value as a double-precision quantity
     */
    public static double roundNumberToDouble(double value, int decimals) {
        return new BigDecimal(value).setScale(decimals, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    /**
     * Rounds numbers down at the given decimal. 1.234 with decimal 1 will result in a string holding "1.2"
     *
     * @param value a floating point quantity
     * @param decimals number of digits to the right of the decimal point
     * @return a formatted string representation
     */
    public static String roundNumberTo(float value, int decimals) {
        return "" + Math.round(value * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }

    /**
     * used to compare two floats which are tested for having (almost) the same value
     */
    public static boolean areFloatsEqual(float f1, float f2) {
        return areFloatsEqual(f1, f2, 0.001F);
    }

    public static boolean areFloatsEqual(float f1, float f2, float maxDifference) {
        return Math.abs(f1 - f2) < maxDifference;
    }


    /**
     * Returns the redstone level at the given position. Use this when you don't care what side(s) the signal is
     * coming from, just the level of the signal at the position.
     *
     * @param world the world
     * @param pos the position to check
     * @return the redstone level
     */
    public static int getRedstoneLevel(World world, BlockPos pos) {
        return world != null ? world.getRedstonePowerFromNeighbors(pos) : 0;
    }

    /**
     * Retrieve a web page from the given URL.
     *
     * @param urlString the URL
     * @return the web page
     * @throws IOException if there are any problems
     */
    public static String getPage(final String urlString) throws IOException {
        StringBuilder all = new StringBuilder();
        URL myUrl = new URL(urlString);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(myUrl.openStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                all.append(line).append(System.getProperty("line.separator"));
            }
        }

        return all.toString();
    }

    public static double distBetween(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt(distBetweenSq(x1, y1, z1, x2, y2, z2));
    }

    public static double distBetweenSq(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2);
    }

    public static double distBetweenSq(BlockPos pos1, BlockPos pos2) {
        return distBetweenSq(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
    }

    public static double distBetween(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static double distBetweenSq(double x1, double y1, double x2, double y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    public static double distBetween(Vec3d vec, double x, double y, double z) {
        return distBetween(vec.x, vec.y, vec.z, x, y, z);
    }

    public static double distBetween(Vec3d vec1, Vec3d vec2) {
        return distBetween(vec1, vec2.x, vec2.y, vec2.z);
    }

    public static boolean doesItemMatchFilter(@Nonnull ItemStack filterStack, @Nonnull ItemStack stack, boolean checkDurability, boolean checkNBT, boolean checkModSimilarity) {
        if (filterStack.isEmpty() && stack.isEmpty()) return true;
        if (filterStack.isEmpty() || stack.isEmpty()) return false;

        if (checkModSimilarity) {
            String mod1 = filterStack.getItem().getRegistryName().getNamespace();
            String mod2 = stack.getItem().getRegistryName().getNamespace();
            return mod1.equals(mod2);
        }

        if (filterStack.getItem() != stack.getItem()) return false;

        boolean durabilityOK = !checkDurability || (filterStack.getMaxDamage() > 0 && filterStack.getDamage() == stack.getDamage());
        boolean nbtOK = !checkNBT || (filterStack.hasTag() ? filterStack.getTag().equals(stack.getTag()) : !stack.hasTag());

        return durabilityOK && nbtOK;
    }

    public static boolean isBlockLiquid(Block block) {
        return block instanceof FlowingFluidBlock;
    }

    public static String getOrientationName(Direction dir) {
        switch (dir) {
            case UP:
                return "Top";
            case DOWN:
                return "Bottom";
            case NORTH:
                return "North";
            case SOUTH:
                return "South";
            case EAST:
                return "East";
            case WEST:
                return "West";
            default:
                return "Unknown";
        }
    }

    public static void dropItemOnGround(ItemStack stack, World world, BlockPos pos) {
        dropItemOnGround(stack, world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
    }

    public static void dropItemOnGround(ItemStack stack, World world, double x, double y, double z) {
        float dX = world.rand.nextFloat() * 0.8F + 0.1F;
        float dY = world.rand.nextFloat() * 0.8F + 0.1F;
        float dZ = world.rand.nextFloat() * 0.8F + 0.1F;

        ItemEntity entityItem = new ItemEntity(world, x + dX, y + dY, z + dZ, stack.copy());

        if (stack.hasTag()) {
            entityItem.getItem().setTag(stack.getTag().copy());
        }

        float factor = 0.05F;
        entityItem.setMotion(world.rand.nextGaussian() * factor, world.rand.nextGaussian() * factor + 0.2, world.rand.nextGaussian() * factor);
        world.addEntity(entityItem);
        stack.setCount(0);
    }

    public static void dropItemOnGroundPrecisely(ItemStack stack, World world, double x, double y, double z) {
        ItemEntity entityItem = new ItemEntity(world, x, y, z, stack.copy());

        if (stack.hasTag()) {
            entityItem.getItem().setTag(stack.getTag().copy());
        }
        entityItem.setMotion(0, 0, 0);
        world.addEntity(entityItem);
        stack.setCount(0);
    }

    public static PlayerEntity getPlayerFromId(String uuid) {
        return ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByUUID(UUID.fromString(uuid));
    }

    public static PlayerEntity getPlayerFromId(UUID uuid) {
        return ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByUUID(uuid);
    }

    public static PlayerEntity getPlayerFromName(String name) {
        return ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByUsername(name);
    }

    public static boolean isPlayerOp(PlayerEntity player) {
        return player.hasPermissionLevel(2);
    }

    /**
     * Convenience method, ported from 1.8.  Consume one item from the player's inventory.
     *
     * @param inv player's inventory
     * @param item item to consume
     * @return true if an item was consumed
     */
    public static boolean consumeInventoryItem(PlayerInventory inv, Item item) {
        for (int i = 0; i < inv.mainInventory.size(); ++i) {
            if (inv.mainInventory.get(i).getItem() == item) {
                inv.mainInventory.get(i).shrink(1);
                if (inv.mainInventory.get(i).getCount() <= 0) {
                    inv.mainInventory.set(i, ItemStack.EMPTY);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Add all of the non-empty items in the given item handler to the given list.
     * @param handler the item handler
     * @param items the list
     */
    public static void collectNonEmptyItems(IItemHandler handler, NonNullList<ItemStack> items) {
        if (handler != null) {
            for (int i = 0; i < handler.getSlots(); i++) {
                if (!handler.getStackInSlot(i).isEmpty()) {
                    items.add(handler.getStackInSlot(i));
                }
            }
        }
    }

    /**
     * Convenience method, ported from 1.8.  Try to consume one item from the player's inventory.
     *
     * @param inv player's inventory
     * @param stack item to consume
     * @return true if an item was consumed
     */
    public static boolean consumeInventoryItem(PlayerInventory inv, ItemStack stack) {
        int toConsume = stack.getCount();
        for (int i = 0; i < inv.mainInventory.size(); ++i) {
            ItemStack invStack = inv.mainInventory.get(i);
            if (ItemStack.areItemsEqual(invStack, stack)) {
                int consumed = Math.min(invStack.getCount(), stack.getCount());
                invStack.shrink(consumed);
                toConsume -= consumed;
                if (toConsume <= 0) return true;
            }
        }
        return toConsume <= 0;
    }

    /**
     * Get a resource location with the domain of PneumaticCraft: Repressurized's mod ID.
     *
     * @param path the path
     * @return a mod-specific ResourceLocation for the given path
     */
    public static ResourceLocation RL(String path) {
        return new ResourceLocation(RefinedMetalSmelting.MOD_ID, path);
    }

    /**
     * Get a translation string for the given key.  This has support for The One Probe which runs server-side.
     *
     * @param s the translation key
     * @return the translated string (if called server-side, a string which The One Probe will handle client-side)
     */
    public static ITextComponent xlate(String s, Object... args) {
        return new TranslationTextComponent(s, args);
    }

    public static void addText(List<ITextComponent> l, String s) {
        l.add(new StringTextComponent(s));
    }

    public static String dyeColorDesc(int c) {
        // TODO 1.14 make this better
        return TextFormatting.BOLD + StringUtils.capitalize(DyeColor.byId(c).getTranslationKey()) + TextFormatting.RESET;
    }

    public static int getBurnTime(ItemStack stack) {
        int ret = stack.getBurnTime();
        return ForgeEventFactory.getItemBurnTime(stack, ret == -1 ? AbstractFurnaceTileEntity.getBurnTimes().getOrDefault(stack.getItem(), 0) : ret);
    }

    public static Vec3d getBlockCentre(BlockPos pos) {
        return new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
    }

    public static void copyItemHandler(IItemHandler source, ItemStackHandler dest) {
        dest.setSize(source.getSlots());
        for (int i = 0; i < source.getSlots(); i++) {
            dest.setStackInSlot(i, source.getStackInSlot(i).copy());
        }
    }

    public static String posToString(BlockPos pos) {
        return String.format("%d,%d,%d", pos.getX(), pos.getY(), pos.getZ());
    }
}
