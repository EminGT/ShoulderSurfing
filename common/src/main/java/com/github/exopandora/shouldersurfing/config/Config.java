package com.github.exopandora.shouldersurfing.config;

import com.github.exopandora.shouldersurfing.api.model.CrosshairType;
import com.github.exopandora.shouldersurfing.api.model.CrosshairVisibility;
import com.github.exopandora.shouldersurfing.api.model.Perspective;
import com.github.exopandora.shouldersurfing.api.model.TurningMode;
import com.github.exopandora.shouldersurfing.client.ShoulderSurfingImpl;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Config
{
	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final ClientConfig CLIENT;
	
	static
	{
		Pair<ClientConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
		CLIENT_SPEC = pair.getRight();
		CLIENT = pair.getLeft();
	}
	
	public static class ClientConfig
	{
		private final DoubleValue offsetX;
		private final DoubleValue offsetY;
		private final DoubleValue offsetZ;
		
		private final DoubleValue minOffsetX;
		private final DoubleValue minOffsetY;
		private final DoubleValue minOffsetZ;
		
		private final DoubleValue maxOffsetX;
		private final DoubleValue maxOffsetY;
		private final DoubleValue maxOffsetZ;
		
		private final BooleanValue unlimitedOffsetX;
		private final BooleanValue unlimitedOffsetY;
		private final BooleanValue unlimitedOffsetZ;
		
		private final DoubleValue passengerOffsetXMultiplier;
		private final DoubleValue passengerOffsetYMultiplier;
		private final DoubleValue passengerOffsetZMultiplier;
		
		private final DoubleValue sprintOffsetXMultiplier;
		private final DoubleValue sprintOffsetYMultiplier;
		private final DoubleValue sprintOffsetZMultiplier;
		
		private final DoubleValue aimingOffsetXMultiplier;
		private final DoubleValue aimingOffsetYMultiplier;
		private final DoubleValue aimingOffsetZMultiplier;

		private final DoubleValue fallFlyingOffsetXModifier;
		private final DoubleValue fallFlyingOffsetYModifier;
		private final DoubleValue fallFlyingOffsetZModifier;
		
		private final DoubleValue keepCameraOutOfHeadMultiplier;
		private final DoubleValue cameraStepSize;
		private final BooleanValue centerCameraWhenClimbing;
		private final BooleanValue centerCameraWhenFallFlying;
		private final DoubleValue cameraTransitionSpeedMultiplier;
		private final DoubleValue centerCameraWhenLookingDownAngle;
		private final BooleanValue dynamicallyAdjustOffsets;
		private final BooleanValue isCameraDecoupled;
		
		private final BooleanValue replaceDefaultPerspective;
		private final BooleanValue skipThirdPersonFront;
		private final ConfigValue<Perspective> defaultPerspective;
		private final BooleanValue rememberLastPerspective;
		
		private final BooleanValue playerTransparency;
		private final DoubleValue hidePlayerWhenLookingUpAngle;
		private final ConfigValue<TurningMode> turningModeWhenUsingItem;
		private final ConfigValue<TurningMode> turningModeWhenAttacking;
		private final ConfigValue<TurningMode> turningModeWhenInteraction;
		private final ConfigValue<TurningMode> turningModeWhenPicking;
		private final IntValue turningLockTime;
		private final BooleanValue syncPlayerXRotWithInputs;
		
		private final ConfigValue<CrosshairType> crosshairType;
		private final DoubleValue customRaytraceDistance;
		private final BooleanValue useCustomRaytraceDistance;
		private final ConfigValue<List<? extends String>> adaptiveCrosshairHoldItems;
		private final ConfigValue<List<? extends String>> adaptiveCrosshairUseItems;
		private final ConfigValue<List<? extends String>> adaptiveCrosshairHoldItemProperties;
		private final ConfigValue<List<? extends String>> adaptiveCrosshairUseItemProperties;
		private final Map<Perspective, ConfigValue<CrosshairVisibility>> crosshairVisibility = new HashMap<Perspective, ConfigValue<CrosshairVisibility>>();
		
		private final BooleanValue centerPlayerSounds;
		
		public ClientConfig(ForgeConfigSpec.Builder builder)
		{
			builder.push("camera");
			builder.push("offset");
			
			this.offsetX = builder
				.comment("Third person camera x-offset.")
				.translation("x-offset")
				.defineInRange("offset_x", -0.75D, -Double.MAX_VALUE, Double.MAX_VALUE);
			
			this.offsetY = builder
				.comment("Third person camera y-offset.")
				.translation("y-offset")
				.defineInRange("offset_y", 0.0D, -Double.MAX_VALUE, Double.MAX_VALUE);
			
			this.offsetZ = builder
				.comment("Third person camera z-offset.")
				.translation("z-offset")
				.defineInRange("offset_z", 4.0D, -Double.MAX_VALUE, Double.MAX_VALUE);
			
			builder.push("min");
			
			this.minOffsetX = builder
				.comment("If x-offset is limited this is the minimum amount.")
				.translation("Minimum x-offset")
				.defineInRange("min_offset_x", -3.0D, -Double.MAX_VALUE, Double.MAX_VALUE);
			
			this.minOffsetY = builder
				.comment("If y-offset is limited this is the minimum amount.")
				.translation("Minimum y-offset")
				.defineInRange("min_offset_y", -1.0D, -Double.MAX_VALUE, Double.MAX_VALUE);
			
			this.minOffsetZ = builder
				.comment("If z-offset is limited this is the minimum amount.")
				.translation("Minimum z-offset")
				.defineInRange("min_offset_z", -3.0D, -Double.MAX_VALUE, Double.MAX_VALUE);
			
			builder.pop();
			builder.push("max");
			
			this.maxOffsetX = builder
				.comment("If x-offset is limited this is the maximum amount.")
				.translation("Maximum x-offset")
				.defineInRange("max_offset_x", 3.0D, -Double.MAX_VALUE, Double.MAX_VALUE);
			
			this.maxOffsetY = builder
				.comment("If y-offset is limited this is the maximum amount.")
				.translation("Maximum y-offset")
				.defineInRange("max_offset_y", 1.5D, -Double.MAX_VALUE, Double.MAX_VALUE);
			
			this.maxOffsetZ = builder
				.comment("If z-offset is limited this is the maximum amount.")
				.translation("Maximum z-offset")
				.defineInRange("max_offset_z", 5.0D, -Double.MAX_VALUE, Double.MAX_VALUE);
			
			builder.pop();
			builder.push("limits");
			
			this.unlimitedOffsetX = builder
				.comment("Whether or not x-offset adjustment has limits.")
				.translation("Unlimited x-offset")
				.define("unlimited_offset_x", false);
			
			this.unlimitedOffsetY = builder
				.comment("Whether or not y-offset adjustment has limits.")
				.translation("Unlimited y-offset")
				.define("unlimited_offset_y", false);
			
			this.unlimitedOffsetZ = builder
				.comment("Whether or not z-offset adjustment has limits.")
				.translation("Unlimited z-offset")
				.define("unlimited_offset_z", false);
			
			builder.pop();
			builder.push("multiplier");
			builder.push("passenger");
			
			this.passengerOffsetXMultiplier = builder
				.comment("x-offset multiplier for when the player is a passenger.")
				.translation("Passenger x-offset multiplier")
				.defineInRange("multiplier_offset_x", 1.0D, 0, Double.MAX_VALUE);
			
			this.passengerOffsetYMultiplier = builder
				.comment("y-offset multiplier for when the player is a passenger.")
				.translation("Passenger y-offset multiplier")
				.defineInRange("multiplier_offset_y", 1.0D, 0, Double.MAX_VALUE);
			
			this.passengerOffsetZMultiplier = builder
				.comment("z-offset multiplier for when the player is a passenger.")
				.translation("Passenger z-offset multiplier")
				.defineInRange("multiplier_offset_z", 1.0D, 0, Double.MAX_VALUE);
			
			builder.pop();
			builder.push("sprint");
			
			this.sprintOffsetXMultiplier = builder
				.comment("x-offset multiplier for when the player is sprinting.")
				.translation("Sprint x-offset multiplier")
				.defineInRange("multiplier_offset_x", 1.0D, 0, Double.MAX_VALUE);
			
			this.sprintOffsetYMultiplier = builder
				.comment("y-offset multiplier for when the player is sprinting.")
				.translation("Sprint y-offset multiplier")
				.defineInRange("multiplier_offset_y", 1.0D, 0, Double.MAX_VALUE);
			
			this.sprintOffsetZMultiplier = builder
				.comment("z-offset multiplier for when the player is sprinting.")
				.translation("Sprint z-offset multiplier")
				.defineInRange("multiplier_offset_z", 1.0D, 0, Double.MAX_VALUE);
			
			builder.pop();
			builder.push("aiming");
			
			this.aimingOffsetXMultiplier = builder
				.comment("x-offset multiplier for when the player is aiming.")
				.translation("Aiming x-offset multiplier")
				.defineInRange("multiplier_offset_x", 1.0D, 0, Double.MAX_VALUE);
			
			this.aimingOffsetYMultiplier = builder
				.comment("y-offset multiplier for when the player is aiming.")
				.translation("Aiming y-offset multiplier")
				.defineInRange("multiplier_offset_y", 1.0D, 0, Double.MAX_VALUE);
			
			this.aimingOffsetZMultiplier = builder
				.comment("z-offset multiplier for when the player is aiming.")
				.translation("Aiming z-offset multiplier")
				.defineInRange("multiplier_offset_z", 1.0D, 0, Double.MAX_VALUE);
			
			builder.pop();
			builder.pop();
			builder.push("modifier");
			builder.push("fallFlying");

			this.fallFlyingOffsetXModifier = builder
				.comment("Third person camera x-offset when using Elytra.")
				.translation("Elytra x-offset")
				.defineInRange("fall_flying_offset_x", 0.0D, -Double.MAX_VALUE, Double.MAX_VALUE);

			this.fallFlyingOffsetYModifier = builder
				.comment("Third person camera y-offset when using Elytra.")
				.translation("Elytra y-offset")
				.defineInRange("fall_flying_offset_y", 0.5D, -Double.MAX_VALUE, Double.MAX_VALUE);

			this.fallFlyingOffsetZModifier = builder
				.comment("Third person camera z-offset when using Elytra.")
				.translation("Elytra z-offset")
				.defineInRange("fall_flying_offset_z", 0.0D, -Double.MAX_VALUE, Double.MAX_VALUE);

			builder.pop();
			builder.pop();
			builder.pop();
			
			this.keepCameraOutOfHeadMultiplier = builder
				.comment("The distance multiplier on whether or not to hide the player model if the camera gets too close to it. Set to 0 to disable.")
				.translation("Keep camera out of head distance multiplier")
				.defineInRange("keep_camera_out_of_head_distance_multiplier", 0.75D, 0D, Double.MAX_VALUE);
			
			this.cameraStepSize = builder
				.comment("Size of the camera adjustment per step.")
				.translation("Camera step size")
				.defineInRange("camera_step_size", 0.025D, -Double.MAX_VALUE, Double.MAX_VALUE);
			
			this.centerCameraWhenClimbing = builder
				.comment("Whether or not to temporarily center the camera when climbing.")
				.translation("Center camera when climbing")
				.define("center_camera_when_climbing", true);
			
			this.centerCameraWhenFallFlying = builder
				.comment("Whether or not to temporarily center the camera when using Elytra.")
				.translation("Center camera when using Elytra")
				.define("center_camera_when_fall_flying", false);
			
			this.cameraTransitionSpeedMultiplier = builder
				.comment("The speed multiplier at which the camera transitions between positions.")
				.translation("Camera transition speed multiplier")
				.defineInRange("camera_transition_speed_multiplier", 0.25D, 0.05D, 1.0D);
			
			this.centerCameraWhenLookingDownAngle = builder
				.comment("The angle at which the camera will be centered when looking down. Set to 0 to disable.")
				.translation("Center camera when looking down angle")
				.defineInRange("center_camera_when_looking_down_angle", 1D, 0D, 90D);
			
			this.dynamicallyAdjustOffsets = builder
				.comment("Whether or not to dynamically adjust camera offsets depending on space constraints.")
				.translation("Dynamically adjust offsets")
				.define("dynamically_adjust_offsets", true);
			
			this.isCameraDecoupled = builder
				.comment("Whether or not to decouple the camera rotation from the player rotation.")
				.translation("Decoupled camera")
				.define("decoupled_camera", true);
			
			builder.pop();
			builder.push("perspective");
			
			this.defaultPerspective = builder
				.comment("The default perspective when you load the game.")
				.translation("Default perspective")
				.defineEnum("default_perspective", Perspective.SHOULDER_SURFING, Perspective.values());
			
			this.rememberLastPerspective = builder
				.comment("Whether or not to remember the last perspective used.")
				.translation("Remember last perspective")
				.define("remember_last_perspective", true);
			
			this.replaceDefaultPerspective = builder
				.comment("Whether or not to replace the default third person perspective.")
				.translation("Replace default perspective")
				.define("replace_default_perspective", false);
			
			this.skipThirdPersonFront = builder
				.comment("Whether or not to skip the third person front perspective.")
				.translation("Skip third person front perspective")
				.define("skip_third_person_front_perspective", false);
			
			builder.pop();
			builder.push("player");
			
			this.playerTransparency = builder
				.comment("Whether or not to adjust the player model transparency when view is obstructed. Changing this value may require a game restart to take full effect.")
				.translation("Adjust player transparency")
				.define("adjust_player_transparency", true);
			
			this.hidePlayerWhenLookingUpAngle = builder
				.comment("The angle at which the player will no longer be rendered when looking up. Set to 0 to disable.")
				.translation("Center camera when looking up angle")
				.defineInRange("hide_player_when_looking_up_angle", 0D, 0D, 90D);
			
			this.syncPlayerXRotWithInputs = builder
				.comment("Whether to synchronize the x-rot of the player with mouse or controller inputs. This config option only applies when camera is decoupled.")
				.translation("Sync player x-rot with mouse or controller inputs")
				.define("sync_player_x_rot_with_inputs", false);
			
			builder.push("turning");
			
			this.turningModeWhenUsingItem = builder
				.comment("Whether to turn the player when using an item. This config option only applies when camera is decoupled.")
				.translation("Turn player when using an item")
				.defineEnum("when_using_item", TurningMode.ALWAYS, TurningMode.values());
			
			this.turningModeWhenAttacking = builder
				.comment("Whether to turn the player when attacking. This config option only applies when camera is decoupled.")
				.translation("Turn player when attacking")
				.defineEnum("when_attacking", TurningMode.REQUIRES_TARGET, TurningMode.values());
			
			this.turningModeWhenInteraction = builder
				.comment("Whether to turn the player when interacting with blocks. This config option only applies when camera is decoupled.")
				.translation("Turn player when interacting with blocks")
				.defineEnum("when_interacting", TurningMode.ALWAYS, TurningMode.values());
			
			this.turningModeWhenPicking = builder
				.comment("Whether to turn the player when picking blocks or entities. This config option only applies when camera is decoupled.")
				.translation("Turn player when picking")
				.defineEnum("when_picking", TurningMode.ALWAYS, TurningMode.values());
			
			this.turningLockTime = builder
				.comment("The time in ticks the player will remain turned after the interaction has ended. Set to 0 to disable. This config option only applies when camera is decoupled.")
				.translation("Turning lock time (ticks)")
				.defineInRange("turning_lock_time", 4, 0, Integer.MAX_VALUE);
			
			builder.pop();
			builder.pop();
			builder.push("crosshair");
			
			this.crosshairType = builder
				.comment("Crosshair type to use for shoulder surfing.")
				.translation("Crosshair type")
				.defineEnum("crosshair_type", CrosshairType.STATIC, CrosshairType.values());
			
			this.customRaytraceDistance = builder
				.comment("The raytrace distance used for the dynamic crosshair.")
				.translation("Custom raytrace distance")
				.defineInRange("custom_raytrace_distance", 400, 0, Double.MAX_VALUE);
			
			this.useCustomRaytraceDistance = builder
				.comment("Whether or not to use the custom raytrace distance used for the dynamic crosshair.")
				.translation("Use custom raytrace distance")
				.define("use_custom_raytrace_distance", true);
			
			this.adaptiveCrosshairHoldItems = builder
				.comment("Items that when held, trigger the dynamic crosshair in adaptive mode. This config option supports regular expressions. Example: 'minecraft:.*sword' matches 'minecraft:wooden_sword' and 'minecraft:netherite_sword'.")
				.translation("Adaptive crosshair items (hold)")
				.defineList("adaptive_crosshair_hold_items", () ->
				{
					List<String> items = new ArrayList<String>();
					items.add(BuiltInRegistries.ITEM.getKey(Items.SNOWBALL).toString());
					items.add(BuiltInRegistries.ITEM.getKey(Items.EGG).toString());
					items.add(BuiltInRegistries.ITEM.getKey(Items.EXPERIENCE_BOTTLE).toString());
					items.add(BuiltInRegistries.ITEM.getKey(Items.ENDER_PEARL).toString());
					items.add(BuiltInRegistries.ITEM.getKey(Items.SPLASH_POTION).toString());
					items.add(BuiltInRegistries.ITEM.getKey(Items.FISHING_ROD).toString());
					items.add(BuiltInRegistries.ITEM.getKey(Items.LINGERING_POTION).toString());
					return items;
				}, Objects::nonNull);
			
			this.adaptiveCrosshairUseItems = builder
				.comment("Items that when used, trigger the dynamic crosshair in adaptive mode. This config option supports regular expressions. Example: 'minecraft:.*sword' matches 'minecraft:wooden_sword' and 'minecraft:netherite_sword'.")
				.translation("Adaptive crosshair items (use)")
				.defineList("adaptive_crosshair_use_items", ArrayList::new, Objects::nonNull);
			
			this.adaptiveCrosshairHoldItemProperties = builder
				.comment("Item properties of an item, that when held, trigger the dynamic crosshair in adaptive mode.")
				.translation("Adaptive crosshair item properties (hold)")
				.defineList("adaptive_crosshair_hold_item_properties", () ->
				{
					List<String> items = new ArrayList<String>();
					items.add(ResourceLocation.withDefaultNamespace("charged").toString());
					return items;
				}, item -> item != null && ResourceLocation.tryParse(item.toString()) != null);
			
			this.adaptiveCrosshairUseItemProperties = builder
				.comment("Item properties of an item, that when used, trigger the dynamic crosshair in adaptive mode.")
				.translation("Adaptive crosshair item properties (use)")
				.defineList("adaptive_crosshair_use_item_properties", () ->
				{
					List<String> items = new ArrayList<String>();
					items.add(ResourceLocation.withDefaultNamespace("pull").toString());
					items.add(ResourceLocation.withDefaultNamespace("throwing").toString());
					return items;
				}, item -> item != null && ResourceLocation.tryParse(item.toString()) != null);
			
			builder.push("visibility");
			
			for(Perspective entry : Perspective.values())
			{
				ConfigValue<CrosshairVisibility> crosshairVisibility = builder
					.comment("Crosshair visibility for " + entry.toString().toLowerCase() + ".")
					.translation(entry + " crosshair visibility")
					.defineEnum(entry.toString().toLowerCase(), entry.getDefaultCrosshairVisibility(), CrosshairVisibility.values());
				this.crosshairVisibility.put(entry, crosshairVisibility);
			}
			
			builder.pop();
			builder.pop();
			builder.push("audio");
			
			this.centerPlayerSounds = builder
				.comment("Whether to center sounds made by the player.")
				.translation("Center player sounds")
				.define("center_player_sounds", false);
			
			builder.pop();
		}
		
		public double getOffsetX()
		{
			return this.offsetX.get();
		}
		
		public double getOffsetY()
		{
			return this.offsetY.get();
		}
		
		public double getOffsetZ()
		{
			return this.offsetZ.get();
		}
		
		public double getMinOffsetX()
		{
			return this.minOffsetX.get();
		}
		
		public double getMinOffsetY()
		{
			return this.minOffsetY.get();
		}
		
		public double getMinOffsetZ()
		{
			return this.minOffsetZ.get();
		}
		
		public double getMaxOffsetX()
		{
			return this.maxOffsetX.get();
		}
		
		public double getMaxOffsetY()
		{
			return this.maxOffsetY.get();
		}
		
		public double getMaxOffsetZ()
		{
			return this.maxOffsetZ.get();
		}
		
		public boolean isUnlimitedOffsetX()
		{
			return this.unlimitedOffsetX.get();
		}
		
		public boolean isUnlimitedOffsetY()
		{
			return this.unlimitedOffsetY.get();
		}
		
		public boolean isUnlimitedOffsetZ()
		{
			return this.unlimitedOffsetZ.get();
		}
		
		public double getPassengerOffsetXMultiplier()
		{
			return this.passengerOffsetXMultiplier.get();
		}
		
		public double getPassengerOffsetYMultiplier()
		{
			return this.passengerOffsetYMultiplier.get();
		}
		
		public double getPassengerOffsetZMultiplier()
		{
			return this.passengerOffsetZMultiplier.get();
		}
		
		public Vec3 getPassengerOffsetMultipliers()
		{
			return new Vec3(this.getPassengerOffsetXMultiplier(), this.getPassengerOffsetYMultiplier(), this.getPassengerOffsetZMultiplier());
		}
		
		public double getSprintOffsetXMultiplier()
		{
			return this.sprintOffsetXMultiplier.get();
		}
		
		public double getSprintOffsetYMultiplier()
		{
			return this.sprintOffsetYMultiplier.get();
		}
		
		public double getSprintOffsetZMultiplier()
		{
			return this.sprintOffsetZMultiplier.get();
		}
		
		public Vec3 getSprintOffsetMultipliers()
		{
			return new Vec3(this.getSprintOffsetXMultiplier(), this.getSprintOffsetYMultiplier(), this.getSprintOffsetZMultiplier());
		}
		
		public double getAimingOffsetXMultiplier()
		{
			return this.aimingOffsetXMultiplier.get();
		}
		
		public double getAimingOffsetYMultiplier()
		{
			return this.aimingOffsetYMultiplier.get();
		}
		
		public double getAimingOffsetZMultiplier()
		{
			return this.aimingOffsetZMultiplier.get();
		}
		
		public Vec3 getAimingOffsetMultipliers()
		{
			return new Vec3(this.getAimingOffsetXMultiplier(), this.getAimingOffsetYMultiplier(), this.getAimingOffsetZMultiplier());
		}

		public double getFallFlyingOffsetXModifier()
		{
			return this.fallFlyingOffsetXModifier.get();
		}

		public double getFallFlyingOffsetYModifier()
		{
			return this.fallFlyingOffsetYModifier.get();
		}

		public double getFallFlyingOffsetZModifier()
		{
			return this.fallFlyingOffsetZModifier.get();
		}
		
		public Vec3 getFallFlyingOffsetModifiers()
		{
			return new Vec3(this.fallFlyingOffsetXModifier(), this.fallFlyingOffsetYModifier(), this.fallFlyingOffsetZModifier());
		}
		
		public CrosshairVisibility getCrosshairVisibility(Perspective perspective)
		{
			return this.crosshairVisibility.get(perspective).get();
		}
		
		public boolean useCustomRaytraceDistance()
		{
			return this.useCustomRaytraceDistance.get();
		}
		
		public double keepCameraOutOfHeadMultiplier()
		{
			return this.keepCameraOutOfHeadMultiplier.get();
		}
		
		public boolean replaceDefaultPerspective()
		{
			return this.replaceDefaultPerspective.get();
		}
		
		public boolean skipThirdPersonFront()
		{
			return this.skipThirdPersonFront.get();
		}
		
		public Perspective getDefaultPerspective()
		{
			return this.defaultPerspective.get();
		}
		
		public void setDefaultPerspective(Perspective perspective)
		{
			Config.set(this.defaultPerspective, perspective);
		}
		
		public CrosshairType getCrosshairType()
		{
			return this.crosshairType.get();
		}
		
		public boolean doRememberLastPerspective()
		{
			return this.rememberLastPerspective.get();
		}
		
		public double getCameraStepSize()
		{
			return this.cameraStepSize.get();
		}
		
		public boolean doCenterCameraWhenClimbing()
		{
			return this.centerCameraWhenClimbing.get();
		}
		
		public boolean doCenterCameraWhenFallFlying()
		{
			return this.centerCameraWhenFallFlying.get();
		}
		
		public double getCameraTransitionSpeedMultiplier()
		{
			return this.cameraTransitionSpeedMultiplier.get();
		}
		
		public double getCenterCameraWhenLookingDownAngle()
		{
			return this.centerCameraWhenLookingDownAngle.get();
		}
		
		public double getHidePlayerWhenLookingUpAngle()
		{
			return this.hidePlayerWhenLookingUpAngle.get();
		}
		
		public boolean doDynamicallyAdjustOffsets()
		{
			return this.dynamicallyAdjustOffsets.get();
		}
		
		public boolean isPlayerTransparencyEnabled()
		{
			return Config.CLIENT_SPEC.isLoaded() ? this.playerTransparency.get() : this.playerTransparency.getDefault();
		}
		
		public TurningMode getTurningModeWhenUsingItem()
		{
			return this.turningModeWhenUsingItem.get();
		}
		
		public TurningMode getTurningModeWhenAttacking()
		{
			return this.turningModeWhenAttacking.get();
		}
		
		public TurningMode getTurningModeWhenInteracting()
		{
			return this.turningModeWhenInteraction.get();
		}
		
		public TurningMode getTurningModeWhenPicking()
		{
			return this.turningModeWhenPicking.get();
		}
		
		public int getTurningLockTime()
		{
			return this.turningLockTime.get();
		}
		
		public boolean isCameraDecoupled()
		{
			return this.isCameraDecoupled.get();
		}
		
		public double getCustomRaytraceDistance()
		{
			return this.customRaytraceDistance.get();
		}
		
		public List<? extends String> getAdaptiveCrosshairHoldItems()
		{
			return this.adaptiveCrosshairHoldItems.get();
		}
		
		public List<? extends String> getAdaptiveCrosshairUseItems()
		{
			return this.adaptiveCrosshairUseItems.get();
		}
		
		public List<? extends String> getAdaptiveCrosshairHoldItemProperties()
		{
			return this.adaptiveCrosshairHoldItemProperties.get();
		}
		
		public List<? extends String> getAdaptiveCrosshairUseItemProperties()
		{
			return this.adaptiveCrosshairUseItemProperties.get();
		}
		
		public boolean doCenterPlayerSounds()
		{
			return this.centerPlayerSounds.get();
		}
		
		public boolean doSyncPlayerXRotWithInputs()
		{
			return this.syncPlayerXRotWithInputs.get();
		}
		
		public void adjustCameraLeft()
		{
			Config.set(this.offsetX, this.addStep(this.getOffsetX(), this.getMaxOffsetX(), this.isUnlimitedOffsetX()));
		}
		
		public void adjustCameraRight()
		{
			Config.set(this.offsetX, this.subStep(this.getOffsetX(), this.getMinOffsetX(), this.isUnlimitedOffsetX()));
		}
		
		public void adjustCameraUp()
		{
			Config.set(this.offsetY, this.addStep(this.getOffsetY(), this.getMaxOffsetY(), this.isUnlimitedOffsetY()));
		}
		
		public void adjustCameraDown()
		{
			Config.set(this.offsetY, this.subStep(this.getOffsetY(), this.getMinOffsetY(), this.isUnlimitedOffsetY()));
		}
		
		public void adjustCameraIn()
		{
			Config.set(this.offsetZ, this.subStep(this.getOffsetZ(), this.getMinOffsetZ(), this.isUnlimitedOffsetZ()));
		}
		
		public void adjustCameraOut()
		{
			Config.set(this.offsetZ, this.addStep(this.getOffsetZ(), this.getMaxOffsetZ(), this.isUnlimitedOffsetZ()));
		}
		
		private double addStep(double value, double max, boolean unlimited)
		{
			double next = value + this.getCameraStepSize();
			
			if(unlimited)
			{
				return next;
			}
			
			return Math.min(next, max);
		}
		
		private double subStep(double value, double min, boolean unlimited)
		{
			double next = value - this.getCameraStepSize();
			
			if(unlimited)
			{
				return next;
			}
			
			return Math.max(next, min);
		}
		
		public void swapShoulder()
		{
			Config.set(this.offsetX, -this.getOffsetX());
		}
	}
	
	protected static <T> void set(ForgeConfigSpec.ConfigValue<T> configValue, T value)
	{
		if(value != null && !value.equals(configValue.get()))
		{
			configValue.set(value);
		}
	}
	
	public static void onConfigReload()
	{
		if(Config.CLIENT.doRememberLastPerspective())
		{
			Config.CLIENT.setDefaultPerspective(Perspective.current());
		}
		
		if(!Config.CLIENT.isCameraDecoupled())
		{
			ShoulderSurfingImpl.getInstance().resetState();
		}
	}
}
