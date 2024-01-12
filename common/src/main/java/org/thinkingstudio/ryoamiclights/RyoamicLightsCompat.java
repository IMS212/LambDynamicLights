/*
 * Copyright © 2020 LambdAurora <email@lambdaurora.dev>
 *
 * This file is part of LambDynamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.thinkingstudio.ryoamiclights;

import dev.architectury.platform.Platform;

/**
 * Represents a utility class for compatibility.
 *
 * @author LambdAurora
 * @version 1.3.3
 * @since 1.0.0
 */
public final class RyoamicLightsCompat {
	/**
	 * Returns whether Canvas is installed.
	 *
	 * @return {@code true} if Canvas is installed, else {@code false}
	 */
	public static boolean isCanvasInstalled() {
		return Platform.isModLoaded("canvas");
	}

	/**
	 * Returns whether Lil Tater Reloaded is installed.
	 *
	 * @return {@code true} if LTR is installed, else {@code false}
	 */
	public static boolean isLilTaterReloadedInstalled() {
		// Don't even think about it Yog.
		return Platform.isModLoaded("ltr");
	}

	/**
	 * Returns whether Embeddium is installed.
	 *
	 * @return {@code true} if Embeddium is installed, else {@code false}
	 */
	public static boolean isEmbeddiumInstalled() {
		return Platform.isModLoaded("embeddium");
	}

	/**
	 * Returns whether Sodium is installed.
	 *
	 * @return {@code true} if Sodium is installed, else {@code false}
	 */
	public static boolean isSodiumInstalled() {
		return Platform.isModLoaded("embeddium");
	}


	/**
	 * Returns whether Forgified Fabric API is installed.
	 *
	 * @return {@code true} if Forgified Fabric API is installed, else {@code false}
	 */
	public static boolean isForgifiedFabricApiInstalled() {
		return Platform.isModLoaded("fabric_api");
	}

	/**
	 * Returns whether Fabric API is installed.
	 *
	 * @return {@code true} if Fabric API is installed, else {@code false}
	 */
	public static boolean isFabricApiInstalled() {
		return Platform.isModLoaded("fabric-api");
	}
}
