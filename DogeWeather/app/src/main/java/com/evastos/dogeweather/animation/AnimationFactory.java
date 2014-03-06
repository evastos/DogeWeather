package com.evastos.dogeweather.animation;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;

/**
 * The Class AnimationFactory.
 *
 * A factory for creating Animation objects.
 *
 * @author Eva
 */
public class AnimationFactory {

	/**
	 * Slide animations to enter a view from top.
	 *
	 * @param duration the animation duration in milliseconds
	 * @param interpolator the interpolator to use (pass {@code null} to use the {@link AccelerateInterpolator} interpolator)
	 * @return a slide transition animation
	 */
	public static Animation inFromTopAnimation(long duration,
			Interpolator interpolator) {
		Animation inFromTopAnim = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromTopAnim.setDuration(duration);
		inFromTopAnim
				.setInterpolator(interpolator == null ? new AccelerateInterpolator()
						: interpolator);
		return inFromTopAnim;
	}

	/**
	 * Slide animations to hide a view by sliding it to the top.
	 *
	 * @param duration the animation duration in milliseconds
	 * @param interpolator the interpolator to use (pass {@code null} to use the {@link AccelerateInterpolator} interpolator)
	 * @return a slide transition animation

	 */
	public static Animation outToTopAnimation(long duration,
			Interpolator interpolator) {
		Animation outToTopAnim = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f);
		outToTopAnim.setDuration(duration);
		outToTopAnim.setInterpolator(interpolator == null ? new AccelerateInterpolator()
				: interpolator);
		return outToTopAnim;
	}

}
